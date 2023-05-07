package net.stellarica.packserve

import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.absolute
import kotlin.io.path.createDirectories
import kotlin.io.path.createTempDirectory
import kotlin.io.path.inputStream
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.outputStream
import kotlin.io.path.relativeTo
import kotlin.io.path.walk


@OptIn(ExperimentalPathApi::class)
fun zipDirectory(dir: Path, out: Path): Path {
	ZipOutputStream(out.outputStream().buffered()).use { z ->
		dir.walk().forEach { file ->
			val zipFileName = file.absolute().relativeTo(dir.absolute())
			val entry = ZipEntry( "$zipFileName${(if (file.isDirectory()) "/" else "" )}")
			z.putNextEntry(entry)
			if (file.isRegularFile()) {
				file.inputStream().use { f -> f.copyTo(z) }
			}
		}
	}
	return out
}

fun unzipFile(file: Path, out: Path) {
	out.createDirectories()
	ZipFile(file.toFile()).use {zip ->
		for (entry in zip.entries()) {
			val path = out.resolve(entry.name)
			path.parent.createDirectories()
			if (!entry.isDirectory) {
				path.parent.createDirectories()
				val outStream = path.outputStream().buffered()
				val inStream = zip.getInputStream(entry)
				inStream.copyTo(outStream)
				inStream.close()
				outStream.close()
			}
		}
	}
}

fun newTempDirectory(): Path {
	val dir = createTempDirectory()
	dir.toFile().deleteOnExit()
	return dir
}

fun newTempFile(): Path {
	val file = kotlin.io.path.createTempFile()
	file.toFile().deleteOnExit()
	return file
}