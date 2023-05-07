package net.stellarica.packserve.output;

import com.velocitypowered.api.proxy.player.ResourcePackInfo
import net.stellarica.packserve.PackServe
import net.stellarica.packserve.source.ResourcePackSource
import java.security.MessageDigest
import kotlin.io.path.readBytes

interface ResourcePackOutput {
    var source: ResourcePackSource
    fun getDownloadURL(): String
    fun getPackSha1(): ByteArray = MessageDigest
        .getInstance("SHA-1")
        .digest(source.getPackZip().readBytes())
}
