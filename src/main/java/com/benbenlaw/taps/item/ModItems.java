package com.benbenlaw.taps.item;


import com.benbenlaw.taps.Taps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Taps.MOD_ID);

    public static final RegistryObject<Item> TREE_SAP = ITEMS.register("tree_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLACK_SAP = ITEMS.register("black_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WHITE_SAP = ITEMS.register("white_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ORANGE_SAP = ITEMS.register("orange_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MAGENTA_SAP = ITEMS.register("magenta_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_BLUE_SAP = ITEMS.register("light_blue_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YELLOW_SAP = ITEMS.register("yellow_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIME_SAP = ITEMS.register("lime_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PINK_SAP = ITEMS.register("pink_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GRAY_SAP = ITEMS.register("gray_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_GRAY_SAP = ITEMS.register("light_gray_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CYAN_SAP = ITEMS.register("cyan_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PURPLE_SAP = ITEMS.register("purple_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLUE_SAP = ITEMS.register("blue_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BROWN_SAP = ITEMS.register("brown_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GREEN_SAP = ITEMS.register("green_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RED_SAP = ITEMS.register("red_sap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_SAP_CAST = ITEMS.register("wooden_sap_cast",
            () -> new Item(new Item.Properties()));






    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
