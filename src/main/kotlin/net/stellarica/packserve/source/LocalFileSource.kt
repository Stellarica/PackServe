package net.stellarica.packserve.source

import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

class LocalFileSource(private val file: Path) : ResourcePackSource {
	init {
		if (!file.exists() || file.isDirectory()) throw IllegalArgumentException()
	}

	override fun getPackZip(): Path = file
}