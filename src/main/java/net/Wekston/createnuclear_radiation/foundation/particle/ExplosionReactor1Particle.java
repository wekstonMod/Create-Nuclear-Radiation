package net.Wekston.createnuclear_radiation.foundation.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExplosionReactor1Particle extends TextureSheetParticle {
    private final SpriteSet sprites;
    protected ExplosionReactor1Particle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, SpriteSet p_106583_) {
        super(level, x, y, z);
        this.sprites = p_106583_;
        this.xd = vx;
        this.yd = 0;
        this.zd = vz;
        this.gravity = 0.25F;
        this.friction = 0.9F;
        this.lifetime = 20 * 20;
        this.quadSize = 5f;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_106588_) {
            this.sprites = p_106588_;
        }


        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double vx,
                                       double vy, double vz) {
            return new ExplosionReactor1Particle(level, x, y, z, vx,
                    vy, vz, this.sprites);
        }
    }
}
