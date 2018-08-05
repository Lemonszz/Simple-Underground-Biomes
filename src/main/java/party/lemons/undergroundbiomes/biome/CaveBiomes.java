package party.lemons.undergroundbiomes.biome;

import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.undergroundbiomes.UndergroundBiomes;
import party.lemons.undergroundbiomes.block.ModBlocks;
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
				new CaveBiome(Blocks.STONE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "stone"),
				new CaveBiome(ModBlocks.BASALT.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "basalt"),
				new CaveBiome(ModBlocks.MARBLE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "marble"),
				new CaveBiome(ModBlocks.SLATE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "slate"),
				new CaveBiome(ModBlocks.LIMESTONE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "limestone"),
				new CaveBiome(ModBlocks.NORITE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "norite"),
				new CaveBiome(ModBlocks.RHYOLITE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "rhyloite"),
				new CaveBiome(Blocks.HARDENED_CLAY.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "adobe"),
				new CaveBiome(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE)).setRegistryName(UndergroundBiomes.MODID, "andesite"),
				new CaveBiome(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE)).setRegistryName(UndergroundBiomes.MODID, "diorite"),
				new CaveBiome(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)).setRegistryName(UndergroundBiomes.MODID, "granite"),

				new CaveBiomeMixed(
						Blocks.STONE.getDefaultState(),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)
						).setRegistryName(UndergroundBiomes.MODID, "vanilla_mix"),

				new CaveBiomeMixed(
						Blocks.STONE.getDefaultState(),
						Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
						ModBlocks.BASALT.getDefaultState(),
						ModBlocks.NORITE.getDefaultState()
						).setRegistryName(UndergroundBiomes.MODID,"greyland"),

				new CaveBiomeMixed(ModBlocks.BASALT.getDefaultState(), ModBlocks.SLATE.getDefaultState()).setRegistryName(UndergroundBiomes.MODID, "darkness"),
				new CaveBiomeMixed(ModBlocks.MARBLE.getDefaultState(), Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE)).setRegistryName(UndergroundBiomes.MODID, "lightness"),
				new CaveBiomeMixed(ModBlocks.RHYOLITE.getDefaultState(), Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)).setRegistryName(UndergroundBiomes.MODID, "orange")
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
}
