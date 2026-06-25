package net.Wekston.createnuclear_radiation;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CNRAllParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CreateNuclearRadiation.MODID);


    public static final RegistryObject<SimpleParticleType> EXPLOSION_DUST =
            PARTICLE_TYPE.register("explosion_dust",
                    () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> EXPLOSION_SMOKE =
            PARTICLE_TYPE.register("explosion_smoke",
                    () -> new SimpleParticleType(true));
}