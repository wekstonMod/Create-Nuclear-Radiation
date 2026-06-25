package net.Wekston.createnuclear_radiation.foundation.mixin;

import net.Wekston.createnuclear_radiation.CNRAllBlocks;
import net.Wekston.createnuclear_radiation.CNRConfig;
import net.Wekston.createnuclear_radiation.foundation.Blocks.RadioActiveBlock;
import net.Wekston.createnuclear_radiation.foundation.Event.spawnExplosionParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.nuclearteam.createnuclear.content.multiblock.core.ReactorCoreEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Pseudo
@Mixin(value = ReactorCoreEntity.class, remap = false)
public class ReactorCoreEntityMixin {

    @Inject(
            method = {"explodeReactorCore"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void explodeReactorCore(Level world, BlockPos pos, CallbackInfo cir) {
        cir.cancel();
        Random random = new Random();

        // explode
        int intRadius = CNRConfig.COMMON.RadiusExplodeReactor.get();
        int radius = (int) Math.ceil(intRadius);
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = radius * 3; y >= -radius/2; --y) {
                    int Circle = x*x + z*z + (y*y) * 3;
                    if (Circle <= intRadius * intRadius) {
                        BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                        if (random.nextFloat() < 0.012) {
                            spawnExplosionParticles.spawnSmoke(world, blockPos);
                        }
                        if (y == 0) {
                            if (random.nextFloat() < 0.05f) {
                                int layer = random.nextInt(1, 4);
                                BlockState radiation = CNRAllBlocks.RADIOACTIVE_BLOCK.get().defaultBlockState().setValue(RadioActiveBlock.LAYERS, layer);
                                world.setBlock(blockPos, radiation, 3);
                            }
                        }
                    }
                }
            }
        }
        // sounds && particle
        world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 0f, Level.ExplosionInteraction.TNT);
        // layer
        int radiuslayer = (int) Math.ceil(intRadius + CNRConfig.COMMON.RadiusBurnt.get());
        for (int x = -radiuslayer; x <= radiuslayer; x++) {
            for (int z = -radiuslayer; z <= radiuslayer; z++) {
                for (int y = radiuslayer / 2 + 4; y >= -radiuslayer / 2 - 4; --y) {
                    if (x * x + z * z + y * y <= (intRadius + 4) * (intRadius + 4)) {
                        BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        BlockState state = world.getBlockState(blockPos);
                        if (!state.isAir() && !state.is(CNRAllBlocks.RADIOACTIVE_BLOCK.get())) {
                            BlockState layer;
                            layer = (random.nextFloat() > 0.5f) ? Blocks.BLACKSTONE.defaultBlockState() : Blocks.SMOOTH_BASALT.defaultBlockState();
                            world.setBlock(blockPos, layer, 3);
                        }
                    }
                }
            }
        }
        int radiusGrass = (int) Math.ceil(radius * CNRConfig.COMMON.RadiusDeathGrass.get() + CNRConfig.COMMON.RadiusDirt.get());
        for (int x = -radiusGrass; x <= radiusGrass; x++) {
            for (int z = -radiusGrass; z <= radiusGrass; z++) {
                for (int y = radiusGrass / 2; y >= -radiusGrass / 2; --y) {
                    int Circle = x * x + z * z + y * y;
                    if (Circle <= radiusGrass * radiusGrass) {
                        BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        BlockState state = world.getBlockState(blockPos);
                        if (state.getBlock() instanceof TallGrassBlock || state.getBlock() instanceof DoublePlantBlock || state.getBlock() instanceof FlowerBlock) {
                            BlockState fireState;
                            if (random.nextFloat() < 0.3) {
                                fireState = Blocks.FIRE.defaultBlockState();
                            }
                            else if (random.nextFloat() < 0.5) {
                                fireState = Blocks.DEAD_BUSH.defaultBlockState();
                            } else {
                                fireState = Blocks.AIR.defaultBlockState();
                            }
                            world.setBlock(blockPos, fireState, 3);
                        }
                        if (state.getBlock() instanceof LeavesBlock) {
                            BlockState airState = Blocks.AIR.defaultBlockState();
                            world.setBlock(blockPos, airState, 3);
                        }
                        if (Circle <= radius * radius * CNRConfig.COMMON.RadiusDeathGrass.get() * 2) {
                            if (state.is(BlockTags.LOGS)) {
                                BlockState logState = CNRAllBlocks.DEATH_LOG.get().defaultBlockState();
                                world.setBlock(blockPos, logState, 3);
                            }
                            if (state.getBlock() instanceof GrassBlock) {
                                BlockState dirtState = CNRAllBlocks.DEATH_GRASS_BLOCK.get().defaultBlockState();
                                world.setBlock(blockPos, dirtState, 3);
                            }
                        }
                        else {
                            if (state.getBlock() instanceof GrassBlock) {
                                BlockState dirtState = Blocks.DIRT.defaultBlockState();
                                world.setBlock(blockPos, dirtState, 3);
                            }
                        }
                    }
                }
            }
        }
        spawnExplosionParticles.spawnDust(world, pos);

        double radiusDamage = radius * (CNRConfig.COMMON.RadiusDeathGrass.get() + CNRConfig.COMMON.RadiusDirt.get());
        AABB area = new AABB(pos.getX() - radiusDamage, pos.getY() - radiusDamage, pos.getZ() - radiusDamage,
                pos.getX() + radiusDamage, pos.getY() + radiusDamage, pos.getZ() + radiusDamage);

        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, area);
        for (LivingEntity entity : entities) {
            double distanceEntity = entity.distanceToSqr(pos.getCenter());
            double distance = Math.sqrt(distanceEntity);
            float damage = (float) (100 * (1.0 - distance / radiusDamage));
            entity.hurt(entity.damageSources().explosion(null), damage);
        }
    }
}