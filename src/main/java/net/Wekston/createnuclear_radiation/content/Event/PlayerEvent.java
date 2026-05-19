package net.Wekston.createnuclear_radiation.foundation.Event;

import net.Wekston.createnuclear_radiation.CNRAllBlocks;
import net.Wekston.createnuclear_radiation.CNRAllDamageSources;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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
        if (tick < 10) return;

        Player player = event.player;
        Level level = player.level();
        Inventory inventory = player.getInventory();
        for (int i = 0; i <= inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == CNRAllBlocks.RADIOACTIVE_BLOCK.get().asItem()) {
                player.hurt(CNRAllDamageSources.radiation(level), 100.0f);
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
}
