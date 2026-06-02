package net.Wekston.createnuclear_radiation.infrastructure.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class ChangeRadiationPlayer {
    static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("set")
                .requires(cs -> cs.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("count", DoubleArgumentType.doubleArg(-9999, 9999))
                                .executes(ctx -> runAddRadiation(ctx.getSource(), EntityArgument.getPlayers(ctx, "targets"), DoubleArgumentType.getDouble(ctx, "count")))
                ));
    }
    private static int runAddRadiation(CommandSourceStack source, Collection<ServerPlayer> player, double addRadiation) {
        for (ServerPlayer serverPlayer : player) {
            PlayerDataManager.setRadiation(serverPlayer, addRadiation);
            if (player.size() == 1) {
                source.sendSuccess(() -> {
                    return Component.translatable("createnuclear_radiation.command.change.player.single", serverPlayer.getDisplayName(), addRadiation);
                }, true);
            } else {
                source.sendSuccess(() -> {
                    return Component.translatable("createnuclear_radiation.command.change.player.multiple", player.size(), addRadiation);
                }, true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
