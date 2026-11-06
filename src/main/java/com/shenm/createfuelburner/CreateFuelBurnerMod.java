package com.shenm.createfuelburner;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList; // 🌟 新增导入：用于检查模组是否加载

@Mod(CreateFuelBurnerMod.MODID)
public class CreateFuelBurnerMod {
    public static final String MODID = "create_fuel_burner";

    public CreateFuelBurnerMod(IEventBus modEventBus, ModContainer modContainer) {
        // 注册方块、物品、方块实体和创造模式标签页
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        // 🌟 第六步：安全检查
        // 检查当前游戏环境里有没有加载 "jade" 这个模组
        if (ModList.get().isLoaded("jade")) {
            // 如果安装了 Jade，我们可以安全地做一些初始化
            // 注意：因为咱们的插件是通过 services 文件注册的，
            // 这里不需要手动注册插件，写这句只是为了让你看到它的逻辑
            System.out.println("[Create: Fuel Burner] 检测到 Jade 模组，已启用 Jade 信息显示适配。");
        } else {
            // 如果没有安装 Jade，什么也不做，模组照常运行
            System.out.println("[Create: Fuel Burner] 未检测到 Jade 模组，运行在无 Jade 模式。");
        }
    }
}