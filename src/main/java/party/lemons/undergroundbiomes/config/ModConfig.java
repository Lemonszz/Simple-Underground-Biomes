package party.lemons.undergroundbiomes.config;

import net.minecraftforge.common.config.Config;
import party.lemons.undergroundbiomes.UndergroundBiomes;
import party.lemons.undergroundbiomes.noise.FastNoise;

/**
 * Created by Sam on 6/08/2018.
 */
@Config(modid = UndergroundBiomes.MODID)
public class ModConfig
{
	public static BiomeWeights weights = new BiomeWeights();
	public static NoiseSettings noise = new NoiseSettings();
	public static OreSettings ores = new OreSettings();

	public static class BiomeWeights
	{
		public int DEFAULT_BIOME_WEIGHT = 5;

		public int VANILLA_STONE_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int BASALT_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int MARBLE_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int NORITE_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int LIMESTONE_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int RHYOLITE_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int SLATE_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int HARDENED_CLAY_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int ANDESITE_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int DIORITE_CLAY_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int GRANITE_CLAY_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int CYAN_CLAY_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int LIGHTGREY_CLAY_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int VANILLA_MIX_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int GREY_MIX_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int BLACK_MIX_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int WHITE_MIX_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int ORANGE_MIX_WEIGHT = DEFAULT_BIOME_WEIGHT;
		public int CLAY_MIX_WEIGHT = DEFAULT_BIOME_WEIGHT;
	}

	public static class NoiseSettings
	{
		public float BIOME_NOISE_FREQUENCY = 0.016F;
		public FastNoise.CellularDistanceFunction BIOME_NOISE_DISTANCE_FUNCTION = FastNoise.CellularDistanceFunction.Natural;

		public FastNoise.NoiseType SECONDARY_NOISE_TYPE = FastNoise.NoiseType.Value;
		public float SECONDARY_NOISE_FREQUENCY = 0.2F;
	}

	public static class OreSettings
	{
		public int BASALT_COAL_ATTEMPTS = 45;
		public int BASALT_COAL_MAX_Y = 120;

		public int HARD_CLAY_GOLD_ATTEMPTS = 25;
		public int HARD_CLAY_GOLD_MAX_Y = 60;
	}
}
