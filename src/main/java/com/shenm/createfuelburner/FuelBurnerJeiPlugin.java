package com.shenm.createfuelburner;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class FuelBurnerJeiPlugin implements IModPlugin {

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(CreateFuelBurnerMod.MODID, "jei_plugin");

    // 🌟 直接定义 JEI 需要的工作盆配方类型
    private static final RecipeType<BasinRecipe> BASIN_JEI_TYPE = new RecipeType<>(
            ResourceLocation.fromNamespaceAndPath("create", "basin"),
            BasinRecipe.class
    );

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        ItemStack burnerStack = new ItemStack(ModItems.FUEL_BURNER_ITEM.get());

        // 使用单数形式的 addRecipeCatalyst 注册
        registration.addRecipeCatalyst(burnerStack, BASIN_JEI_TYPE);
    }
}