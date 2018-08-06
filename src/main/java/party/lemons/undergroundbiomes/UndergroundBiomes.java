package party.lemons.undergroundbiomes;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import party.lemons.undergroundbiomes.generation.UndergroundBiomeGeneration;

import static party.lemons.undergroundbiomes.UndergroundBiomes.MODID;
import static party.lemons.undergroundbiomes.UndergroundBiomes.MODNAME;
import static party.lemons.undergroundbiomes.UndergroundBiomes.VERSION;
import static party.lemons.undergroundbiomes.UndergroundBiomes.UPDATE;

/**
 * Created by Sam on 2/08/2018.
 */
@Mod(modid = MODID, name = MODNAME, version = VERSION, updateJSON = UPDATE)
public class UndergroundBiomes
{
	public static final String MODID = "simpleundergroundbiomes";
	public static final String MODNAME = "Underground Biomes";
	public static final String VERSION = "0.3.0";
	public static final String UPDATE = "https://raw.githubusercontent.com/Lemonszz/Simple-Underground-Biomes/master/update.json";

	public static UndergroundBiomeGeneration generation;

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		generation = new UndergroundBiomeGeneration();

		MinecraftForge.EVENT_BUS.register(generation);
		MinecraftForge.TERRAIN_GEN_BUS.register(generation);
}

	@Mod.EventHandler
	public void onPostInit(FMLPostInitializationEvent event)
	{
		generation.initFeatures();
	}
}
