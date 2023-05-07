package net.stellarica.packserve.config

import com.sksamuel.hoplite.ConfigAlias

data class Config(
	val promptMessage: String,
	@ConfigAlias("kick-if-failed") val kickIfFailed: Boolean,
	@ConfigAlias("kick-message") val kickMessage: String,
	val source: Source,
	@ConfigAlias("use-integrated-server") val useIntegratedServer: Boolean,
	val server: Server,
	val static: Static,
	val configured: Boolean
)
