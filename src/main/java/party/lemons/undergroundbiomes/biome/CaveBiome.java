package party.lemons.undergroundbiomes.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import party.lemons.undergroundbiomes.config.ModConfig;
import party.lemons.undergroundbiomes.noise.FastNoise;

/**
 * Created by Sam on 2/08/2018.
 */
public class CaveBiome extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<CaveBiome>
{
	private IBlockState stoneReplacement = Blocks.STONE.getDefaultState();
	private int weight;


	public CaveBiome(IBlockState stoneReplacement, int weight)
	{
		this.stoneReplacement = stoneReplacement;
		this.weight = weight;
	}

	public CaveBiome(IBlockState stoneReplacement)
	{
		this(stoneReplacement, ModConfig.weights.DEFAULT_BIOME_WEIGHT);
	}

	public IBlockState getReplacementBlock(FastNoise noise, BlockPos pos)
	{
		return stoneReplacement;
	}

	/***
	 * Weight is basically how often a biome will show up
	 * High weight = more often
	 * Low weight = rarer
	 * Default weight = 5
	 * @return
	 */
	public int getWeight()
	{
		return weight;
	}
}
