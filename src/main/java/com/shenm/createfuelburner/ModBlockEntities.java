package com.shenm.createfuelburner;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateFuelBurnerMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FuelBurnerBlockEntity>> FUEL_BURNER_BE =
            BLOCK_ENTITIES.register("fuel_burner_be",
                    () -> BlockEntityType.Builder.of(FuelBurnerBlockEntity::new, ModBlocks.FUEL_BURNER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}