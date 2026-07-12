package net.Wekston.createnuclear_radiation.foundation.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ExplosionDustParticle extends TextureSheetParticle {
    protected ExplosionDustParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, SpriteSet p_106583_) {
        super(level, x, y, z);
        this.xd = vx;
        this.yd = 0;
        this.zd = vz;
        this.gravity = 0.25F;
        this.friction = 0.9F;
        this.lifetime = 20 * 20;
        this.quadSize = 5f;
        this.setSpriteFromAge(p_106583_);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        float progress = Mth.clamp((float) this.age / (float) this.lifetime, 0, 1);
        float alpha = this.lifetime == 0 || this.age >= this.lifetime ? 0 : 1 - progress * progress;
        this.setAlpha(alpha);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_106588_) {
            this.sprites = p_106588_;
        }


        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double vx,
                                       double vy, double vz) {
            return new ExplosionDustParticle(level, x, y, z, vx,
                    vy, vz, this.sprites);
        }
    }
}
