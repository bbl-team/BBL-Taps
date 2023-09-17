package com.benbenlaw.taps;

import com.benbenlaw.taps.recipe.ModRecipes;
import com.benbenlaw.taps.screen.ModMenuTypes;
import com.benbenlaw.taps.block.ModBlocks;
import com.benbenlaw.taps.block.entity.ModBlockEntities;
import com.benbenlaw.taps.item.ModCreativeTab;
import com.benbenlaw.taps.item.ModItems;
import com.benbenlaw.taps.screen.SapTableMenu;
import com.benbenlaw.taps.screen.SapTableScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.benbenlaw.taps.Taps.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class Taps {

    public static final String MOD_ID = "taps";
    private static final Logger LOGGER = LogManager.getLogger();

    public Taps() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModCreativeTab.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);

        eventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            event.enqueueWork(() -> {

                MenuScreens.register(ModMenuTypes.SAP_TABLE_MENU.get(), SapTableScreen::new);


            });
        }
    }
}
