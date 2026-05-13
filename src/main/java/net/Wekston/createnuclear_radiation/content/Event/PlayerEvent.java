package net.Wekston.createnuclear_radiation.content.Event;

import net.Wekston.createnuclear_radiation.CNRAllBlocks;
import net.Wekston.createnuclear_radiation.CNRAllDamageSources;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateNuclearRadiation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvent {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) {
            return;
        }
        Player player = event.player;
        Level level = player.level();

        Inventory inventory = player.getInventory();
        for (int i = 0; i <= inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == CNRAllBlocks.RADIOACTIVE_BLOCK.get().asItem()) {
                player.hurt(CNRAllDamageSources.radiation(level), 100.0f);
            }
        }
    }
}
