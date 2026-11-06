package com.shenm.createfuelburner;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(CreateFuelBurnerMod.MODID)
public class CreateFuelBurnerMod {
    public static final String MODID = "create_fuel_burner";

    public CreateFuelBurnerMod(IEventBus modEventBus, ModContainer modContainer) {
        // 注册方块、物品、方块实体和创造模式标签页
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
    }
}