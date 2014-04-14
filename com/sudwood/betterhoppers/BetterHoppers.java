package com.sudwood.betterhoppers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;

import com.sudwood.betterhoppers.blocks.BetterBlocks;
import com.sudwood.betterhoppers.tileentities.TileEntityBetterHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityBiggerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityBiggerStrongerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterBiggerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterBiggerStrongerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterStrongerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityStrongerHopper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="betterhoppers", name="BetterHoppers", version="0.0.1")
@NetworkMod(clientSideRequired=true)
public class BetterHoppers 
{
    // The instance of your mod that Forge uses.
    @Instance(value = "betterhoppers")
    public static BetterHoppers instance;
    
    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="com.sudwood.betterhoppers.client.ClientProxy", serverSide="com.sudwood.betterhoppers.CommonProxy")
    public static CommonProxy proxy;
    public static int betterhopperid;
    public static int fasterhopperid;
    public static int biggerhopperid;
    public static int strongerhopperid;
    public static int fasterstrongerhopperid;
    public static int fasterbiggerhopperid;
    public static int biggerstrongerhopperid;
    public static int fasterbiggerstrongerhopperid;
    
    public static boolean enableBetterHopper;
    public static boolean enableFasterHopper;
    public static boolean enableBiggerHopper;
    public static boolean enableStrongerHopper;
    
    public static CreativeTabs betterTab = new BetterHoppersTab("Better Hoppers");
    
    @EventHandler 
    public void preInit(FMLPreInitializationEvent event) {
    	Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		config.load();
			betterhopperid = config.getBlock("BetterHopper", 2800).getInt();
			fasterhopperid = config.getBlock("FasterHopper", 2801).getInt();
			biggerhopperid = config.getBlock("BiggerHopper", 2802).getInt();
			strongerhopperid = config.getBlock("StrongerHopper", 2803).getInt();
			fasterstrongerhopperid = config.getBlock("FasterStrongerHopper", 2804).getInt();
			fasterbiggerhopperid = config.getBlock("FasterBiggerHopper", 2805).getInt();
			biggerstrongerhopperid = config.getBlock("BiggerStrongerHopper", 2806).getInt();
			fasterbiggerstrongerhopperid = config.getBlock("FasterBiggerStrongerHopper", 2807).getInt();
			config.addCustomCategoryComment("Enable", "Enable/Disable");
			enableBetterHopper = config.get("Enable", "BetterHopper", true).getBoolean(true);
		    enableFasterHopper = config.get("Enable", "FasterHopper", true).getBoolean(true);
		    enableBiggerHopper = config.get("Enable", "BiggerHopper", true).getBoolean(true);
		    enableStrongerHopper = config.get("Enable", "StrongerHopper", true).getBoolean(true);
		config.save();
    }
    
    @EventHandler 
    public void load(FMLInitializationEvent event) {
            proxy.registerRenderers();
            BetterBlocks.init();
            GameRegistry.registerTileEntity(TileEntityBetterHopper.class, "BetterHopper");
            GameRegistry.registerTileEntity(TileEntityFasterHopper.class, "FasterHopper");
            GameRegistry.registerTileEntity(TileEntityBiggerHopper.class, "BiggerHopper");
            GameRegistry.registerTileEntity(TileEntityStrongerHopper.class, "StrongerHopper");
            GameRegistry.registerTileEntity(TileEntityFasterStrongerHopper.class, "FasterStrongerHopper");
            GameRegistry.registerTileEntity(TileEntityFasterBiggerHopper.class, "FasterBiggerHopper");
            GameRegistry.registerTileEntity(TileEntityBiggerStrongerHopper.class, "BiggerStrongerHopper");
            GameRegistry.registerTileEntity(TileEntityFasterBiggerStrongerHopper.class, "FasterBiggerStrongerStrongerHopper");
            NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
}
