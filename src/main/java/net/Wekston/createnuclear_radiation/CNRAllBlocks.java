package net.Wekston.createnuclear_radiation;

import com.google.common.base.Supplier;
import net.Wekston.createnuclear_radiation.foundation.Blocks.RadioActiveBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CNRAllBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateNuclearRadiation.MODID);

    public static final RegistryObject<Block> RADIOACTIVE_BLOCK =
            registerBlock("radioactive_block",
                    () -> new RadioActiveBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).dynamicShape().lightLevel((p_220867_) -> {
                        return 10;})));

    public static final RegistryObject<Block> DEATH_GRASS_BLOCK =
            registerBlock("death_grass_block",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));

    public static final RegistryObject<Block> DEATH_PLANKS =
            registerBlock("death_planks",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> DEATH_LOG =
            registerBlock("death_log",
                    () -> log(MapColor.WOOD, MapColor.PODZOL));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return CNRAllItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static RotatedPillarBlock log(MapColor p_285370_, MapColor p_285126_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_285370_ : p_285126_;
        }).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }
}
