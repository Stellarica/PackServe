package net.stellarica.packserve.output;

import net.stellarica.packserve.PackServe.Companion.instance
import net.stellarica.packserve.newTempDirectory
import org.http4k.core.Filter
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.static
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.routing.ResourceLoader.Companion.Directory
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.copyTo
import kotlin.io.path.createTempDirectory

class HttpServerOutput(
	private var port: Int,
	private var externalAddress: String
) : ResourcePackOutput {
	private lateinit var pack: Path
	private lateinit var server: Http4kServer
	override fun outputPack(pack: Path) {
		val tempDir = newTempDirectory()
		this.pack = pack.copyTo(tempDir.resolve("pack.zip"))
		this.server = static(Directory(tempDir.absolute().toString()))
			.asServer(Jetty(port))
			.start()
		instance.logger.warn("Using internal http server. This is not recommended for security reasons!")
		println(tempDir.toString())
	}

	override fun getDownloadURL(): String = externalAddress.removeSuffix("/") + "/pack.zip"
	override fun getPackSha1(): ByteArray = getPackSha1(pack)
}
