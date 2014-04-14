package com.sudwood.betterhoppers.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sudwood.betterhoppers.container.ContainerBetterHopper;

@SideOnly(Side.CLIENT)
public class GuiBetterHopper extends GuiContainer
{
    private static final ResourceLocation hopperGuiTextures = new ResourceLocation("textures/gui/container/hopper.png");
    private static final ResourceLocation biggerHopperGui = new ResourceLocation("betterhoppers", "/textures/bigger_hopper_gui.png");
    private IInventory playerInventory;
    private IInventory blockInventory;
    private boolean isBigger;
    private int inventoryRows;
    public GuiBetterHopper(InventoryPlayer par1InventoryPlayer, IInventory par2IInventory, boolean isBigger)
    {
        super(new ContainerBetterHopper(par1InventoryPlayer, par2IInventory, isBigger));
        this.playerInventory = par1InventoryPlayer;
        this.blockInventory = par2IInventory;
        this.allowUserInput = false;
        this.ySize = 133;
        this.isBigger = isBigger;
        if(isBigger)
        {
        	short short1 = 222;
            int i = short1 - 108;
            this.inventoryRows = par2IInventory.getSizeInventory() / 9;
            this.ySize = 167;
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(this.blockInventory.isInvNameLocalized() ? this.blockInventory.getInvName() : I18n.getString(this.blockInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.isInvNameLocalized() ? this.playerInventory.getInvName() : I18n.getString(this.playerInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	if(!isBigger)
    	{
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(hopperGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    	}
    	if(isBigger)
    	{
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(biggerHopperGui);
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    	}
    }
}
