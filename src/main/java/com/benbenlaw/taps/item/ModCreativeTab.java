package com.benbenlaw.taps.item;

import com.benbenlaw.taps.Taps;
import com.benbenlaw.taps.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Taps.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAPS_TAB = CREATIVE_MODE_TABS.register("taps", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModBlocks.TAP.get().asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.tap"))
            .displayItems((parameters, output) -> {

                output.accept(ModBlocks.TAP.get());
                output.accept(ModBlocks.SAP_TABLE.get());
                output.accept(ModItems.TREE_SAP.get());

                output.accept(ModItems.BLACK_SAP.get());
                output.accept(ModItems.WHITE_SAP.get());
                output.accept(ModItems.YELLOW_SAP.get());
                output.accept(ModItems.GREEN_SAP.get());
                output.accept(ModItems.LIME_SAP.get());
                output.accept(ModItems.ORANGE_SAP.get());
                output.accept(ModItems.RED_SAP.get());
                output.accept(ModItems.PINK_SAP.get());
                output.accept(ModItems.MAGENTA_SAP.get());
                output.accept(ModItems.PURPLE_SAP.get());
                output.accept(ModItems.BROWN_SAP.get());
                output.accept(ModItems.CYAN_SAP.get());
                output.accept(ModItems.LIGHT_BLUE_SAP.get());
                output.accept(ModItems.BLUE_SAP.get());
                output.accept(ModItems.LIGHT_GRAY_SAP.get());
                output.accept(ModItems.GRAY_SAP.get());


            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }


}
