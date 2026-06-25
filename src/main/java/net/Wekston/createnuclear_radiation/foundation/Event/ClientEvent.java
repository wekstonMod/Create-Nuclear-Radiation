package net.Wekston.createnuclear_radiation.foundation.Event;

import net.Wekston.createnuclear_radiation.CNRAllParticles;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.foundation.particle.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = CreateNuclearRadiation.MODID, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void onParticleFactoryRegistry(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CNRAllParticles.EXPLOSION_DUST.get(), ExplosionDustParticle.Provider::new);
        event.registerSpriteSet(CNRAllParticles.EXPLOSION_SMOKE.get(), ExplosionSmokeParticle.Provider::new);
    }
}
