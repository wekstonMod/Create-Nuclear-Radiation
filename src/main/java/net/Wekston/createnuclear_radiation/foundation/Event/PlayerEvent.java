package net.Wekston.createnuclear_radiation.foundation.Event;

import net.Wekston.createnuclear_radiation.CNRAllBlocks;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.Wekston.createnuclear_radiation.infrastructure.command.CNRAllCommands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import static net.Wekston.createnuclear_radiation.content.PlayerData.PlayerEventHandler.syncPlayerRadiation;

@Mod.EventBusSubscriber(modid = CreateNuclearRadiation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvent {
    private static int tick = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) {
            return;
        }
        tick++;
        if (tick < 20) return;
        Player player = event.player;
        if (!player.isCreative()) {
            Inventory inventory = player.getInventory();
            for (int i = 0; i <= inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.getItem() == CNRAllBlocks.RADIOACTIVE_BLOCK.get().asItem()) {
                    PlayerDataManager.setgettingRadiation(player, 50.0);
                    PlayerDataManager.addRadiation(player, 50.0);
                }
            }
        }
        if (event.player instanceof ServerPlayer serverPlayer) {
            syncPlayerRadiation(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = event.getItem();
            if (stack.getItem() == Items.BEETROOT) {
                double effect = PlayerDataManager.getRadiation(player) - PlayerDataManager.getRadiation(player) * 0.05;
                PlayerDataManager.setRadiation(player, effect);
            }
        }
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CNRAllCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerLogin(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        // For BETA-version
        player.sendSystemMessage(Component.translatable("createnuclear_radiation.system.beta"));
    }
}