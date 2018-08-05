package party.lemons.undergroundbiomes.generation;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.undergroundbiomes.biome.CaveBiome;
import party.lemons.undergroundbiomes.biome.CaveBiomes;
import party.lemons.undergroundbiomes.noise.FastNoise;
import party.lemons.undergroundbiomes.util.MathStuff;

/**
 * Created by Sam on 2/08/2018.
 */
public class UndergroundBiomeGeneration
{
	private FastNoise generationNoise;
	private FastNoise secondaryNoise;

	public UndergroundBiomeGeneration()
	{
		generationNoise = new FastNoise();
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		//Using this event to set up the noise as it requires the world seed to setup.
		generationNoise = new FastNoise((int) event.getWorld().getSeed());
		generationNoise.SetNoiseType(FastNoise.NoiseType.Cellular);
		generationNoise.SetFrequency(0.016F);
		generationNoise.SetCellularDistanceFunction(FastNoise.CellularDistanceFunction.Natural);
		generationNoise.SetGradientPerturbAmp(70);

		secondaryNoise = new FastNoise((int)event.getWorld().getSeed());
		secondaryNoise.SetFrequency(0.2F);
	}

	/**
	 * Gets the cave biome at a BlockPos
	 * @param pos
	 * @return CaveBiome
	 */
	public CaveBiome getBiomeAt(BlockPos pos)
	{
		float biome = generationNoise.GetNoise(pos.getX(), pos.getY(), pos.getZ());
		if(biome < 0) biome *= -1;

		double scaled = MathStuff.map(biome, 0, 1, 0, CaveBiomes.getWeightedList().size());

		return CaveBiomes.getWeightedList().get((int) scaled);
	}

	@SubscribeEvent
	public void populateChunk(PopulateChunkEvent.Post event)
	{
		//Main generation

		//Replace stone
		Chunk chunk = event.getWorld().getChunk(event.getChunkX(), event.getChunkZ());
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())	//Loop through every block in a chunk
		{
			if (storage != null)
			{
				for (int x = 0; x < 16; ++x)
				{
					for (int y = 0; y < 16; ++y)
					{
						for (int z = 0; z < 16; ++z)
						{
							//TODO: what if we're replacing stone with stone? Is it quicker to not check?
							if(storage.get(x, y, z).getBlock() == Blocks.STONE)	//Do replacement
							{
								int smoothingScale = 5;
								int offSetX = event.getWorld().rand.nextBoolean() ? (-1  + event.getWorld().rand.nextInt(3)) * event.getWorld().rand.nextInt(smoothingScale) : 0;
								int offSetZ = event.getWorld().rand.nextBoolean() ? (-1  + event.getWorld().rand.nextInt(3)) * event.getWorld().rand.nextInt(smoothingScale) : 0;
								int offSetY = event.getWorld().rand.nextBoolean() ? (-1  + event.getWorld().rand.nextInt(3)) * event.getWorld().rand.nextInt(smoothingScale) : 0;

								BlockPos pos = new BlockPos(offSetX +(chunk.x * 16) + x, (storage.getYLocation() + (y + offSetY)), offSetZ + (chunk.z * 16) + z);

								storage.set(x,y,z, getBiomeAt(pos).getReplacementBlock(secondaryNoise, pos));
							}

						}
					}
				}
			}
		}

		//TODO: Other generation
	}

}
