package net.Wekston.createnuclear_radiation;

import net.Wekston.createnuclear_radiation.content.network.NetworkHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(CreateNuclearRadiation.MODID)
public class CreateNuclearRadiation
{
    public static final String MODID = "createnuclear_radiation";

    public CreateNuclearRadiation(IEventBus modEventBus, ModContainer container)
    {
        container.registerConfig(ModConfig.Type.COMMON, CNRConfig.COMMON_SPEC);
        modEventBus.addListener(NetworkHandler::register);
        CNRAllBlocks.BLOCKS.register(modEventBus);
        CNRAllParticles.PARTICLE_TYPE.register(modEventBus);
        CNRAllItems.ITEMS.register(modEventBus);
        CNRAllBlockEntity.BLOCK_ENTITIES.register(modEventBus);
        CNRCreativeModTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }
}
