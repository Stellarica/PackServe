package net.stellarica.packserve

import com.velocitypowered.api.proxy.Player
import net.stellarica.packserve.PackServe.Companion.instance
import net.stellarica.packserve.output.ResourcePackOutput
import net.stellarica.packserve.source.ResourcePackSource

class PackManager(
	source: ResourcePackSource,
	private val output: ResourcePackOutput
) {
	init {
		val zip = source.getPackZip()
		output.start(zip)
	}

	fun sendPackPrompt(player: Player) {
		val pack = instance.server.createResourcePackBuilder(output.getDownloadURL())
			.setShouldForce(instance.config.markAsRequired)
			.setHash(output.getPackSha1())
			.setPrompt(instance.config.promptMessage.asMiniMessage())
			.build()
		if (player.appliedResourcePack != pack && player.pendingResourcePack != pack)
			player.sendResourcePackOffer(pack)
	}

	fun stop() {
		output.stop()
	}
}