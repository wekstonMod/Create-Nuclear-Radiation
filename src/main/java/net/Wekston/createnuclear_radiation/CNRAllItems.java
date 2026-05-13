package net.Wekston.createnuclear_radiation;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CNRAllItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateNuclearRadiation.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
