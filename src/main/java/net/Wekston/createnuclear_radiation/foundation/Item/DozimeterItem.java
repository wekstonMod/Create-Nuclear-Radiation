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

import java.util.Random;

public class DozimeterItem extends Item {
    public DozimeterItem(Properties p_41383_) {
        super(p_41383_);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            double gettingRadiation = PlayerDataManager.gettingRadiation(player);
            double radiation = PlayerDataManager.getRadiation(player);
            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.1", String.format("%.2f", gettingRadiation)));
            ChatFormatting color1;
            if (gettingRadiation < 1) color1 =ChatFormatting.GREEN;
            else if (gettingRadiation < 7) color1 =ChatFormatting.YELLOW;
            else color1 =ChatFormatting.RED;
            Component colorComponent1 = Component.translatable(String.format("%.2f RAD/s", gettingRadiation)).withStyle(color1);
            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.2", colorComponent1));

            ChatFormatting color2;
            if (radiation < 50) color2 =ChatFormatting.GREEN;
            else if (radiation < 300) color2 =ChatFormatting.YELLOW;
            else color2 =ChatFormatting.RED;
            Component colorComponent2 = Component.translatable(String.format("%.2f RAD", radiation)).withStyle(color2);
            player.sendSystemMessage(Component.translatable("createnuclear_radiation.dozimeter.3", colorComponent2));
            player.sendSystemMessage(Component.literal(" "));
        }
        player.getCooldowns().addCooldown(this, 15);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
