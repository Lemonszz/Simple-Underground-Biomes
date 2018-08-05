package party.lemons.undergroundbiomes.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import party.lemons.undergroundbiomes.noise.FastNoise;
import party.lemons.undergroundbiomes.util.MathStuff;

/**
 * Created by Sam on 5/08/2018.
 */
public class CaveBiomeMixed extends CaveBiome
{
	private IBlockState[] stones;

	public CaveBiomeMixed(int weight, IBlockState... stoneReplacements)
	{
		super(stoneReplacements[0], weight);

		this.stones = stoneReplacements;
	}

	public CaveBiomeMixed(IBlockState... stoneReplacements)
	{
		this(5, stoneReplacements[0]);
	}

	@Override
	public IBlockState getReplacementBlock(FastNoise noise, BlockPos pos)
	{
		double value = noise.GetValue(pos.getX(), pos.getY(), pos.getZ());
		if(value < 0)
			value *= -1;
		double mapped = MathStuff.map(value, 0, 1, 0, stones.length);

		return stones[(int) mapped];
	}
}
