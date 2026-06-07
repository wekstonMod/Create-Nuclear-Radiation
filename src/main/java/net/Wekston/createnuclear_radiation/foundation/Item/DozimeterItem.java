package net.Wekston.createnuclear_radiation.foundation.Item;

import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DozimeterItem extends Item {
    public DozimeterItem(Properties p_41383_) {
        super(p_41383_);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            double gettingRadiation = PlayerDataManager.gettingRadiation(player);
            double radiation = PlayerDataManager.getRadiation(player);
            double getImmunity = PlayerDataManager.getImmunity(player);
            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.1", String.format("%.2f", gettingRadiation)));
            ChatFormatting colorGettingRadiation;
            if (gettingRadiation < 1) colorGettingRadiation =ChatFormatting.GREEN;
            else if (gettingRadiation < 7) colorGettingRadiation =ChatFormatting.YELLOW;
            else colorGettingRadiation =ChatFormatting.RED;

            ChatFormatting colorRadiation;
            if (radiation < 50 * getImmunity) colorRadiation =ChatFormatting.GREEN;
            else if (radiation < 300 * getImmunity) colorRadiation =ChatFormatting.YELLOW;
            else colorRadiation =ChatFormatting.RED;

            Component ComponentGettingRadiation = Component.translatable(String.format("%.2f RAD/s", gettingRadiation)).withStyle(colorGettingRadiation);
            Component ComponentRadiaion = Component.translatable(String.format("%.2f RAD", radiation)).withStyle(colorRadiation);

            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.2", ComponentGettingRadiation));
            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.3", ComponentRadiaion));
            player.sendSystemMessage(Component.literal(" "));
        }
        player.getCooldowns().addCooldown(this, 15);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
