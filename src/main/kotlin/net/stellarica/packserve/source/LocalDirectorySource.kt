package net.stellarica.packserve.source

import net.stellarica.packserve.newTempFile
import net.stellarica.packserve.zipDirectory
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

class LocalDirectorySource(private val dir: Path) : ResourcePackSource {
	init {
		if (!(dir.exists() && dir.isDirectory())) throw IllegalArgumentException()
	}

	override fun getPackZip(): Path {
		val temp = newTempFile()
		zipDirectory(dir, temp)
		return temp
	}
}