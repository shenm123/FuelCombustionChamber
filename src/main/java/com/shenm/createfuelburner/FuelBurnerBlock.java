package com.shenm.createfuelburner;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class FuelBurnerBlock extends BlazeBurnerBlock {

    public FuelBurnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(
                BlockStateProperties.HORIZONTAL_FACING,
                context.getHorizontalDirection().getOpposite()
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FuelBurnerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, st, be) -> {
            if (be instanceof FuelBurnerBlockEntity burner) {
                burner.tick();
            }
        };
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        super.attack(state, level, pos, player);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof FuelBurnerBlockEntity burner)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (level.isClientSide) return ItemInteractionResult.SUCCESS;

        // 1. 处理流体（桶）交互
        if (stack.is(Items.BUCKET) || stack.is(Items.LAVA_BUCKET)) {
            IFluidHandler fluidHandler = burner.lavaTank;
            int beforeAmount = fluidHandler.getFluidInTank(0).getAmount();

            if (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) {
                int afterAmount = fluidHandler.getFluidInTank(0).getAmount();

                // 🌟 播放流体音效 (音量从 1.0F 改为 0.5F)
                if (afterAmount > beforeAmount) {
                    level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 0.5F, 1.0F);
                } else if (afterAmount < beforeAmount) {
                    level.playSound(null, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 0.5F, 1.0F);
                }

                burner.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
                burner.updateHeatLevel();
                return ItemInteractionResult.SUCCESS;
            } else {
                return ItemInteractionResult.FAIL;
            }
        }

        // 2. 处理燃料交互
        int burnTime = stack.getBurnTime(null);
        if (burnTime > 0) {
            burner.remainingBurnTime += burnTime;
            burner.isLit = true;

            if (!player.isCreative()) {
                stack.shrink(1);
            }

            // 🌟 播放烈焰人射击音效 (音量从 1.0F 改为 0.5F)
            level.playSound(null, pos, SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS, 0.2F, 0.8F);

            burner.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
            burner.updateHeatLevel();
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}