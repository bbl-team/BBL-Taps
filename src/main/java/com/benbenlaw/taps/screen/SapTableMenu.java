package com.benbenlaw.taps.screen;

import com.benbenlaw.opolisutilities.screen.slot.ModResultSlot;
import com.benbenlaw.taps.block.ModBlocks;
import com.benbenlaw.taps.block.entity.SapTableBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SapTableMenu extends AbstractContainerMenu {
    private final SapTableBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public SapTableMenu(int containerID, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerID, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(26));
    }

    public SapTableMenu(int containerID, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.SAP_TABLE_MENU.get(), containerID);
        checkContainerSize(inventory, 26);
        blockEntity = ((SapTableBlockEntity) entity);
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {

            this.addSlot(new SlotItemHandler(handler, 0, 8, 17)); //Sap slot

            this.addSlot(new SlotItemHandler(handler, 1, 8, 53)); //upgrades?

            //Outputs
            this.addSlot(new ModResultSlot(handler, 2, 62, 9));
            this.addSlot(new ModResultSlot(handler, 3, 80, 9));
            this.addSlot(new ModResultSlot(handler, 4, 98, 9));
            this.addSlot(new ModResultSlot(handler, 5, 116, 9));
            this.addSlot(new ModResultSlot(handler, 6, 134, 9));
            this.addSlot(new ModResultSlot(handler, 7, 152, 9));

            this.addSlot(new ModResultSlot(handler, 8, 62, 27));
            this.addSlot(new ModResultSlot(handler, 9,80,  27));
            this.addSlot(new ModResultSlot(handler, 10,98,  27));
            this.addSlot(new ModResultSlot(handler, 11,116, 27));
            this.addSlot(new ModResultSlot(handler, 12,134, 27));
            this.addSlot(new ModResultSlot(handler, 13,152, 27));

            this.addSlot(new ModResultSlot(handler, 14, 62, 45));
            this.addSlot(new ModResultSlot(handler, 15, 80, 45));
            this.addSlot(new ModResultSlot(handler, 16, 98, 45));
            this.addSlot(new ModResultSlot(handler, 17, 116, 45));
            this.addSlot(new ModResultSlot(handler, 18, 134, 45));
            this.addSlot(new ModResultSlot(handler, 19, 152, 45));

            this.addSlot(new ModResultSlot(handler, 20, 62, 63));
            this.addSlot(new ModResultSlot(handler, 21, 80, 63));
            this.addSlot(new ModResultSlot(handler, 22, 98, 63));
            this.addSlot(new ModResultSlot(handler, 23, 116, 63));
            this.addSlot(new ModResultSlot(handler, 24, 134, 63));
            this.addSlot(new ModResultSlot(handler, 25, 152, 63));


        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0 ;
    }

    public int getScaledProgress() {

        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 20; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 26;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.SAP_TABLE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
