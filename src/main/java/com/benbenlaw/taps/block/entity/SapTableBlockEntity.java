package com.benbenlaw.taps.block.entity;

import com.benbenlaw.opolisutilities.block.entity.IInventoryHandlingBlockEntity;
import com.benbenlaw.opolisutilities.block.entity.custom.WrappedHandler;
import com.benbenlaw.opolisutilities.networking.ModMessages;
import com.benbenlaw.opolisutilities.networking.packets.PacketSyncItemStackToClient;
import com.benbenlaw.opolisutilities.recipe.NoInventoryRecipe;
import com.benbenlaw.taps.block.ModBlocks;
import com.benbenlaw.taps.recipe.SapTableRecipe;
import com.benbenlaw.taps.screen.SapTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SapTableBlockEntity extends BlockEntity implements MenuProvider, IInventoryHandlingBlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(26) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new PacketSyncItemStackToClient(this, worldPosition));
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i >= 2 && i <= 26,
                            (i, s) -> false)),

                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> {
                                if (index == 1 && itemHandler.isItemValid(1, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                    //                return stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                if (index == 2 && itemHandler.isItemValid(2, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                  //                  return !stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                return false;
                            })),

                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> {
                                if (index == 1 && itemHandler.isItemValid(1, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                    //                return stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                if (index == 2 && itemHandler.isItemValid(2, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                      //              return !stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                return false;
                            })),

                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> {
                                if (index == 1 && itemHandler.isItemValid(1, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                      //              return stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                if (index == 2 && itemHandler.isItemValid(2, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                       //             return !stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                return false;
                            })),

                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> {
                                if (index == 1 && itemHandler.isItemValid(1, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                          //          return stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                if (index == 2 && itemHandler.isItemValid(2, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                           //         return !stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                return false;
                            })),

                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> {
                                if (index == 1 && itemHandler.isItemValid(1, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                        //            return stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                if (index == 2 && itemHandler.isItemValid(2, stack)) {
                                    // Add a condition to check for the specific item you want to allow
                         //           return !stack.getTags().anyMatch(ModTags.Items.MESHES::equals);
                                }
                                return false;
                            })));




    public final ContainerData data;
    private int progress = 0;
    private int maxProgress = 80;

    private long lastExecutionTime = 0;

    private final FluidTank FLUID_TANK = createFluidTank();

    private FluidTank createFluidTank() {
        return new FluidTank(10000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
        };
    }


    public void setHandler(ItemStackHandler handler) {
        copyHandlerContents(handler);
    }

    private void copyHandlerContents(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    public SapTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SAP_TABLE_BLOCK_ENTITY.get(), blockPos, blockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> SapTableBlockEntity.this.progress;
                    case 1 -> SapTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SapTableBlockEntity.this.progress = value;
                    case 1 -> SapTableBlockEntity.this.maxProgress = value;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Sap Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerID, @NotNull Inventory inventory, @NotNull Player player) {
        return new SapTableMenu(containerID, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return directionWrappedHandlerMap.get(side).cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        for (Direction dir : Direction.values()) {
            if (directionWrappedHandlerMap.containsKey(dir)) {
                directionWrappedHandlerMap.get(dir).invalidate();
            }
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("sap_table.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("sap_table.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void tick() {

        Level pLevel = this.level;
        BlockPos pPos = this.worldPosition;
        assert pLevel != null;
        BlockState pState = pLevel.getBlockState(pPos);
        SapTableBlockEntity pBlockEntity = this;

        if (hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }

        //Particle

        if (level.getBlockState(pPos).is(ModBlocks.SAP_TABLE.get()) && level.getBlockState(pPos.above()).is(ModBlocks.TAP.get())) {
            if (level.getBlockState(pPos.above()).getValue(BlockStateProperties.POWERED)) {

                long currentTime = level.getGameTime();

                if (currentTime - lastExecutionTime >= 30) {
                    Direction direction = pState.getValue(BlockStateProperties.HORIZONTAL_FACING);


                    if (direction == Direction.NORTH) {
                        level.addParticle(ParticleTypes.DRIPPING_HONEY, pPos.getX() + 0.5, pPos.getY() + 1.3, pPos.getZ() + 0.75, 0.5, 0.5, 0.5);


                    }
                    if (direction == Direction.SOUTH) {
                        level.addParticle(ParticleTypes.DRIPPING_HONEY, pPos.getX() + 0.5, pPos.getY() + 1.3, pPos.getZ() + 0.25, 0.5, 0.5, 0.5);


                    }
                    if (direction == Direction.EAST) {
                        level.addParticle(ParticleTypes.DRIPPING_HONEY, pPos.getX() + 0.25, pPos.getY() + 1.3, pPos.getZ() + 0.5, 0.5, 0.5, 0.5);


                    }
                    if (direction == Direction.WEST) {
                        level.addParticle(ParticleTypes.DRIPPING_HONEY, pPos.getX() + 0.75, pPos.getY() + 1.3, pPos.getZ() + 0.5, 0.5, 0.5, 0.5);

                    }

                    lastExecutionTime = currentTime;
                }
            }
        }
    }

    private boolean hasRecipe(@NotNull SapTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        assert level != null;
        BlockPos blockPos = entity.worldPosition.above(1);
        BlockState blockAbove = level.getBlockState(blockPos);

        if (!blockAbove.is(ModBlocks.TAP.get()))
            return false;

        Block logBlock = getLogBlock(entity);

        Optional<SapTableRecipe> match = level.getRecipeManager()
                .getAllRecipesFor(SapTableRecipe.Type.INSTANCE)
                .stream()
                .filter(e -> {
                    return e.matches(inventory, level) && logBlock != null && logBlock == ForgeRegistries.BLOCKS.getValue(new ResourceLocation(e.getConnectedTapBlock()));
                })
                .findAny();


        if (match.isPresent()){
            if (blockAbove.getValue(BlockStateProperties.POWERED)) {
                return match.filter(currentRecipe ->
                        hasInputItem(entity, currentRecipe)
                                && canInsertItemIntoOutputSlot(inventory, currentRecipe.getResultItem(Objects.requireNonNull(getLevel()).registryAccess()))
                                && hasOutputSpaceMakingSoaking(entity, currentRecipe)
                                && hasDuration(currentRecipe)).isPresent();
            }
        }


        return false;
    }

    private Block getLogBlock(SapTableBlockEntity entity) {
        Block logBlock = null;
        BlockPos blockPos = entity.worldPosition.above(1);
        BlockState blockAbove = level.getBlockState(blockPos);
        Direction direction = blockAbove.getValue(BlockStateProperties.HORIZONTAL_FACING);

        if (direction == Direction.NORTH) {
            BlockPos logPos = entity.worldPosition.above().north();
            logBlock = level.getBlockState(logPos).getBlock();

        }
        if (direction == Direction.SOUTH) {
            BlockPos logPos = entity.worldPosition.above().south();
            logBlock = level.getBlockState(logPos).getBlock();

        }
        if (direction == Direction.EAST) {
            BlockPos logPos = entity.worldPosition.above().east();
            logBlock = level.getBlockState(logPos).getBlock();

        }
        if (direction == Direction.WEST) {
            BlockPos logPos = entity.worldPosition.above().west();
            logBlock = level.getBlockState(logPos).getBlock();

        }

        return logBlock;
    }


    private boolean hasDuration(SapTableRecipe recipe) {
        return 0 <= recipe.getDuration();
    }

    private boolean hasInputItem(@NotNull SapTableBlockEntity entity, @NotNull SapTableRecipe recipe) {

        ItemStack[] items = recipe.getIngredients().get(0).getItems();
        ItemStack slotItem = entity.itemHandler.getStackInSlot(1);
        for (ItemStack item : items) {
            if (ItemStack.isSameItem(item, slotItem)) {
                return true;
            }
        }
        return false;
    }

    private void craftItem(@NotNull SapTableBlockEntity entity) {
        Level level = entity.level;

        assert level != null;
        if(!level.isClientSide()) {

            SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
            for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
                inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
            }

            Block logBlock = getLogBlock(entity);
            Optional<SapTableRecipe> match = level.getRecipeManager()
                    .getAllRecipesFor(SapTableRecipe.Type.INSTANCE)
                    .stream()
                    .filter(e -> {
                        return e.matches(inventory, level) && logBlock != null && logBlock == ForgeRegistries.BLOCKS.getValue(new ResourceLocation(e.getConnectedTapBlock()));
                    })
                    .findAny();

            if (match.isPresent()) {

                entity.itemHandler.extractItem(1, 1, false);
                assert Minecraft.getInstance().level != null;

                ItemStack remaining = new ItemStack(match.get().getResultItem(Objects.requireNonNull(getLevel()).registryAccess()).getItem());
                for (int i = 2; i <= 26; i++) {
                    remaining = entity.itemHandler.insertItem(
                            i,
                            remaining,
                            false
                    );
                    if (remaining.isEmpty())
                        break;
                }

                entity.resetProgress();

            }

        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        boolean empty = false;

        for (int i =3 ; i < inventory.getContainerSize(); i++)
            if (inventory.getItem(i).isEmpty()) {
                empty = true;
                break;
            }

        return inventory.getItem(3).getItem() == output.getItem() || empty;
    }

    private boolean hasOutputSpaceMakingSoaking(SapTableBlockEntity entity, SapTableRecipe recipe) {
        assert Minecraft.getInstance().level != null;

        boolean result = false;
        for (int i = 3; i < entity.itemHandler.getSlots(); i++) {
            result = entity.itemHandler.getStackInSlot(i).getCount() + recipe.getResultItem(Objects.requireNonNull(getLevel()).registryAccess()).getCount() - 1 <
                    entity.itemHandler.getStackInSlot(i).getMaxStackSize();
            if (result)
                break;
        }


        return result;
    }

}