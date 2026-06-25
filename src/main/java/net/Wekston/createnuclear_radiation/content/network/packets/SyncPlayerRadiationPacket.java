package net.Wekston.createnuclear_radiation.content.network.packets;

import io.netty.buffer.ByteBuf;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncPlayerRadiationPacket(Double radiation, Double gettingradiation, double immunity, double immunityXP) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SyncPlayerRadiationPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CreateNuclearRadiation.MODID, "sync_packets"));

    public static final StreamCodec<ByteBuf, SyncPlayerRadiationPacket> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public SyncPlayerRadiationPacket decode(ByteBuf buf) {
                   double radiation = buf.readDouble();
                    double gettingradiation = buf.readDouble();
                    double immunity = buf.readDouble();
                    double immunityXP = buf.readDouble();

                    return new SyncPlayerRadiationPacket(radiation, gettingradiation, immunity, immunityXP);
                }

                @Override
                public void encode(ByteBuf buf, SyncPlayerRadiationPacket packet) {
                    buf.writeDouble(packet.radiation);
                    buf.writeDouble(packet.gettingradiation);
                    buf.writeDouble(packet.immunity);
                    buf.writeDouble(packet.immunityXP);
                }
            };

    public static void handle(SyncPlayerRadiationPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                PlayerDataManager.setRadiation(mc.player, packet.radiation);
                PlayerDataManager.setgettingRadiation(mc.player, packet.gettingradiation);
                PlayerDataManager.setImmunity(mc.player, packet.immunity);
                PlayerDataManager.setImmunityXP(mc.player, packet.immunityXP);
            }
        });
    }
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}