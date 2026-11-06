package com.shenm.createfuelburner;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    // 创建一个专门用于注册标签页的注册器
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateFuelBurnerMod.MODID);

    // 注册我们的专属标签页
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FUEL_BURNER_TAB =
            CREATIVE_MODE_TABS.register("fuel_burner_tab", () -> CreativeModeTab.builder()
                    // 标签页的名字（需要在语言文件里配置）
                    .title(Component.translatable("itemGroup.create_fuel_burner"))
                    // 标签页的图标（用我们的方块当图标）
                    .icon(() -> new ItemStack(ModBlocks.FUEL_BURNER.get()))
                    // 往标签页里放东西
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.FUEL_BURNER_ITEM.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}