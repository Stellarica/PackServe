package net.stellarica.packserve.output

import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories

class StaticPackOutput(
	private val output: Path,
	private val downloadUrl: String
) : ResourcePackOutput {
	override fun outputPack(pack: Path) {
		output.parent.createDirectories()
		pack.copyTo(output)
	}

	override fun getDownloadURL(): String = downloadUrl
	override fun getPackSha1(): ByteArray = getPackSha1(output)
}