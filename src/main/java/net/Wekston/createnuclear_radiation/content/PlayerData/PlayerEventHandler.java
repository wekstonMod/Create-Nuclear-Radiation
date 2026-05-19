package net.Wekston.createnuclear_radiation.content.PlayerData;

import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.content.network.NetworkHandler;
import net.Wekston.createnuclear_radiation.content.network.packets.SyncPlayerRadiationPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateNuclearRadiation.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            CompoundTag persistentData = serverPlayer.getPersistentData();
            PlayerLevelsManager radiation;
            if (!persistentData.contains(PlayerDataManager.RADIATION_KEY)) {
                radiation = new PlayerLevelsManager();
                } else {
                radiation = new PlayerLevelsManager();
                CompoundTag radiationData = persistentData.getCompound(PlayerDataManager.RADIATION_KEY);
                radiation.loadFromNBT(radiationData);
            }
            persistentData.put(PlayerDataManager.RADIATION_KEY, radiation.saveToNBT());
            syncPlayerRadiation(serverPlayer);
        }
    }


    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerDataManager.setRadiation(serverPlayer, 0);
            PlayerDataManager.setgettingRadiation(serverPlayer, 0);
            syncPlayerRadiation(serverPlayer);
        }
    }
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            syncPlayerRadiation(serverPlayer);
        }
    }
    @SubscribeEvent
    public static void onPlayerSave(PlayerEvent.SaveToFile event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerLevelsManager radiation = PlayerDataManager.getPlayerRadiation(serverPlayer);
            CompoundTag persistentData = serverPlayer.getPersistentData();
            persistentData.put(PlayerDataManager.RADIATION_KEY, radiation.saveToNBT());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player original = event.getOriginal();
            Player newPlayer = event.getEntity();
            if (original instanceof ServerPlayer oldPlayer && newPlayer instanceof ServerPlayer newServerPlayer) {
                CompoundTag oldData = oldPlayer.getPersistentData();
                if (oldData.contains(PlayerDataManager.RADIATION_KEY)) {
                    CompoundTag radiationData = oldData.getCompound(PlayerDataManager.RADIATION_KEY);
                    newServerPlayer.getPersistentData().put(PlayerDataManager.RADIATION_KEY, radiationData);
                    }
            }
        }
    }

    public static void syncPlayerRadiation(ServerPlayer player) {
        if (player.connection == null) {
            return;
        }
        PlayerLevelsManager radiation = PlayerDataManager.getPlayerRadiation(player);
        NetworkHandler.sendToClient(player, new SyncPlayerRadiationPacket(
                radiation.getRadiation(),
                radiation.gettingRadiation()
        ));
    }
}