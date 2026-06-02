package net.Wekston.createnuclear_radiation.infrastructure.command;

import com.mojang.brigadier.CommandDispatcher;
import com.simibubi.create.infrastructure.command.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CNRAllCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("radiation")
                .requires(cs -> cs.hasPermission(2))
                .then(ChangeRadiationPlayer.register())
                .then(DebugRadiationPlayers.register()));
    }
}
