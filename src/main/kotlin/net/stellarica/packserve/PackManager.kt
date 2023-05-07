package net.stellarica.packserve

import com.velocitypowered.api.proxy.player.ResourcePackInfo
import net.kyori.adventure.text.Component
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
		PackServe.instance.server.createResourcePackBuilder(output.getDownloadURL())
			.setHash(output.getPackSha1())
			.setPrompt(Component.text(PackServe.instance.config.promptMessage))
			.build()

}