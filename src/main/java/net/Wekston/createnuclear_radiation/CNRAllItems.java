package net.Wekston.createnuclear_radiation;

import net.Wekston.createnuclear_radiation.foundation.Item.DozimeterItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNRAllItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CreateNuclearRadiation.MODID);

    public static final DeferredItem<Item> DOZIMETER = ITEMS.register("dozimeter",
            () -> new DozimeterItem(new Item.Properties().stacksTo(1)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}