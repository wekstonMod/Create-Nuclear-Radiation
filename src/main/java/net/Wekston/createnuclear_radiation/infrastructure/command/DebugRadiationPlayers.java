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
        double getImmunity = player.stream().mapToDouble(PlayerDataManager::getImmunity).sum() / player.size();
        double getImmunityXP = player.stream().mapToDouble(PlayerDataManager::getImmunityXP).sum() / player.size();

        source.sendSuccess(() -> {
            Component sizePlayer;
            Component radiationPlayer;
            Component gettingRadiationPlayer;
            Component Immunity;
            Component ImmunityXP;

            ChatFormatting color1;
            if (gettingRadiation < 1) color1 =ChatFormatting.GREEN;
            else if (gettingRadiation < 7) color1 =ChatFormatting.YELLOW;
            else color1 =ChatFormatting.RED;
            Component colorComponent1 = Component.literal(String.format("%.2f RAD/s\n", gettingRadiation)).withStyle(color1);

            ChatFormatting color2;
            if (radiation < 50 * getImmunity) color2 =ChatFormatting.GREEN;
            else if (radiation < 300 * getImmunity) color2 =ChatFormatting.YELLOW;
            else color2 =ChatFormatting.RED;
            Component colorComponent2 = Component.literal(String.format("%.2f RAD\n", radiation)).withStyle(color2);



            ChatFormatting color3;
            if (getImmunity > 1.1) color3 =ChatFormatting.GREEN;
            else if (getImmunity > 0.9) color3 =ChatFormatting.YELLOW;
            else color3 = ChatFormatting.RED;
            Component colorComponent3 = Component.literal(String.format("%.1f \uD83D\uDEE1\n", getImmunity)).withStyle(color3);
            String string4 = String.format("§a%.1f/%.1f✨\n", getImmunityXP,  getImmunity * 300);

            if (player.size() == 1) {
                sizePlayer = Component.translatable("createnuclear_radiation.command.info.single", player.iterator().next().getDisplayName().copy().withStyle(ChatFormatting.GREEN));
                radiationPlayer = Component.translatable("createnuclear_radiation.command.info.radiation.single", colorComponent1);
                gettingRadiationPlayer = Component.translatable("createnuclear_radiation.command.info.gettingradiation.single", colorComponent2);
                Immunity = Component.translatable("createnuclear_radiation.command.info.immunity.single", colorComponent3);
                ImmunityXP = Component.translatable("createnuclear_radiation.command.info.immunity_xp.single",  string4);
            }
            else {
                sizePlayer = Component.translatable("createnuclear_radiation.command.info.multiple", player.size());
                radiationPlayer = Component.translatable("createnuclear_radiation.command.info.radiation.multiple", colorComponent1);
                gettingRadiationPlayer = Component.translatable("createnuclear_radiation.command.info.gettingradiation.multiple", colorComponent2);
                Immunity = Component.translatable("createnuclear_radiation.command.info.immunity.multiple", colorComponent3);
                ImmunityXP = Component.translatable("createnuclear_radiation.command.info.immunity_xp.multiple", getImmunityXP, (int) getImmunity * 300);
            }
            return Component.empty()
                    .append(sizePlayer)
                    .append(radiationPlayer)
                    .append(gettingRadiationPlayer)
                    .append(Immunity).append(ImmunityXP);
        }, false);
        return Command.SINGLE_SUCCESS;
    }
}
