package com.sudwood.betterhoppers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import com.sudwood.betterhoppers.blocks.BetterBlocks;

public class BetterHoppersTab extends CreativeTabs
{
	public BetterHoppersTab(String label)
	{
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(BetterBlocks.betterHopper);
	}
	public String getTranslatedTabLabel()
    {
        return this.getTabLabel();
    }
}
