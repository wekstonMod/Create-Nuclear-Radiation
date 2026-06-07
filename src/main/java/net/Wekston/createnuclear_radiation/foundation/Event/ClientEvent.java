package net.Wekston.createnuclear_radiation.foundation.Event;

import net.Wekston.createnuclear_radiation.CNRAllParticles;
import net.Wekston.createnuclear_radiation.CreateNuclearRadiation;
import net.Wekston.createnuclear_radiation.foundation.particle.ExplosionReactor1Particle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateNuclearRadiation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void onParticleFactoryRegistry(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CNRAllParticles.EXPLOSSION_REACTOR_1.get(), ExplosionReactor1Particle.Provider::new);
    }
}
