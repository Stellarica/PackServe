package com.example.velocityplugin

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import net.stellarica.packserve.PackServe.Companion.instance

fun packPromptCommand() = BrigadierCommand(
	LiteralArgumentBuilder
		.literal<CommandSource>("packprompt")
		.requires { source: CommandSource -> source is Player }
		.executes { context: CommandContext<CommandSource> ->
			instance.manager.sendPackPrompt(context.source as Player)
			Command.SINGLE_SUCCESS
		}
		.build()
)
