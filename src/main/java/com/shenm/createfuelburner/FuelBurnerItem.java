package com.shenm.createfuelburner;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class FuelBurnerItem extends BlockItem {

    public FuelBurnerItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        // 检查玩家是否按下了 Shift 键
        if (Screen.hasShiftDown()) {
            // 如果按下了，显示详细描述（灰色字体）
            tooltipComponents.add(Component.translatable("tooltip.create_fuel_burner.desc.1").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.translatable("tooltip.create_fuel_burner.desc.2").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.translatable("tooltip.create_fuel_burner.desc.3").withStyle(ChatFormatting.GOLD));
        } else {
            // 如果没有按下，显示提示（深灰色字体）
            tooltipComponents.add(Component.translatable("tooltip.create_fuel_burner.hold_shift").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}