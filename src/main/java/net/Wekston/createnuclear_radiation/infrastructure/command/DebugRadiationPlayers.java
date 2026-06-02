package net.Wekston.createnuclear_radiation.infrastructure.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class DebugRadiationPlayers {
    static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("info")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("targets", EntityArgument.players())
                        .executes(ctx -> runAddRadiation(ctx.getSource(), EntityArgument.getPlayers(ctx, "targets")))
                );
    }
    private static int runAddRadiation(CommandSourceStack source, Collection<ServerPlayer> player) {
        double radiation = player.stream().mapToDouble(PlayerDataManager::getRadiation).sum() / player.size();
        double gettingRadiation = player.stream().mapToDouble(PlayerDataManager::gettingRadiation).sum() / player.size();
        source.sendSuccess(() -> {
            Component sizePlayer;
            Component radiationPlayer;
            Component gettingRadiationPlayer;

            ChatFormatting color1;
            if (gettingRadiation < 1) color1 =ChatFormatting.GREEN;
            else if (gettingRadiation < 7) color1 =ChatFormatting.YELLOW;
            else color1 =ChatFormatting.RED;
            Component colorComponent1 = Component.translatable(String.format("%.2f RAD/s\n", gettingRadiation)).withStyle(color1);

            ChatFormatting color2;
            if (radiation < 50) color2 =ChatFormatting.GREEN;
            else if (radiation < 300) color2 =ChatFormatting.YELLOW;
            else color2 =ChatFormatting.RED;
            Component colorComponent2 = Component.translatable(String.format("%.2f RAD\n", radiation)).withStyle(color2);
            if (player.size() == 1) {
                sizePlayer = Component.translatable("createnuclear_radiation.command.info.single", player.iterator().next().getDisplayName());
                radiationPlayer = Component.translatable("createnuclear_radiation.command.info.radiation.single", colorComponent1);
                gettingRadiationPlayer = Component.translatable("createnuclear_radiation.command.info.gettingradiation.single", colorComponent2);
            }
            else {
                sizePlayer = Component.translatable("createnuclear_radiation.command.info.multiple", player.size());
                radiationPlayer = Component.translatable("createnuclear_radiation.command.info.radiation.multiple", colorComponent1);
                gettingRadiationPlayer = Component.translatable("createnuclear_radiation.command.info.gettingradiation.multiple", colorComponent2);
            }
            return Component.empty()
                    .append(sizePlayer)
                    .append(radiationPlayer)
                    .append(gettingRadiationPlayer);
        }, false);
        return Command.SINGLE_SUCCESS;
    }
}
