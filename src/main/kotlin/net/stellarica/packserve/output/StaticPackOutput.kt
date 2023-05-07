package net.stellarica.packserve.output

import net.stellarica.packserve.source.ResourcePackSource
import java.nio.file.Path

class StaticPackOutput(
	override var source: ResourcePackSource,
	private var target: Path,
	private var downloadUrl: String
) : ResourcePackOutput {
	override fun getDownloadURL(): String = downloadUrl
}