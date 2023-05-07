package net.stellarica.packserve.output;

import com.velocitypowered.api.proxy.player.ResourcePackInfo
import net.stellarica.packserve.PackServe
import net.stellarica.packserve.source.ResourcePackSource
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.readBytes

interface ResourcePackOutput {
    fun outputPack(pack: Path)
    fun getDownloadURL(): String
    fun getPackSha1(): ByteArray

    fun getPackSha1(pack: Path) = MessageDigest
        .getInstance("SHA-1")
        .digest(pack.readBytes())
}
