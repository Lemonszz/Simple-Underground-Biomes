package party.lemons.undergroundbiomes.generation;

import com.google.common.base.Predicate;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import party.lemons.undergroundbiomes.UndergroundBiomes;
import party.lemons.undergroundbiomes.biome.CaveBiome;

import java.util.Random;

/**
 * Created by Sam on 6/08/2018.
 */
public class WorldGenMineableBiome extends WorldGenMinable
{
	private final CaveBiome[] biomes;

	public WorldGenMineableBiome(IBlockState state, int blockCount, CaveBiome... biomes)
	{
		this(state, blockCount, new StonePredicate(), biomes);
	}

	public WorldGenMineableBiome(IBlockState state, int blockCount, Predicate<IBlockState> p_i45631_3_, CaveBiome... biomes)
	{
		super(state, blockCount, p_i45631_3_);

		this.biomes = biomes;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		CaveBiome biome = UndergroundBiomes.generation.getBiomeAt(position);
		boolean found = false;

		for(int i = 0; i < biomes.length; i++)
		{
			if(biomes[i] == biome)
			{
				found = true;
				break;
			}
		}

		if(!found)
			return false;

		return super.generate(worldIn, rand, position);
	}


	static class StonePredicate implements Predicate<IBlockState>
	{
		private StonePredicate()
		{
		}

		public boolean apply(IBlockState p_apply_1_)
		{
			if (p_apply_1_ != null && p_apply_1_.getBlock() == Blocks.STONE)
			{
				BlockStone.EnumType blockstone$enumtype = (BlockStone.EnumType)p_apply_1_.getValue(BlockStone.VARIANT);
				return blockstone$enumtype.isNatural();
			}
			else
			{
				return false;
			}
		}
	}
}
