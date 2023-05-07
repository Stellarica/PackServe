package net.stellarica.packserve.source

import java.net.URL
import java.nio.file.Path

class GitHubSource(
	private val repository: URL,
	private val branch: String
) : ResourcePackSource {
	override fun getPackZip(): Path {
		// download zip from github
		// however it contains a nested folder structure, so we need to fix that
		TODO()
	}
}