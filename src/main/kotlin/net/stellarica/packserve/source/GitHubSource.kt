package net.stellarica.packserve.source

import net.stellarica.packserve.PackServe.Companion.instance
import net.stellarica.packserve.newTempDirectory
import net.stellarica.packserve.newTempFile
import net.stellarica.packserve.unzipFile
import net.stellarica.packserve.zipDirectory
import java.net.URL
import java.nio.file.Path

import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.outputStream

class GitHubSource(
	private val repository: String,
	private val branch: String
) : ResourcePackSource {
	override fun getPackZip(): Path {
		// fairly easy to download a zip from GitHub,
		// however it contains a nested folder structure which we need to fix
		instance.logger.info("Downloading zip from GitHub")
		val u = URL(repository.removeSuffix("/") + "/zipball/$branch")
		val temp = newTempFile()
		val i = u.openStream()
		val o = temp.outputStream()
		i.copyTo(o)
		i.close()
		o.close()

		// maybe there's a better way to remove the intermediate
		// folder than unzipping and rezipping?
		val extracted = newTempDirectory()
		unzipFile(temp, extracted)
		val new = newTempFile()
		zipDirectory(extracted.listDirectoryEntries().first(), new)
		instance.logger.info("Extracted pack from GitHub")
		return new
	}
}