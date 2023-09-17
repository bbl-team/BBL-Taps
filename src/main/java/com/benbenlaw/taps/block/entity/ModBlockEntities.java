package com.benbenlaw.taps.block.entity;

import com.benbenlaw.taps.Taps;
import com.benbenlaw.taps.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Taps.MOD_ID);

    public static final RegistryObject<BlockEntityType<SapTableBlockEntity>> SAP_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sap_table_block_entity", () ->
                    BlockEntityType.Builder.of(SapTableBlockEntity::new,
                            ModBlocks.SAP_TABLE.get()).build(null));




    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
