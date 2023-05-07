package net.stellarica.packserve.output;

import java.net.URL
import java.nio.file.Path

class HttpServerOutput(
	private var port: Int,
	private var externalAddress: URL
) : ResourcePackOutput {
	private lateinit var pack: Path
	override fun outputPack(pack: Path) {
		this.pack = pack
	}

	override fun getDownloadURL(): String {
		TODO()
	}

	override fun getPackSha1(): ByteArray = getPackSha1(pack)
}
