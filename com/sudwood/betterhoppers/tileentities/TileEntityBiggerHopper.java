package com.sudwood.betterhoppers.tileentities;

import java.util.List;

import com.sudwood.betterhoppers.TransferHelper;
import com.sudwood.betterhoppers.blocks.BlockBetterHopper;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityBiggerHopper extends TileEntityBetterHopper implements IInventory
{
    protected ItemStack[] inventory = new ItemStack[27];
    private String inventoryName;
    private int transferCooldown = 0;
    public final int CooldownTime = 8;
    public int numTransfered = 1;
    
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.inventory = new ItemStack[this.getSizeInventory()];

        this.transferCooldown = par1NBTTagCompound.getInteger("TransferCooldown");

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.inventory.length)
            {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);
        par1NBTTagCompound.setInteger("TransferCooldown", this.transferCooldown);


    }
    
    @Override
    public void updateEntity()
    {
    	 this.transferCooldown--;
     	if(this.transferCooldown <= 0 && !worldObj.isRemote && BlockBetterHopper.getIsBlockNotPoweredFromMetadata(this.blockMetadata))
     	{
 	    	for(int i = 0; i <this.getSizeInventory(); i++)
 	    	{
 	    		if(inventory[i]!=null)
 	    		{
 	    			pushItem();
 	    			break;
 	    		}
 	    	}
 	    		
 	    	pullItem();
 	    	
 	    	this.transferCooldown = this.CooldownTime;
     	}
     	
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
    
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.inventory[i] != null)
        {
            ItemStack itemstack;

            if (this.inventory[i].stackSize <= j)
            {
                itemstack = this.inventory[i];
                this.inventory[i] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[i].splitStack(j);

                if (this.inventory[i].stackSize == 0)
                {
                    this.inventory[i] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.inventory[i] != null)
        {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.inventory[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
        	itemstack.stackSize = this.getInventoryStackLimit();
        }
	}

	@Override
	public String getInvName() {
		return "Bigger Hopper";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return true;
	}

}
