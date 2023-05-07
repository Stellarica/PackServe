package net.stellarica.packserve.config

import com.sksamuel.hoplite.ConfigAlias

data class Server(
	val port: Int,
	@ConfigAlias("external-address") val externalAddress: String
)