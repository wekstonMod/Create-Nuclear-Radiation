package net.Wekston.createnuclear_radiation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateNuclearRadiation.MODID)
public class CreateNuclearRadiation
{
    public static final String MODID = "createnuclear_radiation";

    public CreateNuclearRadiation(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        CNRAllBlocks.BLOCKS.register(modEventBus);
        CNRAllItems.ITEMS.register(modEventBus);
        CNRAllBlockEntity.BLOCK_ENTITIES.register(modEventBus);
        CNRCreativeModTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }

}
