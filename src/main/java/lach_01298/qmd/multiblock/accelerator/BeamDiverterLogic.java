package lach_01298.qmd.multiblock.accelerator;

import java.util.HashSet;
import java.util.Set;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.DipoleMagnet;
import lach_01298.qmd.multiblock.accelerator.QuadrupoleMagnet;
import lach_01298.qmd.multiblock.accelerator.RFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorInlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorOutlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.multiblock.container.ContainerBeamDiverterController;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.BeamDiverterUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.Global;
import nc.multiblock.Multiblock;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.container.ContainerSaltFissionController;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.salt.tile.TileSaltFissionHeater;
import nc.multiblock.fission.salt.tile.TileSaltFissionVessel;
import nc.multiblock.fission.tile.IFissionController;
import nc.multiblock.network.FissionUpdatePacket;
import nc.multiblock.network.SolidFissionUpdatePacket;
import nc.util.NCMath;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class BeamDiverterLogic extends AcceleratorLogic
{

	public BeamDiverterLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		getAccelerator().beams.add(new ParticleStorageAccelerator());
		getAccelerator().beams.add(new ParticleStorageAccelerator());
	}

	@Override
	public String getID()
	{
		return "beam_diverter";
	}

	// Multiblock Validation
	
	
	
	public boolean isMachineWhole(Multiblock multiblock) 
	{
		Accelerator acc = getAccelerator();
		
		if (acc.getExteriorLengthX() != thickness || acc.getExteriorLengthY() != thickness || acc.getExteriorLengthZ() != thickness)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.beam_director.must_be_cube", null);
			return false;
		}
		
		
		if (!(acc.WORLD.getTileEntity(acc.getMiddleCoord()) instanceof TileAcceleratorBeam))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.beam_director.must_be_beam", acc.getMiddleCoord());
			return false;
		}

		if (!acc.isValidDipole(acc.getMiddleCoord(), false) && !acc.isValidDipole(acc.getMiddleCoord(), true))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.beam_director.must_be_dipole", acc.getMiddleCoord());
			return false;
		}
		
		// inlet
		if (getPartMap(TileAcceleratorInlet.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.no_inlet", null);
			return false;
		}

		// outlet
		if (getPartMap(TileAcceleratorOutlet.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.no_outlet", null);
			return false;
		}

		// Energy Ports
		if (getPartMap(TileAcceleratorEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.need_energy_ports", null);
			return false;
		}
		
	
		//beam ports
		
		for(TileAcceleratorBeamPort port :getPartMap(TileAcceleratorBeamPort.class).values())
		{
			boolean valid = false;
			if(port.getPos().toLong() == acc.getMiddleCoord().up(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().down(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().north(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().south(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().east(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().west(2).toLong())
			{
				valid = true;
			}
			
			
			if(!valid)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.invalid_beam_port", port.getPos());
				return false;
			}
		}
				
			
		int inputs =0;
		int outputs =0;	
		for(TileAcceleratorBeamPort port :getPartMap(TileAcceleratorBeamPort.class).values())
		{	
			
			
			
			port.recalculateExternalDirection(acc.getMinimumCoord(), acc.getMaximumCoord());
			if(port.getExternalFacing() == null)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.something_is_wrong", port.getPos());
				return false;
			}
			
			
			if(!(acc.WORLD.getTileEntity(port.getPos().offset(port.getExternalFacing().getOpposite())) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.beam_port_must_connect", port.getPos().offset(port.getExternalFacing().getOpposite()));
				return false;
			}
			if(port.getIOType() == IOType.INPUT)
			{
				inputs++;
			}
			
			if(port.getIOType() == IOType.OUTPUT)
			{
				outputs++;
			}
			
			
			
		}
		
		if(inputs != 1 || outputs != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_have_io", null);
			return false;
		}	
		

		return true;
	}
	
	// Multiblock Methods
	
	@Override
	public void onAcceleratorFormed()
	{
		 Accelerator acc = getAccelerator();

		 if (!getWorld().isRemote)
		{
			//beam ports
			for (TileAcceleratorBeamPort port :acc.getPartMap(TileAcceleratorBeamPort.class).values())
			{
				if(port.getIOType() == IOType.INPUT)
				{
					acc.input = port;
				}
				
				if(port.getIOType() == IOType.OUTPUT)
				{
					acc.output = port;
				}
			}		
		}
 
		 refreshStats();
		 super.onAcceleratorFormed();
		 acc.cooling = (long) (1.1*(acc.rawHeating+acc.getMaxExternalHeating()));
	}
	
	public void onMachineDisassembled()
	{

		super.onMachineDisassembled();
	}
	
	@Override
	public boolean onUpdateServer()
	{
		getAccelerator().errorCode = Accelerator.errorCode_Nothing;
		getAccelerator().beams.get(0).setParticleStack(null);
		pull();
		
		if (getAccelerator().isAcceleratorOn)
		{
			produceBeam();
		}
		else
		{
			resetBeam();
		}
		
		push();

		return super.onUpdateServer();
	}
	
	@Override
	protected void push()
	{
		if (getAccelerator().output != null && getAccelerator().input != null && getAccelerator().output.getExternalFacing() != null && getAccelerator().input.getExternalFacing() != null)
		{
			if (getAccelerator().output.getPos().offset(getAccelerator().output.getExternalFacing().getOpposite(), thickness - 1).equals(getAccelerator().input.getPos()))
			{
				TileEntity tile = getAccelerator().WORLD.getTileEntity(getAccelerator().output.getPos().offset(getAccelerator().output.getExternalFacing()));
				if (tile != null)
				{
					if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getAccelerator().output.getExternalFacing().getOpposite()))
					{
						IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getAccelerator().output.getExternalFacing().getOpposite());
						otherStorage.reciveParticle(getAccelerator().output.getExternalFacing().getOpposite(), getAccelerator().beams.get(2).getParticleStack());
						
					}
				}
			}
			else
			{
				TileEntity tile = getAccelerator().WORLD.getTileEntity(getAccelerator().output.getPos().offset(getAccelerator().output.getExternalFacing()));
				if (tile != null)
				{
					if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getAccelerator().output.getExternalFacing().getOpposite()))
					{
						IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getAccelerator().output.getExternalFacing().getOpposite());
						otherStorage.reciveParticle(getAccelerator().output.getExternalFacing().getOpposite(), getAccelerator().beams.get(1).getParticleStack());
						//System.out.println("Curve");
					}
				}
			}

			
		}

	}
	
	
	public long getEnergyLoss()
	{
		if(this.getAccelerator().beams.get(0).getParticleStack() != null)
		{
			Particle particle = this.getAccelerator().beams.get(0).getParticleStack().getParticle();
			ParticleStack particleIn = getAccelerator().beams.get(0).getParticleStack();
			return (long)(Math.pow(particle.getCharge(),2)/(6*Math.pow(particle.getMass(),4)*Math.pow(QMDConfig.beamDiverterRadius,2))*particleIn.getMeanEnergy());
		}
		
		return 0;
	}
	
	public long getMaxEnergy()
	{
		if(this.getAccelerator().beams.get(0).getParticleStack() != null)
		{
			Particle particle = this.getAccelerator().beams.get(0).getParticleStack().getParticle();
			return (long) (Math.pow(particle.getCharge()*getAccelerator().dipoleStrength*QMDConfig.beamDiverterRadius,2)/(2*particle.getMass())*1000000);
		}
		
		return 0;
	}
	
	
	
	private void refreshStats()
	{
		int energy = 0;
		int heat = 0;
		int parts= 0;
		double efficiency =0;
		
		double voltage = 0;
		for(TileAcceleratorMagnet magnet :getAccelerator().getPartMap(TileAcceleratorMagnet.class).values())
		{
			heat += magnet.heat;
			energy += magnet.basePower;
			parts++;
			efficiency += magnet.efficiency;
			getAccelerator().dipoleStrength = magnet.strength;
		}

		efficiency /= parts;
		getAccelerator().requiredEnergy =  (int) (energy/efficiency);
		getAccelerator().rawHeating = heat;
		getAccelerator().efficiency = efficiency;
		getAccelerator().acceleratingVoltage=(int) voltage;
		
	}

	
	// Recipe Stuff
	
	private void resetBeam()
	{
		getAccelerator().beams.get(1).setParticleStack(null);
		getAccelerator().beams.get(2).setParticleStack(null);
	}


	private void produceBeam()
	{
		if(this.getAccelerator().beams.get(0).getParticleStack() != null)
		{
			getAccelerator().beams.get(1).setParticleStack(this.getAccelerator().beams.get(0).getParticleStack().copy());
			getAccelerator().beams.get(2).setParticleStack(this.getAccelerator().beams.get(0).getParticleStack().copy());
			ParticleStack particleIn = getAccelerator().beams.get(0).getParticleStack();
			Particle particle = this.getAccelerator().beams.get(0).getParticleStack().getParticle();
			
			if(particleIn.getMeanEnergy() <= getMaxEnergy())
			{
				ParticleStack particleOut = getAccelerator().beams.get(1).getParticleStack();
				ParticleStack particleStraightOut = getAccelerator().beams.get(2).getParticleStack();
				
				particleOut.addMeanEnergy(-getEnergyLoss());
				particleOut.addFocus(-5*QMDConfig.beamAttenuationRate);
				particleStraightOut.addFocus(-5*QMDConfig.beamAttenuationRate);
				
				if(particleOut.getFocus() <= 0)
				{
					particleOut = null;
					getAccelerator().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
				}
				if(particleStraightOut.getFocus() <= 0)
				{
					particleStraightOut = null;
					getAccelerator().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
				}
			}	
		}
		else
		{
			resetBeam();
		}
	}
	
	
	
	// Network
	@Override
	public BeamDiverterUpdatePacket getUpdatePacket()
	{
		return new BeamDiverterUpdatePacket(getAccelerator().controller.getTilePos(),
				getAccelerator().isAcceleratorOn, getAccelerator().cooling, getAccelerator().rawHeating,getAccelerator().maxCoolantIn,getAccelerator().maxCoolantOut,
				getAccelerator().requiredEnergy, getAccelerator().efficiency, getAccelerator().acceleratingVoltage,
				getAccelerator().RFCavityNumber, getAccelerator().quadrupoleNumber, getAccelerator().quadrupoleStrength, getAccelerator().dipoleNumber, getAccelerator().dipoleStrength, getAccelerator().errorCode,
				getAccelerator().heatBuffer, getAccelerator().energyStorage, getAccelerator().tanks, getAccelerator().beams);
	}
	
	@Override
	public void onPacket(AcceleratorUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof BeamDiverterUpdatePacket)
		{
			BeamDiverterUpdatePacket packet = (BeamDiverterUpdatePacket) message;
		}
	}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);

	}
	
	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
	}
	
	

	@Override
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) 
	{
		return new ContainerBeamDiverterController(player, getAccelerator().controller);
	}
	
}
