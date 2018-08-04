package party.lemons.undergroundbiomes.block;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.undergroundbiomes.UndergroundBiomes;

/**
 * Created by Sam on 4/08/2018.
 */
@Mod.EventBusSubscriber(modid = UndergroundBiomes.MODID, value = Side.CLIENT)
public class ModelRegistry
{
	@SubscribeEvent
	public static void onRegisterItemModels(ModelRegistryEvent event)
	{
		ModBlocks.blocks.forEach(b -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory")));
	}
}
