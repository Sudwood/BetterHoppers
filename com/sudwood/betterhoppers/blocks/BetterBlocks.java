package com.sudwood.betterhoppers.blocks;

import com.sudwood.betterhoppers.BetterHoppers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BetterBlocks 
{
	public static Block betterHopper;
	public static Block fasterHopper;
	public static Block biggerHopper;
	public static Block strongerHopper;
	public static Block fasterStrongerHopper;
	public static Block fasterBiggerHopper;
	public static Block biggerStrongerHopper;
	public static Block fasterBiggerStrongerHopper;
	
	public static void init()
	{
		if(BetterHoppers.enableBetterHopper)
		{
			betterHopper = new BlockBetterHopper(BetterHoppers.betterhopperid, 0).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:betterhopper");
			GameRegistry.registerBlock(betterHopper, "BetterHopper");
			LanguageRegistry.addName(betterHopper, "Better Hopper");
			GameRegistry.addRecipe(new ItemStack(betterHopper, 1), new Object[]{
				"I I", "LHL", " I ", 'I', Item.ingotIron, 'L', new ItemStack(Item.dyePowder, 1, 4), 'H', Block.chest
			});
			
			if(BetterHoppers.enableFasterHopper)
			{
				fasterHopper = new BlockBetterHopper(BetterHoppers.fasterhopperid, 1).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:fasterhopper");
				GameRegistry.registerBlock(fasterHopper, "FasterHopper");
				LanguageRegistry.addName(fasterHopper, "Faster Hopper");
				GameRegistry.addRecipe(new ItemStack(fasterHopper, 1), new Object[]{
					"R R", "RHR", " R ", 'R', Item.redstone, 'H', betterHopper
				});
			}
			
			if(BetterHoppers.enableBiggerHopper)
			{
				biggerHopper = new BlockBetterHopper(BetterHoppers.biggerhopperid, 3).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:biggerhopper");
				GameRegistry.registerBlock(biggerHopper, "BiggerHopper");
				LanguageRegistry.addName(biggerHopper, "Bigger Hopper");
				GameRegistry.addRecipe(new ItemStack(biggerHopper, 1), new Object[]{
					"D D", "DHD", " D ", 'D', Item.diamond, 'H', betterHopper
				});
			}
			
			if(BetterHoppers.enableStrongerHopper)
			{
				strongerHopper = new BlockBetterHopper(BetterHoppers.strongerhopperid, 2).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:strongerhopper");
				GameRegistry.registerBlock(strongerHopper, "StrongerHopper");
				LanguageRegistry.addName(strongerHopper, "Stronger Hopper");
				GameRegistry.addRecipe(new ItemStack(strongerHopper, 1), new Object[]{
					"L L", "LHL", " L ", 'L', new ItemStack(Item.dyePowder, 1, 4) , 'H', betterHopper
				});
			}
			if(BetterHoppers.enableFasterHopper && BetterHoppers.enableStrongerHopper)
			{
				fasterStrongerHopper = new BlockBetterHopper(BetterHoppers.fasterstrongerhopperid, 4).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:fasterstrongerhopper");
				GameRegistry.registerBlock(fasterStrongerHopper, "FasterStrongerHopper");
				LanguageRegistry.addName(fasterStrongerHopper, "Faster Stronger Hopper");
				GameRegistry.addRecipe(new ItemStack(fasterStrongerHopper, 1), new Object[]{
					"L L", "LHL", " L ", 'L', new ItemStack(Item.dyePowder, 1, 4) , 'H', fasterHopper
				});
				GameRegistry.addRecipe(new ItemStack(fasterStrongerHopper, 1), new Object[]{
					"R R", "RHR", " R ", 'R', Item.redstone, 'H', strongerHopper
				});
			}
			if(BetterHoppers.enableBiggerHopper && BetterHoppers.enableFasterHopper)
			{
				fasterBiggerHopper = new BlockBetterHopper(BetterHoppers.fasterbiggerhopperid, 5).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:fasterbiggerhopper");
				GameRegistry.registerBlock(fasterBiggerHopper, "FasterBiggerHopper");
				LanguageRegistry.addName(fasterBiggerHopper, "Faster Bigger Hopper");
				GameRegistry.addRecipe(new ItemStack(fasterBiggerHopper, 1), new Object[]{
					"D D", "DHD", " D ", 'D', Item.diamond , 'H', fasterHopper
				});
				GameRegistry.addRecipe(new ItemStack(fasterBiggerHopper, 1), new Object[]{
					"R R", "RHR", " R ", 'R', Item.redstone, 'H', biggerHopper
				});
			}
			if(BetterHoppers.enableBiggerHopper && BetterHoppers.enableStrongerHopper)
			{
				biggerStrongerHopper = new BlockBetterHopper(BetterHoppers.biggerstrongerhopperid, 6).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:biggerstrongerhopper");
				GameRegistry.registerBlock(biggerStrongerHopper, "BiggerStrongerHopper");
				LanguageRegistry.addName(biggerStrongerHopper, "Bigger Stronger Hopper");
				GameRegistry.addRecipe(new ItemStack(biggerStrongerHopper, 1), new Object[]{
					"L L", "LHL", " L ", 'L', new ItemStack(Item.dyePowder, 1, 4) , 'H', biggerHopper
				});
				GameRegistry.addRecipe(new ItemStack(biggerStrongerHopper, 1), new Object[]{
					"D D", "DHD", " D ", 'D', Item.diamond, 'H', strongerHopper
				});
			}
			if(BetterHoppers.enableFasterHopper && BetterHoppers.enableBiggerHopper && BetterHoppers.enableStrongerHopper)
			{
				fasterBiggerStrongerHopper = new BlockBetterHopper(BetterHoppers.fasterbiggerstrongerhopperid, 7).setCreativeTab(BetterHoppers.betterTab).setResistance(100F).setHardness(1.5F).setUnlocalizedName("BetterHoppers:fasterbiggerstrongerhopper");
				GameRegistry.registerBlock(fasterBiggerStrongerHopper, "FasterBiggerStrongerHopper");
				LanguageRegistry.addName(fasterBiggerStrongerHopper, "Faster Bigger Stronger Hopper");
				GameRegistry.addRecipe(new ItemStack(fasterBiggerStrongerHopper, 1), new Object[]{
					"L L", "LHL", " L ", 'L', new ItemStack(Item.dyePowder, 1, 4) , 'H', fasterBiggerHopper
				});
				GameRegistry.addRecipe(new ItemStack(fasterBiggerStrongerHopper, 1), new Object[]{
					"D D", "DHD", " D ", 'D', Item.diamond, 'H', fasterStrongerHopper
				});
				GameRegistry.addRecipe(new ItemStack(fasterBiggerHopper, 1), new Object[]{
					"R R", "RHR", " R ", 'R', Item.redstone, 'H', biggerStrongerHopper
				});
			}
			
			
		}
	}
}
