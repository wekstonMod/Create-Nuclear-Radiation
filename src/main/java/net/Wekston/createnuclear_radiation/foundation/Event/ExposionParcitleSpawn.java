package net.Wekston.createnuclear_radiation.foundation.Event;

import net.Wekston.createnuclear_radiation.CNRAllParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ExposionParcitleSpawn {
    public static void spawnExposionParticle(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            double radius = 3;
            double speed = 10.0;
            int count = 100;
            serverLevel.sendParticles(
                    CNRAllParticles.EXPLOSSION_REACTOR_1.get(),
                    pos.getX(), pos.getY(), pos.getZ(),
                    count,
                    radius, 0, radius,
                    speed
            );
        }
    }
}
