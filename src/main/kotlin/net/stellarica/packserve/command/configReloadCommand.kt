package net.stellarica.packserve.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import net.stellarica.packserve.PackServe

fun configReloadCommand() = BrigadierCommand(
	LiteralArgumentBuilder
		.literal<CommandSource>("packreload")
		.requires { source: CommandSource -> source.hasPermission("packserve.reload") }
		.executes {
			PackServe.instance.loadConfig()
			PackServe.instance.createPackManager()
			Command.SINGLE_SUCCESS
		}
		.build()
)
