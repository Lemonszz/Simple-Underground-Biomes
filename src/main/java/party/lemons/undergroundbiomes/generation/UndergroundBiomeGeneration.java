package party.lemons.undergroundbiomes.generation;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.undergroundbiomes.biome.CaveBiome;
import party.lemons.undergroundbiomes.biome.CaveBiomes;
import party.lemons.undergroundbiomes.config.ModConfig;
import party.lemons.undergroundbiomes.noise.FastNoise;
import party.lemons.undergroundbiomes.util.MathStuff;

import java.util.Random;

/**
 * Created by Sam on 2/08/2018.
 */
public class UndergroundBiomeGeneration implements IWorldGenerator
{
	private FastNoise generationNoise;
	private FastNoise secondaryNoise;

	private WorldGenMineableBiome basaltCoalGenerator;
	private WorldGenMineableBiome hardClayGoldGenerator;

	public UndergroundBiomeGeneration()
	{
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		generationNoise = null;
		secondaryNoise = null;
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		//Using this event to set up the noise as it requires the world seed to setup.
		generationNoise = new FastNoise((int) event.getWorld().getSeed());
		generationNoise.SetNoiseType(FastNoise.NoiseType.Cellular);
		generationNoise.SetFrequency(ModConfig.noise.BIOME_NOISE_FREQUENCY);
		generationNoise.SetCellularDistanceFunction(ModConfig.noise.BIOME_NOISE_DISTANCE_FUNCTION);
		generationNoise.SetGradientPerturbAmp(70);

		secondaryNoise = new FastNoise((int)event.getWorld().getSeed());
		secondaryNoise.SetNoiseType(ModConfig.noise.SECONDARY_NOISE_TYPE);
		secondaryNoise.SetFrequency(ModConfig.noise.SECONDARY_NOISE_FREQUENCY);
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
		if(!canGenerationInWorld(event.getWorld()) || generationNoise == null || secondaryNoise == null)
			return;

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

	public void initFeatures()
	{
		GameRegistry.registerWorldGenerator(this, 999);

		basaltCoalGenerator = new WorldGenMineableBiome(Blocks.COAL_ORE.getDefaultState(), 17, CaveBiomes.BASALT);
		hardClayGoldGenerator = new WorldGenMineableBiome(Blocks.GOLD_ORE.getDefaultState(), 5, CaveBiomes.BASALT);
	}

	public boolean canGenerationInWorld(World world)
	{
		return world.provider.getDimension() == 0;
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if(!canGenerationInWorld(world) || generationNoise == null || secondaryNoise == null)
			return;

		final int BASALT_COAL_ATTEMPTS = ModConfig.ores.BASALT_COAL_ATTEMPTS;
		final int BASALT_COAL_MAX_LEVEL = ModConfig.ores.BASALT_COAL_MAX_Y;
		generateOre( basaltCoalGenerator, rand, chunkX, chunkZ, world, BASALT_COAL_MAX_LEVEL, BASALT_COAL_ATTEMPTS);

		final int MESA_GOLD_ATTEMPTS = ModConfig.ores.HARD_CLAY_GOLD_ATTEMPTS;
		final int MESA_GOLD_MAX_LEVEL = ModConfig.ores.HARD_CLAY_GOLD_MAX_Y;
		generateOre(hardClayGoldGenerator, rand, chunkX, chunkZ, world, MESA_GOLD_MAX_LEVEL, MESA_GOLD_ATTEMPTS);
	}

	public void generateOre(WorldGenerator generator, Random rand, int chunkX, int chunkZ, World world, int maxLevel, int attempts)
	{
		for(int i = 0; i < attempts; i++)
		{
			int x = chunkX * 16 + rand.nextInt(16);
			int y = rand.nextInt(maxLevel);
			int z = chunkZ * 16 + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}
}
