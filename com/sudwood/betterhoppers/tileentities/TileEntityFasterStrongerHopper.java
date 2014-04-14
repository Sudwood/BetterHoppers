package com.sudwood.betterhoppers.tileentities;

import com.sudwood.betterhoppers.TransferHelper;
import com.sudwood.betterhoppers.blocks.BlockBetterHopper;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityFasterStrongerHopper extends TileEntityFasterHopper implements IInventory
{
	public int numTransfered = 64;
	@Override
	public String getInvName() {
		return "Faster Stronger Hopper";
	}
	
	public boolean putItemInThisInventory(ItemStack stack)
    {
    	int[] slot = null;
    	if(stack!=null)
    	{
    		slot = TransferHelper.checkSpace(xCoord, yCoord, zCoord, stack, worldObj, 0);
    		if(slot!=null)
    		{
	    		if(slot[1] == 0)
				{
					inventory[slot[0]] = stack.copy();
					this.onInventoryChanged();
					return true;
				}
				if(slot[1] == 1)
				{
					inventory[slot[0]].stackSize += stack.stackSize;
					this.onInventoryChanged();
					return true;
				}
    		}
    	}
    	return false;
    	
    }
     
    public void pullItem()
    {
    	int[] slot = TransferHelper.getFirstStackThatFits(xCoord, yCoord+ForgeDirection.UP.offsetY, zCoord, inventory, worldObj, numTransfered);
    	if(slot!= null)
    	{
    		try
    		{
    			IInventory tile = (IInventory) worldObj.getBlockTileEntity(xCoord, yCoord+ForgeDirection.UP.offsetY, zCoord);
    			if(slot[1] == 0)
    			{
    				inventory[slot[2]] = tile.getStackInSlot(slot[0]).copy();
    				inventory[slot[2]].stackSize = numTransfered;
    				this.onInventoryChanged();
    				if(tile.getStackInSlot(slot[0]).stackSize == numTransfered)
    				{
    					tile.setInventorySlotContents(slot[0], null);
    				}
    				else
    				{
    					ItemStack stack = tile.getStackInSlot(slot[0]).copy();
    					stack.stackSize-=numTransfered;
    					tile.setInventorySlotContents(slot[0], stack);
    				}
    				tile.onInventoryChanged();
    			}
    			if(slot[1] == 1)
    			{
    				ItemStack stackish = tile.getStackInSlot(slot[0]).copy();
    				stackish.stackSize = numTransfered;
    				if(slot[3]!= 0)
    				{
    					stackish.stackSize = slot[3];
    				}
    				System.out.println(stackish.stackSize);
    				if(this.inventory[slot[2]].stackSize<64)
    				this.inventory[slot[2]].stackSize += stackish.stackSize;
    				this.onInventoryChanged();
    				if(tile.getStackInSlot(slot[0]).stackSize == stackish.stackSize)
    				{
    					tile.setInventorySlotContents(slot[0], null);
    				}
    				else
    				{
    					ItemStack stack = tile.getStackInSlot(slot[0]).copy();
    					stack.stackSize-=stackish.stackSize;
    					tile.setInventorySlotContents(slot[0], stack);
    				}
    				tile.onInventoryChanged();
    			}
    		}
    		
    		catch(Exception e)
    		{
    			
    		}
    	}
    }
    
    
    public void pushItem()
    {
    	int sideAttached = BlockBetterHopper.getDirectionFromMetadata(this.blockMetadata) ^ 1;
    	int[] slot = null;
    	for(int i = 0; i < inventory.length; i++)
    	{
	    	if(inventory[i]!=null)
	    	{
	    		
	    		IInventory transfer = null;
	    		switch(BlockBetterHopper.getDirectionFromMetadata(this.blockMetadata))
	    		{
	    		case 0: //down
	    			slot = TransferHelper.checkSpace(xCoord+ForgeDirection.DOWN.offsetX, yCoord+ForgeDirection.DOWN.offsetY, zCoord+ForgeDirection.DOWN.offsetZ, new ItemStack(inventory[i].getItem(), numTransfered, inventory[i].getItemDamage()), worldObj, sideAttached);
	    			try
	    			{
	    				transfer = (IInventory) worldObj.getBlockTileEntity(xCoord+ForgeDirection.DOWN.offsetX, yCoord+ForgeDirection.DOWN.offsetY, zCoord+ForgeDirection.DOWN.offsetZ);
	    			}
	    			catch(Exception e)
	    			{
	    				
	    			}
	    			break;
	    		case 2: //North
	    			slot = TransferHelper.checkSpace(xCoord+ForgeDirection.NORTH.offsetX, yCoord+ForgeDirection.NORTH.offsetY, zCoord+ForgeDirection.NORTH.offsetZ, new ItemStack(inventory[i].getItem(), numTransfered, inventory[i].getItemDamage()), worldObj, sideAttached);
	    			
	    			try
	    			{
	    				transfer = (IInventory) worldObj.getBlockTileEntity(xCoord+ForgeDirection.NORTH.offsetX, yCoord+ForgeDirection.NORTH.offsetY, zCoord+ForgeDirection.NORTH.offsetZ);
	    			}
	    			catch(Exception e)
	    			{
	    				
	    			}break;
	    		case 3: //south
	    			slot = TransferHelper.checkSpace(xCoord+ForgeDirection.SOUTH.offsetX, yCoord+ForgeDirection.SOUTH.offsetY, zCoord+ForgeDirection.SOUTH.offsetZ, new ItemStack(inventory[i].getItem(), numTransfered, inventory[i].getItemDamage()), worldObj, sideAttached);
	    			try
	    			{
	    				transfer = (IInventory) worldObj.getBlockTileEntity(xCoord+ForgeDirection.SOUTH.offsetX, yCoord+ForgeDirection.SOUTH.offsetY, zCoord+ForgeDirection.SOUTH.offsetZ);
	    			}
	    			catch(Exception e)
	    			{
	    				
	    			}
	    			break;
	    		case 4: //west
	    			slot = TransferHelper.checkSpace(xCoord+ForgeDirection.WEST.offsetX, yCoord+ForgeDirection.WEST.offsetY, zCoord+ForgeDirection.WEST.offsetZ, new ItemStack(inventory[i].getItem(), numTransfered, inventory[i].getItemDamage()), worldObj, sideAttached);
	    			try
	    			{
	    				transfer = (IInventory) worldObj.getBlockTileEntity(xCoord+ForgeDirection.WEST.offsetX, yCoord+ForgeDirection.WEST.offsetY, zCoord+ForgeDirection.WEST.offsetZ);
	    			}
	    			catch(Exception e)
	    			{
	    				
	    			}
	    			break;
	    		case 5: //east
	    			slot = TransferHelper.checkSpace(xCoord+ForgeDirection.EAST.offsetX, yCoord+ForgeDirection.EAST.offsetY, zCoord+ForgeDirection.EAST.offsetZ, new ItemStack(inventory[i].getItem(), numTransfered, inventory[i].getItemDamage()), worldObj, sideAttached);
	    			try
	    			{
	    				transfer = (IInventory) worldObj.getBlockTileEntity(xCoord+ForgeDirection.EAST.offsetX, yCoord+ForgeDirection.EAST.offsetY, zCoord+ForgeDirection.EAST.offsetZ);
	    			}
	    			catch(Exception e)
	    			{
	    				
	    			}
	    			break;
	    		}
	    		if(slot!=null && transfer!= null)
	    		{	
	    			if(slot[1] == 0)
	    			{
	    				ItemStack stack = inventory[i].copy();
	    				stack.stackSize = numTransfered;
	    				transfer.setInventorySlotContents(slot[0], stack);
	    				transfer.onInventoryChanged();
	    				if(inventory[i].stackSize == numTransfered)
	    				{
	    					inventory[i] = null;
	    					this.onInventoryChanged();
	    					return;
	    				}
	    				inventory[i].stackSize-=numTransfered;
	    				this.onInventoryChanged();
	    				return;
	    			}
	    			if(slot[1] == 1 && transfer.getStackInSlot(slot[0]).stackSize < transfer.getInventoryStackLimit())
	    			{
	    				ItemStack stack = inventory[i].copy();
	    				if(slot[2]!=0)
	    					stack.stackSize = slot[2]+transfer.getStackInSlot(slot[0]).stackSize;
	    				else
	    					stack.stackSize = numTransfered+transfer.getStackInSlot(slot[0]).stackSize;
		    				transfer.setInventorySlotContents(slot[0], stack);
		    				transfer.onInventoryChanged();
		    				if(inventory[i].stackSize == numTransfered)
		    				{
		    					inventory[i] = null;
		    					this.onInventoryChanged();
		    					return;
		    				}
		    				inventory[i].stackSize-=numTransfered;
		    				this.onInventoryChanged();
		    				return;
	    				
	    			}
	    			
	    		}
	    		
	    	}
    	}
    }

}
