package net.stellarica.packserve.output

import net.stellarica.packserve.source.ResourcePackSource
import java.nio.file.Path

class StaticPackOutput(
	private val output: Path,
	private val downloadUrl: String
): ResourcePackOutput {
	override fun outputPack(pack: Path) {
		TODO()
	}

	override fun getDownloadURL(): String = downloadUrl
	override fun getPackSha1(): ByteArray {
		TODO("Not yet implemented")
	}
}