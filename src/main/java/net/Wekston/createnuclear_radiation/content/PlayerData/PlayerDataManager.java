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
                clientRadiationCache.settingRadiation(0);
                clientRadiationCache.setImmunityXP(0);
                clientRadiationCache.setImmunity(0.7);
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
        return getPlayerRadiation(player).gettingRadiation();
    }
    public static void setgettingRadiation(Player player, double num) {
        PlayerLevelsManager gettingRadiation = getPlayerRadiation(player);
        gettingRadiation.settingRadiation(num);
        savePlayerRadiation(player, gettingRadiation);
    }

    public static double getRadiation(Player player) {
        return getPlayerRadiation(player).getRadiation();
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


    public static double getImmunity(Player player) {
        return getPlayerRadiation(player).getImmunity();
    }
    public static void setImmunity(Player player, double num) {
        PlayerLevelsManager immunity = getPlayerRadiation(player);
        immunity.setImmunity(num);
        savePlayerRadiation(player, immunity);
    }

    public static double getImmunityXP(Player player) {
        return getPlayerRadiation(player).getImmunityXP();
    }
    public static void setImmunityXP(Player player, double num) {
        PlayerLevelsManager immunity = getPlayerRadiation(player);
        immunity.setImmunityXP(num);
        savePlayerRadiation(player, immunity);
    }
}