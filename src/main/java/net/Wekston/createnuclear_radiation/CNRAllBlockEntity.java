package net.Wekston.createnuclear_radiation;

import net.Wekston.createnuclear_radiation.content.BlockEntity.RadioActiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CNRAllBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CreateNuclearRadiation.MODID);

    public static final RegistryObject<BlockEntityType<RadioActiveBlockEntity>> RADIOACTIVATE_BLOCKENTITY =
            BLOCK_ENTITIES.register("radioactivate_blockentity",
                    () -> BlockEntityType.Builder.of(
                            RadioActiveBlockEntity::new,
                            CNRAllBlocks.RADIOACTIVE_BLOCK.get()
                    ).build(null));

}
