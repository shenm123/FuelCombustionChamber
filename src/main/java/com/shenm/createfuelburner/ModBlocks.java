package com.shenm.createfuelburner;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreateFuelBurnerMod.MODID);

    public static final DeferredBlock<Block> FUEL_BURNER = BLOCKS.register(
            "create_fuel_burner",
            () -> new FuelBurnerBlock(BlockBehaviour.Properties.of()
                    // 1. 硬度设置为 3.5F（和熔炉完全一样）
                    .strength(3.5F)
                    // 2. 必须用镐子挖才会掉落
                    .requiresCorrectToolForDrops()
                    // 3. 挖掘和放置音效设置为石头（原版熔炉也是石头音效）
                    .sound(SoundType.STONE)
                    // 4. 保留 noOcclusion 防止渲染时的穿模问题
                    .noOcclusion()
                    // 5. 动态光照：只要不是在 NONE 状态，就发出亮度为 13 的光（同燃烧的熔炉）
                    .lightLevel(state -> {
                        if (state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL)) {
                            return state.getValue(BlazeBurnerBlock.HEAT_LEVEL) != BlazeBurnerBlock.HeatLevel.NONE ? 13 : 0;
                        }
                        return 0;
                    })
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}