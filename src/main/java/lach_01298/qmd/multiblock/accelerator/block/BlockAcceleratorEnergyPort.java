package lach_01298.qmd.multiblock.accelerator.block;



import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCasing;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorOutlet;

import nc.util.Lang;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockAcceleratorEnergyPort extends BlockAcceleratorPart
{

	public BlockAcceleratorEnergyPort()
	{
		super();
		
	}







	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileAcceleratorEnergyPort();
	}



	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND || player.isSneaking())
			return false;
		return rightClickOnPart(world, pos, player, hand, facing);
	}	
	
}