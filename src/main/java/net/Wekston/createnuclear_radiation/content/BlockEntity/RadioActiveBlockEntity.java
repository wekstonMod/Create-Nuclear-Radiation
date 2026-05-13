package net.Wekston.createnuclear_radiation.content.BlockEntity;

import net.Wekston.createnuclear_radiation.CNRAllBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.nuclearteam.createnuclear.CNEffects;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem;

import java.util.List;

public class RadioActiveBlockEntity extends BlockEntity {

    public RadioActiveBlockEntity(BlockPos pos, BlockState state) {
        super(CNRAllBlockEntity.RADIOACTIVATE_BLOCKENTITY.get(), pos, state);
    }

    private int tick;
    private static final int RADIUS = 100;

    public static void tick(Level level, BlockPos pos, BlockState state, RadioActiveBlockEntity blockEntity) {
        if (blockEntity.tick < 100) {
            blockEntity.tick++;
        } else {
            LivingRadioactive(level, pos);
            blockEntity.tick = 0;
        }
    }

    public static void LivingRadioactive(Level level, BlockPos pos) {
        AABB area = new AABB(pos.getX() - RADIUS, pos.getY() - RADIUS, pos.getZ() - RADIUS,
                pos.getX() + RADIUS, pos.getY() + RADIUS, pos.getZ() + RADIUS);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);
        for (LivingEntity entity : entities) {
            double distance = entity.distanceToSqr(pos.getCenter());
            boolean isWearingAntiRadiationArmor = true;
            for (ItemStack armor : entity.getArmorSlots()) {
                if (!AntiRadiationArmorItem.Armor.isArmored2(armor)) {
                    isWearingAntiRadiationArmor = false;
                    break;
                }
            }
            if (!isWearingAntiRadiationArmor || distance < 10 * 10) {
                if (distance <= 10 * 10) {
                    entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200,
                            4, false, false, false));

                    entity.addEffect(new MobEffectInstance(CNEffects.RADIATION.get(), 100,
                            3, false, false, false));
                }
                if (distance <= 20 * 20) {
                    entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100,
                            2, false, false, false));

                    entity.addEffect(new MobEffectInstance(CNEffects.RADIATION.get(), 100,
                            2, false, false, false));
                }
                if (distance <= 30 * 30) {
                    entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100,
                            1, false, false, false));

                    entity.addEffect(new MobEffectInstance(CNEffects.RADIATION.get(), 100,
                            1, false, false, false));
                }
                if (distance <= RADIUS * RADIUS) {
                    entity.addEffect(new MobEffectInstance(CNEffects.RADIATION.get(), 100,
                            0, false, false, false));
                }
            }
        }
    }
}
