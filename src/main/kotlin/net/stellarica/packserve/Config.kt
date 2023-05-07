package net.stellarica.packserve

import com.sksamuel.hoplite.ConfigAlias

data class Config (
	val promptMessage: String,
	@ConfigAlias("kick-if-failed") val kickIfFailed: Boolean,
	@ConfigAlias("kick-message") val kickMessage: String,
	val source: Source,
	@ConfigAlias("use-integrated-server") val useIntegratedServer: Boolean,
	val server: Server,
	val static: Static,
	val configured: Boolean
)

data class Source(
	@ConfigAlias("use-github") val useGitHub: Boolean,
	@ConfigAlias("local-path") val localPath: String,
	val repository: String,
	val branch: String
)

data class Server(
	val port: Int,
	@ConfigAlias("external-address") val externalAddress: String
)

data class Static(
	@ConfigAlias("output-file") val outputFile: String,
	@ConfigAlias("external-url") val externalUrl: String
)