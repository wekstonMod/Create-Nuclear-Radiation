package net.Wekston.createnuclear_radiation.content.Mixin;

import net.Wekston.createnuclear_radiation.CNRAllDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.nuclearteam.createnuclear.CNTags;
import net.nuclearteam.createnuclear.content.effects.RadiationEffect;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Pseudo
@Mixin(value = RadiationEffect.class)
public class RadiationEffectMixin {

    @Inject(
            method = {"applyEffectTick"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void applyEffectTick(LivingEntity livingEntity, int amplifier, CallbackInfo cir) {
        cir.cancel();
        if (livingEntity.getType().is(CNTags.CNEntityTags.IRRADIATED_IMMUNE.tag)) {
            MobEffect effect = (MobEffect) (Object) this;
            livingEntity.removeEffect(effect);
        } else {
            boolean isWearingAntiRadiationArmor = true;
            for (ItemStack armor : livingEntity.getArmorSlots()) {
                if (!AntiRadiationArmorItem.Armor.isArmored2(armor)) {
                    isWearingAntiRadiationArmor = false;
                    break;
                }
            }

            if (!isWearingAntiRadiationArmor || amplifier > 3) {
                Level level = livingEntity.level();
                int damage = 1 << amplifier;
                livingEntity.hurt(CNRAllDamageSources.radiation(level), (float) damage);
            }
        }
    }
}
