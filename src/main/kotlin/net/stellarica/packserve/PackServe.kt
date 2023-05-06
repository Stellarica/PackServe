package net.stellarica.packserve

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import java.nio.file.Path

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
	@Subscribe
	fun onProxyInit(event: ProxyInitializeEvent) {

	}
}