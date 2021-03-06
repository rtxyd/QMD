package lach_01298.qmd.recipes;

import static nc.config.NCConfig.ore_dict_raw_material_recipes;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.MaterialEnums;
import lach_01298.qmd.item.QMDItems;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.enumm.MetaEnums.IngotType;
import nc.init.NCArmor;
import nc.init.NCBlocks;
import nc.init.NCItems;
import nc.init.NCTools;
import nc.radiation.RadArmor;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.vanilla.CraftingRecipeHandler;
import nc.recipe.vanilla.ingredient.BucketIngredient;
import nc.recipe.vanilla.recipe.ShapedEnergyRecipe;
import nc.recipe.vanilla.recipe.ShapedFluidRecipe;
import nc.recipe.vanilla.recipe.ShapelessArmorRadShieldingRecipe;
import nc.recipe.vanilla.recipe.ShapelessFluidRecipe;
import nc.util.ArmorHelper;
import nc.util.ItemStackHelper;
import nc.util.NCUtil;
import nc.util.OreDictHelper;
import nc.util.RegistryHelper;
import nc.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class QMDCraftingRecipeHandler 
{
	
	public static void registerCraftingRecipes() 
	{
		addShapedOreRecipe(new ItemStack(QMDItems.canister,4), new Object[] {"TTT", "T T", "TTT", 'T', "ingotTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.beamline,6), new Object[] {"SSS", "   ", "SSS", 'S', "ingotStainlessSteel"});
		tools("ingotTungstenCarbide", QMDItems.sword_tungsten_carbide, QMDItems.pickaxe_tungsten_carbide, QMDItems.shovel_tungsten_carbide, QMDItems.axe_tungsten_carbide,QMDItems.hoe_tungsten_carbide);
		addShapedOreRecipe(new ItemStack(QMDBlocks.fissionReflector, 1, 0), new Object[] {"TTT", "TFT", "TTT", 'T', "ingotTungstenCarbide", 'F', "steelFrame"});
		addShapedOreRecipe(QMDBlocks.oreLeacher, new Object[] {"PRP", "CFC", "PHP", 'P', "plateElite", 'F', "chassis", 'C', NCBlocks.chemical_reactor, 'R', NCBlocks.rock_crusher, 'H', "ingotHardCarbon"});
		addShapedOreRecipe(QMDBlocks.irradiator, new Object[] {"TPT", "PFP", "TPT", 'P', "plateDU", 'F', "chassis", 'T', "ingotTungsten"});
		
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.WATER.getID()), new Object[] {new BucketIngredient("water"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.IRON.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotIron", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.REDSTONE.getID()), new Object[] {"III", "ICI", "III", 'I', "dustRedstone", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.QUARTZ.getID()), new Object[] {"III", "ICI", "III", 'I', "gemQuartz", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.OBSIDIAN.getID()), new Object[] {"DID", "ICI", "DID", 'I', "obsidian", 'D', "dustObsidian" ,'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.NETHER_BRICK.getID()), new Object[] {"DID", "ICI", "DID", 'I', Blocks.NETHER_BRICK, 'D', "ingotBrickNether" ,'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.GLOWSTONE.getID()), new Object[] {"III", "ICI", "III", 'I', "dustGlowstone", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.LAPIS.getID()), new Object[] {"III", "ICI", "III", 'I', "gemLapis", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.GOLD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotGold", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.PRISMARINE.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemPrismarine", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.SLIME.getID()), new Object[] {"III", "ICI", "III", 'I', "slimeball", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.END_STONE.getID()), new Object[] {"DID", "ICI", "DID", 'I', "endstone", 'D',"dustEndstone",'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.PURPUR.getID()), new Object[] {"DID", "ICI", "DID", 'I', Blocks.PURPUR_BLOCK, 'D', Items.CHORUS_FRUIT_POPPED,'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.DIAMOND.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemDiamond", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.EMERALD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemEmerald", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.COPPER.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotCopper", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.TIN.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotTin", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LEAD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotLead", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.BORON.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotBoron", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LITHIUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotLithium", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.MAGNESIUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotMagnesium", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.MANGANESE.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotManganese", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ALUMINUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotAluminum", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.SILVER.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotSilver", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.FLUORITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemFluorite", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.VILLIAUMITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemVilliaumite", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.CAROBBIITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemCarobbiite", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ARSENIC.getID()), new Object[] {"III", "ICI", "III", 'I', "dustArsenic", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_NITROGEN.getID()), new Object[] {new BucketIngredient("liquid_nitrogen"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_HELIUM.getID()), new Object[] {new BucketIngredient("liquid_helium"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ENDERIUM.getID()), new Object[] {new BucketIngredient("enderium"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.CRYOTHEUM.getID()), new Object[] {new BucketIngredient("cryotheum"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});

		
		addShapedOreRecipe(QMDBlocks.linearAcceleratorController, new Object[] {"PEP", "BFB", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'B', "processorBasic", 'F', QMDBlocks.acceleratorCasing});
		addShapedOreRecipe(QMDBlocks.ringAcceleratorController, new Object[] {"PEP", "AFA", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'A', "processorAdvanced", 'F', QMDBlocks.acceleratorCasing});
		addShapedOreRecipe(QMDBlocks.beamDiverterController, new Object[] {"PTP", "AFA", "PTP", 'P', "plateAdvanced", 'T', "ingotTough", 'A', "processorAdvanced", 'F', QMDBlocks.acceleratorCasing});

		
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCasing,8), new Object[] {"STS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame"});
		addShapelessOreRecipe(QMDBlocks.acceleratorCasing, new Object[] {QMDBlocks.acceleratorGlass});
		addShapelessOreRecipe(QMDBlocks.acceleratorGlass, new Object[] {QMDBlocks.acceleratorCasing, "blockGlass"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorBeamPort, 4), new Object[] {"STS", "BFB", "STS", 'S', "ingotStainlessSteel", 'T', "ingotTough", 'B', QMDBlocks.beamline, 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorInlet,4), new Object[] {"SIS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "servo"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorOutlet,4), new Object[] {"STS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "servo"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorEnergyPort,4), new Object[] {"SIS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorBeam,3), new Object[] {"SSS", "BBB", "SSS", 'S', "ingotStainlessSteel", 'B', QMDBlocks.beamline});
		addShapedOreRecipe(QMDBlocks.acceleratorSource, new Object[] {"AAA", " CT", "AAA", 'A', "plateAdvanced", 'C', QMDBlocks.acceleratorCasing, 'T', QMDItems.tungsten_filament});

		
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,EnumTypes.MagnetType.COPPER.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotCopper"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,EnumTypes.MagnetType.MAGNESIUM_DIBORIDE.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotMagnesiumDiboride"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,EnumTypes.MagnetType.NIOBIUM_TIN.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,EnumTypes.MagnetType.BSCCO.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "wireBSCCO"});
		
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorYoke,4), new Object[] {"IBI", "IBI", "IBI", 'I', "ingotIron",'B', "bioplastic"});
		
		addShapedOreRecipe(new ItemStack(QMDItems.part,4,MaterialEnums.PartType.EMTPY_COOLER.getID()), new Object[] {"STS", "SHS", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'H', "ingotThermoconducting"});
		addShapedOreRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID()), new Object[] {"STS", "SBS", "STS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'B', "processorBasic"});
		
		
		
		
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,2,EnumTypes.MagnetType.COPPER.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotCopper"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,2,EnumTypes.MagnetType.MAGNESIUM_DIBORIDE.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotMagnesiumDiboride"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,2,EnumTypes.MagnetType.NIOBIUM_TIN.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotNiobiumTin"});
		
		addShapedOreRecipe(QMDBlocks.targetChamberController, new Object[] {"PTP", "BFB", "PTP", 'P', "plateElite", 'T', "ingotTough", 'B', "processorBasic", 'F', QMDBlocks.particleChamberCasing});
		addShapedOreRecipe(QMDBlocks.decayChamberController, new Object[] {"PTP", "BFB", "PTP", 'P', "plateElite", 'T', "ingotTough", 'B', "processorBasic", 'F', NCBlocks.decay_hastener});
		
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberCasing,8), new Object[] {"STS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'F', "steelFrame"});
		addShapelessOreRecipe(QMDBlocks.particleChamberCasing, new Object[] {QMDBlocks.particleChamberGlass});
		addShapelessOreRecipe(QMDBlocks.particleChamberGlass, new Object[] {QMDBlocks.particleChamberCasing, "blockGlass"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberPort, 4), new Object[] {"SHS", "VFV", "SHS", 'S', "ingotStainlessSteel", 'H', Blocks.HOPPER, 'V', "servo", 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberEnergyPort,4), new Object[] {"SIS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'F', "steelFrame", 'I', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberBeam,3), new Object[] {"STS", "BBB", "STS", 'S', "ingotStainlessSteel", 'B', QMDBlocks.beamline, 'T', "ingotTungsten"});
		addShapedOreRecipe(QMDBlocks.particleChamber, new Object[] {"NSN", "NCN", "NSN", 'S', "ingotStainlessSteel", 'C', "chassis", 'N', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberBeamPort, 4), new Object[] {"STS", "BFB", "STS", 'S', "ingotStainlessSteel", 'T', "ingotTungsten", 'B', QMDBlocks.beamline, 'F', "steelFrame"});

		
		addShapedOreRecipe(new ItemStack(QMDItems.source_sodium_22), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotSodium22",'B', "bioplastic"});
		addShapedOreRecipe(new ItemStack(QMDItems.source_cobalt_60), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotCobalt60",'B', "bioplastic"});
		addShapedOreRecipe(new ItemStack(QMDItems.source_iridium_192), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotIridium192",'B', "bioplastic"});
		
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.EM_CALORIMETER.getID()), new Object[] {"SSS", "SCS", "SSS", 'S',  new ItemStack(QMDItems.part,1,MaterialEnums.PartType.SCINTILLATOR_PWO.getID()), 'C', new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.HADRON_CALORIMETER.getID()), new Object[] {"SSS", "SCS", "SSS", 'S',  new ItemStack(QMDItems.part,1,MaterialEnums.PartType.SCINTILLATOR_PLASTIC.getID()), 'C', new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.SILLICON_TRACKER.getID()), new Object[] {"BAB", "ACA", "BAB", 'B',  "processorBasic", 'A', "processorAdvanced", 'C', new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID())});

		
		
		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER,4), new Object[] {"dustCoal","dustSulfur","dustSodiumNitrate","dustSodiumNitrate"});
		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER,4), new Object[] {"dustCharcoal","dustSulfur","dustSodiumNitrate","dustSodiumNitrate"});
		
		addShapedOreRecipe(QMDBlocks.rtgStrontium, new Object[] {"AGA", "GSG", "AGA", 'S', "ingotStrontium90",'A', "plateAdvanced", 'G', "ingotGraphite"});
		
	}
	
	public static void blockCompress(Block blockOut, int metaOut, String itemOutOreName, Object itemIn)
	{
		addShapedOreRecipe(
				OreDictHelper.getPrioritisedCraftingStack(new ItemStack(blockOut, 1, metaOut), itemOutOreName),
				new Object[] { "III", "III", "III", 'I', itemIn });
	}

	public static void blockOpen(Item itemOut, int metaOut, String itemOutOreName, Object itemIn)
	{
		addShapelessOreRecipe(
				OreDictHelper.getPrioritisedCraftingStack(new ItemStack(itemOut, 9, metaOut), itemOutOreName),
				new Object[] { itemIn });
	}

	public static void tools(Object material, Item sword, Item pick, Item shovel, Item axe, Item hoe)
	{
		addShapedOreRecipe(sword, new Object[] { "M", "M", "S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(pick, new Object[] { "MMM", " S ", " S ", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(shovel, new Object[] { "M", "S", "S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(axe, new Object[] { "MM", "MS", " S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(axe, new Object[] { "MM", "SM", "S ", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(hoe, new Object[] { "MM", " S", " S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(hoe, new Object[] { "MM", "S ", "S ", 'M', material, 'S', "stickWood" });
	}

	public static void armor(Object material, Item helm, Item chest, Item legs, Item boots)
	{
		addShapedOreRecipe(helm, new Object[] { "MMM", "M M", 'M', material });
		addShapedOreRecipe(chest, new Object[] { "M M", "MMM", "MMM", 'M', material });
		addShapedOreRecipe(legs, new Object[] { "MMM", "M M", "M M", 'M', material });
		addShapedOreRecipe(boots, new Object[] { "M M", "M M", 'M', material });
	}

	private static final Map<String, Integer> RECIPE_COUNT_MAP = new HashMap<String, Integer>();

	public static void addShapedOreRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedOreRecipe.class, out, inputs);
	}

	public static void addShapedEnergyRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedEnergyRecipe.class, out, inputs);
	}

	public static void addShapedFluidRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedFluidRecipe.class, out, inputs);
	}

	public static void addShapelessOreRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessOreRecipe.class, out, inputs);
	}

	public static void addShapelessFluidRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessFluidRecipe.class, out, inputs);
	}

	public static void addShapelessArmorUpgradeRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessArmorRadShieldingRecipe.class, out, inputs);
	}

	public static void registerRecipe(Class<? extends IRecipe> clazz, Object out, Object... inputs)
	{
		if (out == null || Lists.newArrayList(inputs).contains(null))
			return;
		ItemStack outStack = ItemStackHelper.fixItemStack(out);
		if (!outStack.isEmpty() && inputs != null)
		{
			String outName = outStack.getTranslationKey();
			if (RECIPE_COUNT_MAP.containsKey(outName))
			{
				int count = RECIPE_COUNT_MAP.get(outName);
				RECIPE_COUNT_MAP.put(outName, count + 1);
				outName = outName + "_" + count;
			}
			else
				RECIPE_COUNT_MAP.put(outName, 1);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID, outName);
			try
			{
				IRecipe recipe = NCUtil.newInstance(clazz, location, outStack, inputs);
				recipe.setRegistryName(location);
				ForgeRegistries.RECIPES.register(recipe);
			}
			catch (Exception e)
			{

			}
		}
	}
}