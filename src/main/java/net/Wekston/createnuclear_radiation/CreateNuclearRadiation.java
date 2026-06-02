package net.Wekston.createnuclear_radiation;

import com.simibubi.create.infrastructure.command.AllCommands;
import net.Wekston.createnuclear_radiation.content.network.NetworkHandler;
import net.Wekston.createnuclear_radiation.infrastructure.command.CNRAllCommands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        modEventBus.addListener(this::commonSetup);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }
}
