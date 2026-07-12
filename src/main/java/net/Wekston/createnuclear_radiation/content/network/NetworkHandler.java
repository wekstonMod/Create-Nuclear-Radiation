package net.Wekston.createnuclear_radiation.content.network;

import net.Wekston.createnuclear_radiation.content.network.packets.SyncPlayerRadiationPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
public class NetworkHandler {
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                SyncPlayerRadiationPacket.TYPE,
                SyncPlayerRadiationPacket.STREAM_CODEC,
                SyncPlayerRadiationPacket::handle);

    }
    public static void sendToClient(ServerPlayer player, CustomPacketPayload packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }
}