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
                output.accept(ModItems.OAK_SAP.get());


            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }


}
