package net.stellarica.packserve

import com.velocitypowered.api.proxy.player.ResourcePackInfo
import net.kyori.adventure.text.minimessage.MiniMessage
import net.stellarica.packserve.PackServe.Companion.instance
import net.stellarica.packserve.output.ResourcePackOutput
import net.stellarica.packserve.source.ResourcePackSource

class PackManager(
	private val source: ResourcePackSource,
	private val output: ResourcePackOutput
) {
	fun processPack() {
		val zip = source.getPackZip()
		output.outputPack(zip)
	}

	fun getPackInfo(): ResourcePackInfo =
		instance.server.createResourcePackBuilder(output.getDownloadURL())
			.setShouldForce(instance.config.markAsRequired)
			.setHash(output.getPackSha1())
			.setPrompt(instance.config.promptMessage.asMiniMessage())
			.build()

}