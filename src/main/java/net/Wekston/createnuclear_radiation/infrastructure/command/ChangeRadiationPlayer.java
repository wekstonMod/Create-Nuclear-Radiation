package net.Wekston.createnuclear_radiation.infrastructure.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.ChatFormatting;
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
                .then(Commands.literal("radiation")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("count", DoubleArgumentType.doubleArg(-9999, 9999))
                                .executes(ctx -> runChangeRadiation(ctx.getSource(), EntityArgument.getPlayers(ctx, "targets"), DoubleArgumentType.getDouble(ctx, "count"))))))
                .then(Commands.literal("immunity")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("count", DoubleArgumentType.doubleArg(0.1, 10))
                                        .executes(ctx -> runChangeImmunity(ctx.getSource(), EntityArgument.getPlayers(ctx, "targets"), DoubleArgumentType.getDouble(ctx, "count")))
                                )))


                ;
    }
    private static int runChangeRadiation(CommandSourceStack source, Collection<ServerPlayer> player, double addRadiation) {
        for (ServerPlayer serverPlayer : player) {
            PlayerDataManager.setRadiation(serverPlayer, addRadiation);
            if (player.size() == 1) {
                source.sendSuccess(() -> Component.translatable("createnuclear_radiation.command.change.radiation.single", serverPlayer.getDisplayName().copy().withStyle(ChatFormatting.GREEN), addRadiation), true);
            } else {
                source.sendSuccess(() -> Component.translatable("createnuclear_radiation.command.change.radiation.multiple", player.size(), addRadiation), true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }


    private static int runChangeImmunity(CommandSourceStack source, Collection<ServerPlayer> player, double numImmunity) {
        for (ServerPlayer serverPlayer : player) {
            PlayerDataManager.setImmunity(serverPlayer, numImmunity);
            if (player.size() == 1) {
                source.sendSuccess(() -> Component.translatable("createnuclear_radiation.command.change.immunity.single", serverPlayer.getDisplayName().copy().withStyle(ChatFormatting.GREEN), PlayerDataManager.getImmunity(serverPlayer)), true);
            } else {
                source.sendSuccess(() -> Component.translatable("createnuclear_radiation.command.change.immunity.multiple", player.size(), PlayerDataManager.getImmunity(serverPlayer)), true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
