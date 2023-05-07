package net.stellarica.packserve.config

import com.sksamuel.hoplite.ConfigAlias

data class Source(
	@ConfigAlias("use-github") val useGitHub: Boolean,
	@ConfigAlias("local-path") val localPath: String,
	val repository: String,
	val branch: String
)