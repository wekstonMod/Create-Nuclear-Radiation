package net.Wekston.createnuclear_radiation.foundation.Event;

import net.Wekston.createnuclear_radiation.CNRAllParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class spawnExplosionParticles {
    public static void spawnDust(Level level, BlockPos pos) {
        new Thread(() -> {
            try {
                Thread.sleep(100);
                if (level instanceof ServerLevel serverLevel) {
                    double radius = 3;
                    double speed = 10.0;
                    int count = 100;
                    for (ServerPlayer serverPlayer : serverLevel.players()) {
                        serverLevel.sendParticles(serverPlayer,
                                CNRAllParticles.EXPLOSION_DUST.get(), true,
                                pos.getX(), pos.getY() + 4, pos.getZ(),
                                count,
                                radius, 0, radius,
                                speed);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void spawnSmoke(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            double radius = 0;
            double speed = 0.0;
            for (ServerPlayer serverPlayer : serverLevel.players()) {
                serverLevel.sendParticles(serverPlayer,
                        CNRAllParticles.EXPLOSION_SMOKE.get(), true,
                        pos.getX(), pos.getY(), pos.getZ(),
                        1,
                        radius, radius, radius,
                        speed);
            }
        }
    }
}
