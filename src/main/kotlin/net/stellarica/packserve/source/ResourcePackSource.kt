package net.stellarica.packserve.source

import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.readBytes

interface ResourcePackSource {
	fun getPackZip(): Path

	fun getPackSha1(): ByteArray = MessageDigest
		.getInstance("SHA-1")
		.digest(getPackZip().readBytes())
}