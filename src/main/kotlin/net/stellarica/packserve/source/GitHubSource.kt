package net.stellarica.packserve.source

import java.net.URL
import java.nio.file.Path

class GitHubSource(private val repository: URL, private val branch: String) : ResourcePackSource {
	override fun getPackZip(): Path {
		TODO()
	}
}