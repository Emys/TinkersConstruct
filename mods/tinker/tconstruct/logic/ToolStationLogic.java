package mods.tinker.tconstruct.logic;

import mods.tinker.common.InventoryLogic;
import mods.tinker.tconstruct.container.ToolStationContainer;
import mods.tinker.tconstruct.crafting.ToolBuilder;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/* Simple class for storing items in the block
 */

public class ToolStationLogic extends InventoryLogic
{
	ItemStack previousTool;
	String toolName;

	public ToolStationLogic()
	{
		super(5);
		toolName = "";
	}

	@Override
	public ItemStack getStackInSlotOnClosing (int var1)
	{
		return null;
	}

	@Override
	public String getDefaultName ()
	{
		return "toolstation.crafting";
	}

	@Override
	public Container getGuiContainer (InventoryPlayer inventoryplayer, World world, int x, int y, int z)
	{
		return new ToolStationContainer(inventoryplayer, this);
	}

	public void onInventoryChanged ()
	{
		buildTool(toolName);
		if (this.worldObj != null)
		{
			this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.updateTileEntityChunkAndDoNothing(this.xCoord, this.yCoord, this.zCoord, this);
		}
	}

	public void buildTool (String name)
	{
		toolName = name;
		ItemStack tool = ToolBuilder.instance.buildTool(inventory[1], inventory[2], inventory[3], name);
		if (inventory[0] == null)
			inventory[0] = tool;
		else
		{
			NBTTagCompound tags = inventory[0].getTagCompound();
			if (!tags.getCompoundTag("InfiTool").hasKey("Built"))
			{
				inventory[0] = tool;
			}
		}
		/*if (container != null)
			container.detectAndSendChanges();*/
	}
	
	public void setToolname (String name)
	{
		toolName = name;
		buildTool (name);
	}

	void consumeItems ()
	{
		for (int i = 1; i <= 3; i++)
			super.decrStackSize(i, 1);

		/*if (container != null)
			container.detectAndSendChanges();*/
	}
	
	public boolean canUpdate()
    {
        return false;
    }
}