package party.lemons.undergroundbiomes.biome;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import party.lemons.undergroundbiomes.UndergroundBiomes;

/**
 * Created by Sam on 2/08/2018.
 */
@Mod.EventBusSubscriber
public class CaveBiomeRegistry
{
	public static IForgeRegistry<CaveBiome> CAVE_BIOME_REGISTRY;

	@SubscribeEvent
	public static void createRegistry(RegistryEvent.NewRegistry event)
	{
		CAVE_BIOME_REGISTRY =
				new RegistryBuilder<CaveBiome>()
				.setType(CaveBiome.class)
				.setName(new ResourceLocation(UndergroundBiomes.MODID, "cave_biomes"))
				.create();
	}
}
