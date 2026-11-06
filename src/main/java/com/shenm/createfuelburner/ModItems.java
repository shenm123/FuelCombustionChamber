package com.shenm.createfuelburner;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateFuelBurnerMod.MODID);

    // 🌟 修改：使用自定义的 FuelBurnerItem
    public static final DeferredItem<FuelBurnerItem> FUEL_BURNER_ITEM = ITEMS.register(
            "create_fuel_burner",
            () -> new FuelBurnerItem(ModBlocks.FUEL_BURNER.get(), new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}