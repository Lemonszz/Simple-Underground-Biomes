package party.lemons.undergroundbiomes.biome;

import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.undergroundbiomes.UndergroundBiomes;
import party.lemons.undergroundbiomes.block.ModBlocks;
import party.lemons.undergroundbiomes.config.ModConfig;
import party.lemons.undergroundbiomes.util.MathStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sam on 2/08/2018.
 */
@Mod.EventBusSubscriber(modid = UndergroundBiomes.MODID)
@GameRegistry.ObjectHolder(UndergroundBiomes.MODID)
public class CaveBiomes
{
	private static List<CaveBiome> weightedList;

	@SubscribeEvent
	public static void onRegisterCaveBiome(RegistryEvent.Register<CaveBiome> event)
	{
		event.getRegistry().registerAll(
				new CaveBiome(Blocks.STONE.getDefaultState(), ModConfig.weights.VANILLA_STONE_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "stone"),
				new CaveBiome(ModBlocks.BASALT.getDefaultState(), ModConfig.weights.BASALT_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "basalt"),
				new CaveBiome(ModBlocks.MARBLE.getDefaultState(), ModConfig.weights.MARBLE_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "marble"),
				new CaveBiome(ModBlocks.SLATE.getDefaultState(), ModConfig.weights.SLATE_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "slate"),
				new CaveBiome(ModBlocks.LIMESTONE.getDefaultState(), ModConfig.weights.LIMESTONE_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "limestone"),
				new CaveBiome(ModBlocks.NORITE.getDefaultState(), ModConfig.weights.NORITE_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "norite"),
				new CaveBiome(ModBlocks.RHYOLITE.getDefaultState(), ModConfig.weights.RHYOLITE_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "rhyloite"),
				new CaveBiome(Blocks.HARDENED_CLAY.getDefaultState(), ModConfig.weights.HARDENED_CLAY_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "adobe"),
				new CaveBiome(Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN), ModConfig.weights.CYAN_CLAY_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "cyan"),
				new CaveBiome(Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER), ModConfig.weights.LIGHTGREY_CLAY_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "lightgrey"),
				new CaveBiome(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),ModConfig.weights.ANDESITE_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "andesite"),
				new CaveBiome(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), ModConfig.weights.DIORITE_CLAY_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "diorite"),
				new CaveBiome(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), ModConfig.weights.GRANITE_CLAY_WEIGHT).setRegistryName(UndergroundBiomes.MODID, "granite"),

				new CaveBiomeMixed(ModConfig.weights.VANILLA_MIX_WEIGHT,
						Blocks.STONE.getDefaultState(),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)
						).setRegistryName(UndergroundBiomes.MODID, "vanilla_mix"),

				new CaveBiomeMixed(ModConfig.weights.GREY_MIX_WEIGHT,
						Blocks.STONE.getDefaultState(),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
						ModBlocks.BASALT.getDefaultState(),
						ModBlocks.NORITE.getDefaultState()
						).setRegistryName(UndergroundBiomes.MODID,"greyland"),

				new CaveBiomeMixed(ModConfig.weights.BLACK_MIX_WEIGHT,ModBlocks.BASALT.getDefaultState(), ModBlocks.SLATE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "darkness"),
				new CaveBiomeMixed(ModConfig.weights.WHITE_MIX_WEIGHT,ModBlocks.MARBLE.getDefaultState(), Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE)).setRegistryName(UndergroundBiomes.MODID, "lightness"),
				new CaveBiomeMixed(ModConfig.weights.ORANGE_MIX_WEIGHT,ModBlocks.RHYOLITE.getDefaultState(), Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)).setRegistryName(UndergroundBiomes.MODID, "orange"),
				new CaveBiomeMixed(ModConfig.weights.CLAY_MIX_WEIGHT,Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN),Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER)).setRegistryName(UndergroundBiomes.MODID, "mixed_clay")
		);
	}


	/**
	 * Gets a "weighted" list of biomes
	 * @return
	 */
	public static List<CaveBiome> getWeightedList()
	{
		//This feels dumb but i can't be bothered working out a better solution it's 3am leave me alone
		if(weightedList == null)
		{
			weightedList = new ArrayList<>();
			for(CaveBiome b : CaveBiomeRegistry.CAVE_BIOME_REGISTRY.getValuesCollection())
			{
				int weight = b.getWeight();
				for(int i = 0; i < weight; i++)	//add the biome to the list more for higher weight biomes
				{
					weightedList.add(b);
				}
			}
		}
		return weightedList;
	}

	@GameRegistry.ObjectHolder("basalt")
	public static CaveBiome BASALT = null;
}
