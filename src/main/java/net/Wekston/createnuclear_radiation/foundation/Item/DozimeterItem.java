package net.Wekston.createnuclear_radiation.foundation.Item;

import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DozimeterItem extends Item {
    public DozimeterItem(Properties p_41383_) {
        super(p_41383_);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            Random random = new Random();
            double rad = PlayerDataManager.gettingRadiation(player);
            double radiation = 0;
            if (rad == 0) {
                radiation += random.nextDouble(0, 0.09);
            }
            else {
                radiation = rad;
            }
            double radiationStats = PlayerDataManager.getRadiation(player);
            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.1", String.format("%.2f", radiation)));
            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.2", String.format("%.2f", radiationStats)));
        }
        player.getCooldowns().addCooldown(this, 15);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
