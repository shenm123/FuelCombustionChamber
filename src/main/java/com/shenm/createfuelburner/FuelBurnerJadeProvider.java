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
        if (accessor.getBlockEntity() instanceof FuelBurnerBlockEntity burner) {
            if (burner.isLit) {
                if (burner.lavaTank.getFluidAmount() >= 1000) {
                    tooltip.add(Component.translatable("jade.create_fuel_burner.superheated").withStyle(ChatFormatting.AQUA));
                } else {
                    tooltip.add(Component.translatable("jade.create_fuel_burner.heated").withStyle(ChatFormatting.GOLD));
                }
            } else {
                tooltip.add(Component.translatable("jade.create_fuel_burner.inactive").withStyle(ChatFormatting.GRAY));
            }

            int lavaAmount = burner.lavaTank.getFluidAmount();
            tooltip.add(Component.translatable("jade.create_fuel_burner.lava_amount", lavaAmount).withStyle(ChatFormatting.RED));

            int seconds = burner.remainingBurnTime / 20;
            if (seconds > 0) {
                tooltip.add(Component.translatable("jade.create_fuel_burner.burn_time", seconds).withStyle(ChatFormatting.YELLOW));
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