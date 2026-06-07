package net.Wekston.createnuclear_radiation.content.network.packets;

import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
public class SyncPlayerRadiationPacket {
    private final double radiation;
    private final double gettingradiation;
    private final double immunity;
    private final double immunityXP;

    public SyncPlayerRadiationPacket(double radiation, double gettingradiation, double immunity, double immunityXP) {
        this.radiation = radiation;
        this.gettingradiation = gettingradiation;
        this.immunity = immunity;
        this.immunityXP = immunityXP;
    }

    public static void encode(SyncPlayerRadiationPacket packet, FriendlyByteBuf buffer) {
        buffer.writeDouble(packet.radiation);
        buffer.writeDouble(packet.gettingradiation);
        buffer.writeDouble(packet.immunity);
        buffer.writeDouble(packet.immunityXP);
    }

    public static SyncPlayerRadiationPacket decode(FriendlyByteBuf buffer) {
        return new SyncPlayerRadiationPacket(
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble()
        );
    }

    public static void handle(SyncPlayerRadiationPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                PlayerDataManager.setRadiation(mc.player, packet.radiation);
                PlayerDataManager.setgettingRadiation(mc.player, packet.gettingradiation);
                PlayerDataManager.setImmunity(mc.player, packet.immunity);
                PlayerDataManager.setImmunityXP(mc.player, packet.immunityXP);
            }
        });
        context.setPacketHandled(true);
    }
}