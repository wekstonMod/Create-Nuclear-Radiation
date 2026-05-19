package net.Wekston.createnuclear_radiation;

import net.Wekston.createnuclear_radiation.foundation.Item.DozimeterItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CNRAllItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateNuclearRadiation.MODID);

    public static final RegistryObject<Item> DOZIMETER = ITEMS.register("dozimeter",
            () -> new DozimeterItem(new Item.Properties().stacksTo(1)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
