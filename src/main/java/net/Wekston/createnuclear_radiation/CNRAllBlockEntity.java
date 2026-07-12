package net.Wekston.createnuclear_radiation;

import net.Wekston.createnuclear_radiation.content.BlockEntity.RadioActiveBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CNRAllBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateNuclearRadiation.MODID);

    public static final Supplier<BlockEntityType<RadioActiveBlockEntity>> RADIOACTIVATE_BLOCKENTITY =
            BLOCK_ENTITIES.register("radioactivate_blockentity",
                    () -> BlockEntityType.Builder.of(
                            RadioActiveBlockEntity::new,
                            CNRAllBlocks.RADIOACTIVE_BLOCK.get()
                    ).build(null));

}
