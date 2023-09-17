package com.benbenlaw.taps.recipe;

import com.benbenlaw.taps.Taps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SapTableRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputItem;
    private final int duration;
    private final String connectedTapBlock;

    public SapTableRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItem,
                          int duration, String connectedTapBlock) {

        this.id = id;
        this.output = output;
        this.inputItem = inputItem;
        this.duration = duration;
        this.connectedTapBlock = connectedTapBlock;

    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, @NotNull Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        if(inputItem.get(0).test(pContainer.getItem(1))) {
            return duration >= 0;
        }
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) {
        return output;
    }


    public int getDuration() {
        return duration;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItem;
    }

    public String getConnectedTapBlock() {
        return connectedTapBlock;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Type implements RecipeType<SapTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "strainer";
    }

    public static class Serializer implements RecipeSerializer<SapTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Taps.MOD_ID, "sap_table");

        @Override
        public SapTableRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int duration = GsonHelper.getAsInt(json, "duration", 200);
            String connectedTapBlock = GsonHelper.getAsString(json, "connectedTapBlock", "minecraft:oak_log");

            return new SapTableRecipe(id, output, inputs, duration, connectedTapBlock);

        }

        @Override
        public SapTableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ItemStack output = buf.readItem();

            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            int duration = buf.readInt();
            String connectedTapBlock = buf.readUtf(Short.MAX_VALUE);

            return new SapTableRecipe(id, output, inputs, duration, connectedTapBlock);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SapTableRecipe recipe) {
            buf.writeItemStack(recipe.output, false);

            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeInt(recipe.getDuration());
            buf.writeUtf(recipe.getConnectedTapBlock(), Short.MAX_VALUE);
        }
    }
}
