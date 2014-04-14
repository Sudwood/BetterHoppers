package com.sudwood.betterhoppers.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.sudwood.betterhoppers.BetterHoppers;
import com.sudwood.betterhoppers.tileentities.TileEntityBetterHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityBiggerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityBiggerStrongerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterBiggerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterBiggerStrongerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityFasterStrongerHopper;
import com.sudwood.betterhoppers.tileentities.TileEntityStrongerHopper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBetterHopper extends BlockHopper
{
    private final Random field_94457_a = new Random();
    @SideOnly(Side.CLIENT)
    private static Icon hopperIcon;
    @SideOnly(Side.CLIENT)
    private static Icon hopperTopIcon;
    @SideOnly(Side.CLIENT)
    private static Icon hopperInsideIcon;
    
    private int Type;

    public BlockBetterHopper(int par1, int type)
    {
        super(par1);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1F, 1.0F);
        Type = type;
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1F, 1.0F);
    }
    
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) 
    {
    	
    	if(par5Entity instanceof EntityItem)
    	{
    		boolean temp = false;
    		TileEntityBetterHopper tile = (TileEntityBetterHopper) par1World.getBlockTileEntity(par2, par3, par4);
    		temp = tile.putItemInThisInventory(((EntityItem) par5Entity).getEntityItem());
    		if(temp)
    			par5Entity.setDead();
    	}
    }
    
    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1F, f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1F, 1.0F);
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = Facing.oppositeSide[par5];

        if (j1 == 1)
        {
            j1 = 0;
        }
        return j1;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        
        switch(Type)
    	{
    	case 0: // better hopper
    		return new TileEntityBetterHopper();
    	case 1: // faster
    		return new TileEntityFasterHopper();
    	case 2: // stronger
    		return new TileEntityStrongerHopper();
    	case 3: // bigger
    		return new TileEntityBiggerHopper();
    	case 4: // faster stronger
    		return new TileEntityFasterStrongerHopper();
    	case 5: // faster bigger
    		return new TileEntityFasterBiggerHopper();
    	case 6: // bigger stronger
    		return new TileEntityBiggerStrongerHopper();
    	case 7: // faster bigger stronger
    		return new TileEntityFasterBiggerStrongerHopper();
    	default:
    		return new TileEntityBetterHopper();
    	}
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.updateMetadata(par1World, par2, par3, par4);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityBetterHopper TileEntityBetterHopper = (com.sudwood.betterhoppers.tileentities.TileEntityBetterHopper) par1World.getBlockTileEntity(par2, par3, par4);

            if (TileEntityBetterHopper != null)
            {
               par5EntityPlayer.openGui(BetterHoppers.instance, 0, par1World, par2, par3, par4);
            }

            return true;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        this.updateMetadata(par1World, par2, par3, par4);
    }

    /**
     * Updates the Metadata to include if the Hopper gets powered by Redstone or not
     */
    private void updateMetadata(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        int i1 = getDirectionFromMetadata(l);
        boolean flag = !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
        boolean flag1 = getIsBlockNotPoweredFromMetadata(l);

        if (flag != flag1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 | (flag ? 0 : 8), 4);
        }
    }

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntityBetterHopper TileEntityBetterHopper = (TileEntityBetterHopper)par1World.getBlockTileEntity(par2, par3, par4);

        if (TileEntityBetterHopper != null)
        {
            for (int j1 = 0; j1 < TileEntityBetterHopper.getSizeInventory(); ++j1)
            {
                ItemStack itemstack = TileEntityBetterHopper.getStackInSlot(j1);

                if (itemstack != null)
                {
                    float f = this.field_94457_a.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int k1 = this.field_94457_a.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.field_94457_a.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.field_94457_a.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.field_94457_a.nextGaussian() * f3);
                        par1World.spawnEntityInWorld(entityitem);
                    }
                }
            }

            par1World.func_96440_m(par2, par3, par4, par5);
            
        }
        par1World.removeBlockTileEntity(par2, par3, par4);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 38;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public static int getDirectionFromMetadata(int par0)
    {
        return par0 & 7;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.hopperTopIcon : this.hopperIcon;
    }

    public static boolean getIsBlockNotPoweredFromMetadata(int par0)
    {
        return (par0 & 8) != 8;
    }

    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory(getHopperTile(par1World, par2, par3, par4));
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
    	switch(Type)
    	{
    	case 0: // better hopper
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:hopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	case 1: // faster
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:faster_hopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	case 2: // stronger
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:stronger_hopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	case 3: // bigger
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:bigger_hopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	case 4: // faster stronger
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:faster_strongerhopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	case 5: // faster bigger
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:faster_bigger_hopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	case 6: // bigger stronger
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:stronger_bigger_hopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	case 7: // faster bigger stronger
    		this.hopperIcon = par1IconRegister.registerIcon("betterhoppers:faster_bigger_stronger_hopper_outside");
            this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
            this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
    		break;
    	}
    }

    @SideOnly(Side.CLIENT)
    public static Icon getHopperIcon(String par0Str)
    {
        return par0Str.equals("hopper_outside") ? hopperIcon : (par0Str.equals("hopper_inside") ? hopperInsideIcon : null);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets the icon name of the ItemBlock corresponding to this block. Used by hoppers.
     */
    public String getItemIconName()
    {
    	switch(Type)
    	{
    	case 0: // better hopper
    		return "betterhoppers:hopper";
    	case 1: // faster
    		return "betterhoppers:faster_hopper";
    	case 2: // stronger
    		return "betterhoppers:stronger_hopper";
    	case 3: // bigger
    		return "betterhoppers:bigger_hopper";
    	case 4: // faster stronger
    		return "betterhoppers:faster_stronger_hopper";
    	case 5: // faster bigger
    		return "betterhoppers:faster_bigger_hopper";
    	case 6: // bigger stronger
    		return "betterhoppers:bigger_stronger_hopper";
    	case 7: // faster bigger stronger
    		return "betterhoppers:faster_bigger_stronger_hopper";
    	default:
    		return "betterhoppers:hopper";
    	}
        
    }

    public static TileEntityHopper getHopperTile(IBlockAccess par0IBlockAccess, int par1, int par2, int par3)
    {
        return (TileEntityHopper)par0IBlockAccess.getBlockTileEntity(par1, par2, par3);
    }
}
