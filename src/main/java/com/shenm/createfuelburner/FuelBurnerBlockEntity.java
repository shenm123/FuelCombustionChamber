package com.shenm.createfuelburner;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class FuelBurnerBlockEntity extends BlazeBurnerBlockEntity {

    public int remainingBurnTime = 0;
    public boolean isLit = false;

    public final FluidTank lavaTank = new FluidTank(1000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            updateHeatLevel();
        }
    };

    public FuelBurnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FUEL_BURNER_BE.get(), pos, state);
    }

    public void tick() {
        if (level == null) return;

        // 客户端：生成粒子效果
        if (level.isClientSide) {
            BlockState state = getBlockState();
            if (state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL)) {
                BlazeBurnerBlock.HeatLevel heat = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);
                if (heat != BlazeBurnerBlock.HeatLevel.NONE) {
                    spawnParticles(heat);
                }
            }
            return;
        }

        // 服务端：逻辑倒计时
        if (remainingBurnTime > 0) {
            // 🌟 核心修改：实时判断当前有没有 1000mB 熔岩
            boolean hasLava = lavaTank.getFluidAmount() >= 1000;

            if (hasLava) {
                // 有熔岩：超级加热，每 tick 扣除 8 点（时间流逝快 8 倍）
                remainingBurnTime -= 8;
            } else {
                // 无熔岩：普通加热，每 tick 扣除 2 点（时间流逝快 2 倍）
                remainingBurnTime -= 2;
            }

            // 防止扣除过量变成负数
            if (remainingBurnTime < 0) {
                remainingBurnTime = 0;
            }

            isLit = true;
        } else {
            isLit = false;
        }

        updateHeatLevel();
    }

    // 根据加热等级生成不同的粒子
    private void spawnParticles(BlazeBurnerBlock.HeatLevel heat) {
        RandomSource random = level.random;

        double x = worldPosition.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
        double y = worldPosition.getY() + 1.0 + 0.1;
        double z = worldPosition.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.5;

        // 烟雾粒子
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.05, 0.0);

        if (heat == BlazeBurnerBlock.HeatLevel.SEETHING) {
            // 超级加热：灵魂火（蓝火） + 灵魂粒子
            if (random.nextFloat() < 0.6f) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0.0, 0.02, 0.0);
            }
            if (random.nextFloat() < 0.2f) {
                level.addParticle(ParticleTypes.SOUL, x, y, z, 0.0, 0.05, 0.0);
            }
        } else if (heat == BlazeBurnerBlock.HeatLevel.KINDLED) {
            // 普通加热：红火
            if (random.nextFloat() < 0.5f) {
                level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.01, 0.0);
            }
        }
    }

    public void updateHeatLevel() {
        if (level == null || level.isClientSide) return;

        boolean hasLava = lavaTank.getFluidAmount() >= 1000;
        BlazeBurnerBlock.HeatLevel newLevel;

        if (isLit && hasLava) {
            newLevel = BlazeBurnerBlock.HeatLevel.SEETHING;
        } else if (isLit) {
            newLevel = BlazeBurnerBlock.HeatLevel.KINDLED;
        } else {
            newLevel = BlazeBurnerBlock.HeatLevel.NONE;
        }

        BlockState state = getBlockState();
        if (state.getValue(BlazeBurnerBlock.HEAT_LEVEL) != newLevel) {
            level.setBlockAndUpdate(worldPosition, state.setValue(BlazeBurnerBlock.HEAT_LEVEL, newLevel));
        }
    }

    @Override
    public void write(@NotNull CompoundTag compound, HolderLookup.@NotNull Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putInt("BurnTime", remainingBurnTime);
        compound.putBoolean("IsLit", isLit);
        lavaTank.writeToNBT(registries, compound);
    }

    @Override
    protected void read(@NotNull CompoundTag compound, HolderLookup.@NotNull Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        remainingBurnTime = compound.getInt("BurnTime");
        isLit = compound.getBoolean("IsLit");
        lavaTank.readFromNBT(registries, compound);
    }
}