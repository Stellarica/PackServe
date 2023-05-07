package net.stellarica.packserve

import com.google.inject.Inject
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addFileSource
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerResourcePackStatusEvent
import com.velocitypowered.api.event.player.ServerPostConnectEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import net.stellarica.packserve.config.Config
import net.stellarica.packserve.output.HttpServerOutput
import net.stellarica.packserve.output.StaticPackOutput
import net.stellarica.packserve.source.GitHubSource
import net.stellarica.packserve.source.LocalDirectorySource
import net.stellarica.packserve.source.LocalFileSource
import org.slf4j.Logger
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
	val logger: Logger,
	@DataDirectory val dataDir: Path
) {
	lateinit var config: Config
	private lateinit var manager: PackManager

	companion object {
		lateinit var instance: PackServe
	}

	@Subscribe
	fun onProxyInit(event: ProxyInitializeEvent) {
		instance = this
		loadConfig()
		createPackManager()
		manager.processPack()
	}

	fun loadConfig() {
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
	}

	private fun createPackManager() {
		if (!config.configured) {
			logger.error("PackServe has not been configured!")
			return
		}

		val source = if (config.source.useGitHub) {
			logger.info("Using GitHUb resource pack source")
			GitHubSource(config.source.repository, config.source.branch)
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

		val output = if (config.useIntegratedServer) {
			HttpServerOutput(config.server.port, config.server.externalAddress)
		} else {
			StaticPackOutput(Path.of(config.static.outputFile), config.static.externalUrl)
		}

		manager = PackManager(source, output)
	}

	@Subscribe
	fun onPlayerJoin(event: ServerPostConnectEvent) {
		val pack = manager.getPackInfo()
		if (event.player.appliedResourcePack != pack && event.player.pendingResourcePack != pack)
			event.player.sendResourcePackOffer(pack)
	}

	@Subscribe
	fun onResourcePackStatus(event: PlayerResourcePackStatusEvent) {
		if (
			config.kickIfFailed &&
			event.status != PlayerResourcePackStatusEvent.Status.ACCEPTED &&
			event.status != PlayerResourcePackStatusEvent.Status.SUCCESSFUL
			)
		{
			event.player.disconnect(config.kickMessage.asMiniMessage())
		}
	}
}