package net.Wekston.createnuclear_radiation;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CNRCreativeModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateNuclearRadiation.MODID);

    public static final RegistryObject<CreativeModeTab> CREATE_NUCLEAR_RADIATION = CREATIVE_MODE_TABS.register("createnuclear_radiation_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(CNRAllItems.DOZIMETER.get()))
                    .title(Component.translatable("creativetab.createnuclear_radiation_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(CNRAllBlocks.RADIOACTIVE_BLOCK.get());
                        pOutput.accept(CNRAllBlocks.DEATH_GRASS_BLOCK.get());
                        pOutput.accept(CNRAllBlocks.DEATH_LOG.get());
                        pOutput.accept(CNRAllItems.DOZIMETER.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}