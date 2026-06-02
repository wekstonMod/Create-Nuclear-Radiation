package net.Wekston.createnuclear_radiation.content.BlockEntity;

import net.Wekston.createnuclear_radiation.CNRAllDamageSources;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerLevelsManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = CreateNuclearRadiation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RadioactiveBlockEvent {

    private static int radiationTick = 0;
    public static final Map<UUID, Double> playerMaxRadiation = new HashMap<>();
    public static boolean needRadiation = false;
    private static int tick = 0;
    private static double radiationCheck = 0;
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) {
            return;
        }
        tick++;
        if (tick < 20) return;
        tick = 0;
        applyRadiation(event.player);
        radiationTick++;
        Player player = event.player;
        Level level = player.level();
        double radiation = PlayerDataManager.getRadiation(player);
        Random random = new Random();
        if (radiationTick == 5) {
            radiationCheck = radiation;
        }
        if (radiationTick >= 10) {
            if (radiationCheck >= radiation) {
                PlayerDataManager.setgettingRadiation(player, 0);
            }
            if (radiation > 0 && PlayerDataManager.gettingRadiation(player) < 0.5) {
                PlayerDataManager.setRadiation(player, radiation - 0.01 * radiation);
            }
            radiationTick = 0;
            if (radiation > 200) {
                if (random.nextFloat() > 0.6) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, (int) (20 * radiation / 100), 0, false, false));
                }
                if (radiation >= 500 && random.nextFloat() < 0.45) {
                    player.hurt(CNRAllDamageSources.radiation(level), 100.0f);
                }
                if (radiation > 300 && random.nextFloat() < 0.2) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (10 * radiation / 80), 1, false, false));
                }
                if (radiation > 400 && random.nextFloat() > 0.8) {
                    player.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (20 * radiation / 50), 2, false, false));
                }
            }
        }
    }

    public static void RadiationPlayer(UUID playerID, double radiation) {
        Double currentMax = playerMaxRadiation.getOrDefault(playerID, 0.0);
        if (radiation >= currentMax) {
            playerMaxRadiation.put(playerID, radiation);
        }
        else if (radiation == 0) {
            playerMaxRadiation.put(playerID, radiation);
        }
        needRadiation = true;
    }
    public static void applyRadiation(Player player) {
        if (!needRadiation) return;
        for (Map.Entry<UUID, Double> entry : playerMaxRadiation.entrySet()) {
            double radiation = entry.getValue();
            if (player != null) {
                boolean isWearingAntiRadiationArmor = true;
                for (ItemStack armor : player.getArmorSlots()) {
                    if (!AntiRadiationArmorItem.Armor.isArmored2(armor)) {
                        isWearingAntiRadiationArmor = false;
                        break;
                    }
                }
                if (!isWearingAntiRadiationArmor) {
                    PlayerDataManager.addRadiation(player, radiation);
                    PlayerDataManager.setgettingRadiation(player, radiation);
                }
                else {
                    // Radiation protection in ARMOR
                    double saveRadiation;
                    if (radiation < 5) {
                        saveRadiation = 0.2 * radiation; // 20%
                    } else if (radiation < 7) {
                        saveRadiation = 0.4 * radiation; // 40%
                    } else if (radiation < 9) {
                        saveRadiation = 0.6 * radiation; // 60%
                    } else {
                        saveRadiation = 0.8 * radiation; // 80%
                    }
                    PlayerDataManager.addRadiation(player, saveRadiation);
                    PlayerDataManager.setgettingRadiation(player, saveRadiation);
                }
            }
        }
        playerMaxRadiation.clear();
        needRadiation = false;
    }
}
