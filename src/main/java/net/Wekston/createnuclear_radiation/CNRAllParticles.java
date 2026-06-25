package net.Wekston.createnuclear_radiation;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CNRAllParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE =
            DeferredRegister.create(Registries.PARTICLE_TYPE, CreateNuclearRadiation.MODID);

    public static final Supplier<SimpleParticleType> EXPLOSION_DUST =
            PARTICLE_TYPE.register("explosion_dust",
                    () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> EXPLOSION_SMOKE =
            PARTICLE_TYPE.register("explosion_smoke",
                    () -> new SimpleParticleType(true));
}