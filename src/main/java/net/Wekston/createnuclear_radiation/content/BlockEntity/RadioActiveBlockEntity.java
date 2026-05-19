package net.Wekston.createnuclear_radiation.content.BlockEntity;

import net.Wekston.createnuclear_radiation.CNRAllBlockEntity;
import net.Wekston.createnuclear_radiation.CNRAllDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import java.util.List;
public class RadioActiveBlockEntity extends BlockEntity {

    public RadioActiveBlockEntity(BlockPos pos, BlockState state) {
        super(CNRAllBlockEntity.RADIOACTIVATE_BLOCKENTITY.get(), pos, state);
    }

    private int tick = 0;
    private static final int RADIUS = 100;

    public static void tick(Level level, BlockPos pos, BlockState state, RadioActiveBlockEntity blockEntity) {
        blockEntity.tick++;
        if (blockEntity.tick >= 10) {
            blockEntity.tick = 0;
            DistanceEntity(level, pos);
        }
    }


    public static void DistanceEntity(Level level, BlockPos pos) {
        AABB area = new AABB(pos.getX() - RADIUS, pos.getY() - RADIUS, pos.getZ() - RADIUS,
                pos.getX() + RADIUS, pos.getY() + RADIUS, pos.getZ() + RADIUS);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);
        for (LivingEntity entity : entities) {
            double distanceEntity = entity.distanceToSqr(pos.getCenter());
            double distance = Math.sqrt(distanceEntity);
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (player != null) {
                    if (distance < RADIUS && distance >= 0) {
                        double radiation = (10 * (1.0 - distance / RADIUS));
                        RadioactiveBlockEvent.RadiationPlayer(player.getUUID(), radiation);
                    }
                    else {
                        double radiation = 0;
                        RadioactiveBlockEvent.RadiationPlayer(player.getUUID(), radiation);

                    }
                }
            }else {
                if (distance < 20 * 20) {
                    entity.hurt(CNRAllDamageSources.radiation(level), (float) 2);
                }
            }
        }
    }
}
