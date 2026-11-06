package com.shenm.createfuelburner;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum FuelBurnerJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        tooltip.add(Component.literal("Jade 测试成功！").withStyle(ChatFormatting.GREEN));
        if (accessor.getBlockEntity() instanceof FuelBurnerBlockEntity burner) {
            if (burner.isLit) {
                if (burner.lavaTank.getFluidAmount() >= 1000) {
                    tooltip.add(Component.literal("超级加热 (Superheated)").withStyle(ChatFormatting.AQUA));
                } else {
                    tooltip.add(Component.literal("加热中 (Heated)").withStyle(ChatFormatting.GOLD));
                }
            } else {
                tooltip.add(Component.literal("未加热 (Inactive)").withStyle(ChatFormatting.GRAY));
            }

            int lavaAmount = burner.lavaTank.getFluidAmount();
            tooltip.add(Component.literal("熔岩: " + lavaAmount + " / 1000 mB").withStyle(ChatFormatting.RED));

            int seconds = burner.remainingBurnTime / 20;
            if (seconds > 0) {
                tooltip.add(Component.literal("剩余燃烧: " + seconds + " 秒").withStyle(ChatFormatting.YELLOW));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(CreateFuelBurnerMod.MODID, "fuel_burner_provider");
    }
    // 🌟 新增：控制该提供器的显示优先级（数值越大越靠下显示）
    @Override
    public int getDefaultPriority() {
        return 9999;
    }
}