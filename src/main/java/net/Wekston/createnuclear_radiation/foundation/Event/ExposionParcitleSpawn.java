package net.Wekston.createnuclear_radiation.foundation.Event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ExposionParcitleSpawn {
    public static void spawnExposionParticle(Level level, BlockPos pos, int Radius) {

        for (int radius = 1; radius <= Radius; radius++) {
            int finalRadius = radius;

            level.getServer().execute(() -> {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeExplosionParticle(level, pos, finalRadius);
            });
        }
    }
    public static void executeExplosionParticle(Level level, BlockPos pos, int radius) {
        final double countParticle = radius * 1.8;
        for (int i = 0; i < countParticle; i++) {
            double angle = 2 * Math.PI * i / countParticle;
            double xOffset = Math.cos(angle) * radius;
            double zOffset = Math.sin(angle) * radius;
            double yOffset = 0.2 + Math.random() * 0.6;
            Vec3 centerPos = Vec3.atCenterOf(pos);
            Vec3 particlePos = centerPos.add(xOffset, yOffset, zOffset);
            if (level instanceof ServerLevel serverLevel) {
                double outwardX = Math.cos(angle) * 0.5;
                double outwardZ = Math.sin(angle) * 0.5;
                if (radius % 3 == 0) {
                    serverLevel.sendParticles(
                            ParticleTypes.FLAME,
                            particlePos.x, particlePos.y, particlePos.z,
                            1,
                            outwardX, Math.random() * 0.2, outwardZ,
                            0.2
                    );
                }
                if (radius <= 50) {
                    double yOffsetRadius = Math.sin(angle) * radius;
                    double outwardY = Math.sin(angle) * 0.5;
                    Vec3 particlePosRadius = centerPos.add(xOffset, yOffsetRadius, zOffset);
                    serverLevel.sendParticles(
                            ParticleTypes.EXPLOSION_EMITTER,
                            particlePosRadius.x, particlePosRadius.y, particlePosRadius.z,
                            1,
                            outwardX, outwardY, outwardZ,
                            0.2
                    );
                } else {
                    serverLevel.sendParticles(
                            ParticleTypes.EXPLOSION_EMITTER,
                            particlePos.x, particlePos.y, particlePos.z,
                            1,
                            outwardX, Math.random() * 0.2, outwardZ,
                            0.2
                    );
                    BlockState state = level.getBlockState(BlockPos.containing(particlePos));
                    if (!state.isAir() && !state.is(Blocks.BEDROCK)) {
                        level.setBlock(BlockPos.containing(particlePos), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }
}
