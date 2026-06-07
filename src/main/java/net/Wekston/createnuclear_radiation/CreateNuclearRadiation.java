package net.Wekston.createnuclear_radiation;

import net.Wekston.createnuclear_radiation.content.network.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateNuclearRadiation.MODID)
public class CreateNuclearRadiation
{
    public static final String MODID = "createnuclear_radiation";

    public CreateNuclearRadiation(FMLJavaModLoadingContext context)
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        IEventBus modEventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        CNRAllParticles.PARTICLE_TYPE.register(modEventBus);
        CNRAllBlocks.BLOCKS.register(modEventBus);
        CNRAllItems.ITEMS.register(modEventBus);
        CNRAllBlockEntity.BLOCK_ENTITIES.register(modEventBus);
        CNRCreativeModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }
}
