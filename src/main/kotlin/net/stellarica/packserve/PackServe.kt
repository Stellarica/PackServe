package net.stellarica.packserve

import com.google.inject.Inject
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addFileSource
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import org.slf4j.Logger
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

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
	private val server: ProxyServer,
	private val logger: Logger,
	@DataDirectory val dataDir: Path
) {
	private lateinit var config: Config

	private lateinit var packHash: ByteArray
	private lateinit var packURL: String

	@Subscribe
	fun onProxyInit(event: ProxyInitializeEvent) {
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

		/*
		packURL =  config.repository + "/archive/refs/heads/" + config.branch + ".zip"
		packHash = config.hash.toByteArray()
		if (packHash.size != 20) {
			logger.warn("Attempting to get SHA-1 hash of resource pack, as it wasn't specified!")
			packHash = MessageDigest
				.getInstance("SHA-1")
				.digest(URL(packURL).openConnection().getInputStream().readAllBytes())
		}*/
	}

	@Subscribe
	fun onResourcePack(event: PlayerChatEvent) {
		logger.warn("sending pack")
		val pack = server.createResourcePackBuilder(packURL)
			.setHash(packHash)
			.setPrompt(Component.text("Download! Or perish!"))
			.setShouldForce(true)
			.build()

		event.player.sendResourcePackOffer(pack)
	}
}