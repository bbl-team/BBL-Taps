package com.benbenlaw.taps.recipe;

import com.benbenlaw.opolisutilities.recipe.*;
import com.benbenlaw.taps.Taps;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER=
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Taps.MOD_ID);


    public static final RegistryObject<RecipeSerializer<SapTableRecipe>> SAP_TABLE_SERIALIZER =
            SERIALIZER.register("sap_table", () -> SapTableRecipe.Serializer.INSTANCE);



    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
    }


}
