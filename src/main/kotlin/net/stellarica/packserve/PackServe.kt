package net.stellarica.packserve

import com.google.inject.Inject
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addFileSource
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerPostConnectEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.player.ResourcePackInfo
import net.kyori.adventure.text.Component
import net.stellarica.packserve.config.Config
import net.stellarica.packserve.output.HttpServerOutput
import net.stellarica.packserve.output.ResourcePackOutput
import net.stellarica.packserve.output.StaticPackOutput
import net.stellarica.packserve.source.GitHubSource
import net.stellarica.packserve.source.LocalDirectorySource
import net.stellarica.packserve.source.LocalFileSource
import net.stellarica.packserve.source.ResourcePackSource
import org.slf4j.Logger
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

@Suppress("Unused")
@Plugin(
	id = "packserve",
	name = "PackServe",
	version = "0.1.0",
	url = "https://github.com/Stellarica/PackServe",
	description = "Resource pack stuff:tm:",
	authors = ["trainb0y"]
)
class PackServe @Inject constructor(
	val server: ProxyServer,
	private val logger: Logger,
	@DataDirectory val dataDir: Path
) {
	private lateinit var config: Config
	private lateinit var source: ResourcePackSource
	private lateinit var output: ResourcePackOutput

	companion object {
		lateinit var instance: PackServe
	}

	@Subscribe
	fun onProxyInit(event: ProxyInitializeEvent) {
		instance = this

		val configPath = dataDir.resolve("packserve.conf")

		if (!configPath.exists()) {
			dataDir.createDirectories()
			Files.copy(this::class.java.getResource("/packserve.conf")!!.openStream(), configPath)
		}

		config = ConfigLoaderBuilder.empty()
			.withClassLoader(this::class.java.classLoader)
			.addDefaults()
			.addFileSource(configPath.toFile())
			.build()
			.loadConfigOrThrow<Config>()

		if (!config.configured) {
			logger.error("PackServe has not been configured!")
			return
		}

		source = if (config.source.useGitHub) {
			logger.info("Using GitHUb resource pack source")
			GitHubSource(URL(config.source.repository), config.source.branch)
		} else {
			val path = Path.of(config.source.localPath)
			if (path.isDirectory()) {
				logger.info("Using local directory resource pack source")
				LocalDirectorySource(path)
			} else if (path.exists()) {
				logger.info("Using local file resource pack source")
				LocalFileSource(path)
			} else {
				logger.error("No valid resource pack source specified!")
				return
			}
		}

		output = if (config.useIntegratedServer) {
			HttpServerOutput(source, config.server.port, URL(config.server.externalAddress))
		} else {
			StaticPackOutput(source, Path.of(config.static.outputFile), config.static.externalUrl)
		}
	}

	fun getPackInfo(): ResourcePackInfo = server.createResourcePackBuilder(output.getDownloadURL())
		.setHash(output.getPackSha1())
		.setPrompt(Component.text(config.promptMessage))
		.build()

	@Subscribe
	fun onPlayerJoin(event: ServerPostConnectEvent) {
		event.player.sendResourcePackOffer(getPackInfo())
	}
}