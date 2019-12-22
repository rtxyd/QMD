package lach_01298.qmd.multiblock.accelerator.linear.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;

import lach_01298.qmd.multiblock.accelerator.linear.LinearAcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.linear.block.BlockLinearAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPart;
import nc.Global;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.RegistryHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileLinearAcceleratorController extends TileAcceleratorPart implements IAcceleratorController
{

	public TileLinearAcceleratorController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public Class<? extends AcceleratorLogic> getLogicClass()
	{
		return LinearAcceleratorLogic.class;
	}

	@Override
	public AcceleratorLogic createNewLogic(AcceleratorLogic oldLogic)
	{
		return new LinearAcceleratorLogic(oldLogic);
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().getFacing() != null)
		{
			getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(FACING_ALL, getPartPosition().getFacing()), 2);
		}
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos)
	{
		super.onBlockNeighborChanged(state, world, pos, fromPos);
		if (getMultiblock() != null) getMultiblock().updateActivity();
	}

	@Override
	public void updateBlockState(boolean isActive)
	{
		if (getBlockType() instanceof BlockLinearAcceleratorController)
		{
			((BlockLinearAcceleratorController) getBlockType()).setState(isActive, this);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}

	@Override
	public void doMeltdown()
	{

	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
}