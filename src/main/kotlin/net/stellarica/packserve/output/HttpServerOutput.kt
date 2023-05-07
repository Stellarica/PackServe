package net.stellarica.packserve.output;

import net.stellarica.packserve.source.ResourcePackSource
import java.net.URL

class HttpServerOutput(
	override var source: ResourcePackSource,
	private var port: Int,
	private var externalAddress: URL
) : ResourcePackOutput {
	override fun getDownloadURL(): String {
		TODO()
	}
}
