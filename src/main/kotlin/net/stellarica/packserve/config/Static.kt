package net.stellarica.packserve.config

import com.sksamuel.hoplite.ConfigAlias

data class Static(
	@ConfigAlias("output-file") val outputFile: String,
	@ConfigAlias("external-url") val externalUrl: String
)