package com.sudwood.betterhoppers.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBetterHopper extends Container
{
    private final IInventory inventory;
    private boolean isBigger;
    public ContainerBetterHopper(InventoryPlayer par1InventoryPlayer, IInventory par2IInventory, boolean isBigger)
    {
    	this.isBigger = isBigger;
    	int i2;
    	this.inventory = par2IInventory;
    	int b0 = 51;
    	
    	
    	if(isBigger)
    	{
    		
    		int x = 8;
    		int y = 18;
    		int i = 0;
    		int j = 0;
    		b0 = 171;
    		 for (j = 0; j < 3; ++j)
    	        {
    	            for (i = 0; i < 9; ++i)
    	            {
    	                this.addSlotToContainer(new Slot(par2IInventory, i+j*9, 8 + i * 18, 18 + j * 18));
    	            }
    	        }
    		 for (i2 = 0; i2 < 3; ++i2)
	 	        {
	 	            for (j = 0; j < 9; ++j)
	 	            {
	 	                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i2 * 9 + 9, 8 + j * 18, i2 * 18 + 86));
	 	            }
	 	        }
	
	 	        for (i2 = 0; i2 < 9; ++i2)
	 	        {
	 	            this.addSlotToContainer(new Slot(par1InventoryPlayer, i2, 8 + i2 * 18, 144));
	 	        }
    	}
    	else
    	{
	        for (i2 = 0; i2 < par2IInventory.getSizeInventory(); ++i2)
	        {
	            this.addSlotToContainer(new Slot(par2IInventory, i2, 44 + i2 * 18, 20));
	        } 
	        for (i2 = 0; i2 < 3; ++i2)
	        {
	            for (int j = 0; j < 9; ++j)
	            {
	                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i2 * 9 + 9, 8 + j * 18, i2 * 18 + b0));
	            }
	        }

	        for (i2 = 0; i2 < 9; ++i2)
	        {
	            this.addSlotToContainer(new Slot(par1InventoryPlayer, i2, 8 + i2 * 18, 58 + b0));
	        }
    	}
    	
    	
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.inventory.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < this.inventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        this.inventory.closeChest();
    }
}
