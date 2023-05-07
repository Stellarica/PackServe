package net.stellarica.packserve.output

import net.stellarica.packserve.PackServe.Companion.instance
import net.stellarica.packserve.newTempDirectory
import org.http4k.routing.ResourceLoader.Companion.Directory
import org.http4k.routing.static
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.copyTo

class HttpServerOutput(
	private var port: Int,
	private var externalAddress: String
) : ResourcePackOutput {
	private lateinit var pack: Path
	private lateinit var server: Http4kServer
	override fun start(pack: Path) {
		instance.logger.warn("Using internal HTTP server to host the pack.")
		instance.logger.warn("For security reasons, this is not recommended on production servers!")

		// don't want to just serve the pack we're passed, as http4k serves the entire folder,
		// and we don't necessarily have control over where that is
		val tempDir = newTempDirectory()
		this.pack = pack.copyTo(tempDir.resolve("pack.zip"))
		this.server = static(Directory(tempDir.absolute().toString()))
			.asServer(Jetty(port))
			.start()
	}

	override fun stop() {
		server.stop()
	}

	override fun getDownloadURL(): String = externalAddress.removeSuffix("/") + "/pack.zip"
	override fun getPackSha1(): ByteArray = getPackSha1(pack)
}
