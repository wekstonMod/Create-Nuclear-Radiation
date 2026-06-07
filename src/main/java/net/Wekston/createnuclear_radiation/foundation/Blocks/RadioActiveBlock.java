package net.Wekston.createnuclear_radiation.foundation.Blocks;

import net.Wekston.createnuclear_radiation.CNRAllBlockEntity;
import net.Wekston.createnuclear_radiation.content.BlockEntity.RadioActiveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RadioActiveBlock extends FallingBlock implements EntityBlock {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;

    protected static final VoxelShape[] SHAPE = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public RadioActiveBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public VoxelShape getShape(BlockState p_56620_, BlockGetter p_56621_, BlockPos p_56622_, CollisionContext p_56623_) {
        return SHAPE[p_56620_.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_56625_, BlockGetter p_56626_, BlockPos p_56627_, CollisionContext p_56628_) {
        return SHAPE[p_56625_.getValue(LAYERS) - 1];
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState p_56632_, BlockGetter p_56633_, BlockPos p_56634_) {
        return SHAPE[p_56632_.getValue(LAYERS)];
    }

    @Override
    public @NotNull VoxelShape getVisualShape(BlockState p_56597_, BlockGetter p_56598_, BlockPos p_56599_, CollisionContext p_56600_) {
        return SHAPE[p_56597_.getValue(LAYERS)];
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_56630_) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState p_222453_, BlockGetter p_222454_, BlockPos p_222455_) {
        return p_222453_.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_56587_) {
        BlockState blockstate = p_56587_.getLevel().getBlockState(p_56587_.getClickedPos());
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
        } else {
            return super.getStateForPlacement(p_56587_);
        }
    }


    @Override
    public boolean canBeReplaced(BlockState p_56589_, BlockPlaceContext p_56590_) {
        int i = p_56589_.getValue(LAYERS);
        if (p_56590_.getItemInHand().is(this.asItem()) && i < 8) {
            if (p_56590_.replacingClickedOnBlock()) {
                return p_56590_.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RadioActiveBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }

        if (type == CNRAllBlockEntity.RADIOACTIVATE_BLOCKENTITY.get()) {
            return (lvl, pos, st, be) -> RadioActiveBlockEntity.tick(lvl, pos, st, (RadioActiveBlockEntity) be);
        }
        return null;
    }
}
