package net.Wekston.createnuclear_radiation.content.PlayerData;

import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlayerDataManager {
    public static final String RADIATION_KEY = CreateNuclearRadiation.MODID;
    @OnlyIn(Dist.CLIENT)
    private static PlayerLevelsManager clientRadiationCache;
    public static PlayerLevelsManager getPlayerRadiation(Player player) {
        if (player.level().isClientSide) {
            if (clientRadiationCache == null) {
                clientRadiationCache = new PlayerLevelsManager();
                clientRadiationCache.setRadiation(0);
                clientRadiationCache.setgettingRadiation(0);
            }
            return clientRadiationCache;
        } else {
            CompoundTag persistentData = player.getPersistentData();
            PlayerLevelsManager radiation = new PlayerLevelsManager();
            if (persistentData.contains(RADIATION_KEY)) {
                radiation.loadFromNBT(persistentData.getCompound(RADIATION_KEY));
            } else {
                CompoundTag RadiationData = radiation.saveToNBT();
                persistentData.put(RADIATION_KEY, RadiationData);
            }

            return radiation;
        }
    }

    public static void savePlayerRadiation(Player player, PlayerLevelsManager radiation) {
        if (player.level().isClientSide) {
            clientRadiationCache = radiation;
        } else {
            CompoundTag persistentData = player.getPersistentData();
            persistentData.put(RADIATION_KEY, radiation.saveToNBT());
        }
    }

    public static double gettingRadiation(Player player) {
        double radiation = getPlayerRadiation(player).gettingRadiation();
        return radiation;
    }

    public static void setgettingRadiation(Player player, double num) {
        PlayerLevelsManager radiation = getPlayerRadiation(player);
        radiation.setgettingRadiation(num);
        savePlayerRadiation(player, radiation);
    }

    public static double getRadiation(Player player) {
        double num = getPlayerRadiation(player).getRadiation();
        return num;
    }

    public static void setRadiation(Player player, double num) {
        PlayerLevelsManager radiation = getPlayerRadiation(player);
        radiation.setRadiation(num);
        savePlayerRadiation(player, radiation);
    }

    public static void addRadiation(Player player, double num) {
        PlayerLevelsManager radiation = getPlayerRadiation(player);
        radiation.addRadiation(num);
        savePlayerRadiation(player, radiation);
    }
}