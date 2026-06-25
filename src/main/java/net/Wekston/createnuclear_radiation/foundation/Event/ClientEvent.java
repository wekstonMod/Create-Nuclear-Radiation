package net.Wekston.createnuclear_radiation.foundation.Event;

import net.Wekston.createnuclear_radiation.CNRAllParticles;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.foundation.particle.ExplosionDustParticle;
import net.Wekston.createnuclear_radiation.foundation.particle.ExplosionSmokeParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateNuclearRadiation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void onParticleFactoryRegistry(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CNRAllParticles.EXPLOSION_DUST.get(), ExplosionDustParticle.Provider::new);
        event.registerSpriteSet(CNRAllParticles.EXPLOSION_SMOKE.get(), ExplosionSmokeParticle.Provider::new);
    }
}
