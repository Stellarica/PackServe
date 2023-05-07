package net.stellarica.packserve.source

import java.nio.file.Path

interface ResourcePackSource {
	fun getPackZip(): Path
}