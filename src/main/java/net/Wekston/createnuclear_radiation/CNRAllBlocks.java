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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNRAllBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateNuclearRadiation.MODID);


    public static final DeferredBlock<Block> RADIOACTIVE_BLOCK =
            registerBlock("radioactive_block",
                    () -> new RadioActiveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).
                            requiresCorrectToolForDrops().strength(5.0F, 6.0F).
                            sound(SoundType.METAL).dynamicShape().lightLevel((p_220867_) -> {
                        return 10;})));

    public static final DeferredBlock<Block> DEATH_GRASS_BLOCK =
            registerBlock("death_grass_block",
                    () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).randomTicks().
                            strength(0.6F).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> DEATH_PLANKS =
            registerBlock("death_planks",
                    () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));

    public static final DeferredBlock<Block> DEATH_LOG =
            registerBlock("death_log",
                    () -> log(MapColor.WOOD, MapColor.PODZOL));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        CNRAllItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static RotatedPillarBlock log(MapColor p_285370_, MapColor p_285126_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_285370_ : p_285126_;
        }).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }
}
