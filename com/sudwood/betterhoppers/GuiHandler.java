package com.sudwood.betterhoppers;

import com.sudwood.betterhoppers.client.gui.GuiBetterHopper;
import com.sudwood.betterhoppers.container.ContainerBetterHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityBetterHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityBiggerHopper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch(ID)
		{
		case 0:
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityBiggerHopper)
			{
				
				return new ContainerBetterHopper(player.inventory, (TileEntityBetterHopper) tileEntity, true);
			}
	        if(tileEntity instanceof TileEntityBetterHopper)
	        {
	        	return new ContainerBetterHopper(player.inventory, (TileEntityBetterHopper) tileEntity, false);
	        }
			break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch(ID)
		{
		case 0:
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityBiggerHopper)
			{
				return new GuiBetterHopper(player.inventory, (TileEntityBetterHopper) tileEntity, true);
			}
	        if(tileEntity instanceof TileEntityBetterHopper)
	        {
	        	return new GuiBetterHopper(player.inventory, (TileEntityBetterHopper) tileEntity, false);
	        }
			break;
		}
		return null;
	}

}
