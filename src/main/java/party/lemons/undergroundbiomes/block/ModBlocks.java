package party.lemons.undergroundbiomes.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.undergroundbiomes.UndergroundBiomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam on 3/08/2018.
 */
@Mod.EventBusSubscriber(modid = UndergroundBiomes.MODID)
@GameRegistry.ObjectHolder(UndergroundBiomes.MODID)
public class ModBlocks
{
	public static List<Block> blocks = new ArrayList<>();
	private static List<Block> oreDictStone = new ArrayList<>();
	private static List<Block> oreDictCobble = new ArrayList<>();
	private static Map<Block, Block> stoneSmelting = new HashMap<>();

	@GameRegistry.ObjectHolder("basalt")
	public static Block BASALT = Blocks.AIR;

	@GameRegistry.ObjectHolder("marble")
	public static Block MARBLE = Blocks.AIR;

	@GameRegistry.ObjectHolder("limestone")
	public static Block LIMESTONE = Blocks.AIR;

	@GameRegistry.ObjectHolder("norite")
	public static Block NORITE = Blocks.AIR;

	@GameRegistry.ObjectHolder("rhyolite")
	public static Block RHYOLITE = Blocks.AIR;

	@GameRegistry.ObjectHolder("slate")
	public static Block SLATE = Blocks.AIR;

	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
	{
		createStone("basalt", MapColor.BLACK, event.getRegistry());
		createStone("marble", MapColor.WHITE_STAINED_HARDENED_CLAY, event.getRegistry());
		createStone("limestone", MapColor.GREEN_STAINED_HARDENED_CLAY, event.getRegistry());
		createStone("norite", MapColor.GRAY_STAINED_HARDENED_CLAY, event.getRegistry());
		createStone("rhyolite", MapColor.ADOBE, event.getRegistry());
		createStone("slate", MapColor.BLUE_STAINED_HARDENED_CLAY, event.getRegistry());

		OreDictionary.registerOre("cobblestone", Blocks.HARDENED_CLAY);
		OreDictionary.registerOre("cobblestone", new ItemStack(Blocks.STONE, 1, 1));
		OreDictionary.registerOre("cobblestone", new ItemStack(Blocks.STONE, 1, 3));
		OreDictionary.registerOre("cobblestone", new ItemStack(Blocks.STONE, 1, 5));
	}

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event)
	{
		blocks.forEach(b -> event.getRegistry().register(new ItemBlock(b).setRegistryName(b.getRegistryName())));

		///All things below here require the item to exist to do
		///TODO: look for better place to do this
		oreDictCobble.forEach(b -> OreDictionary.registerOre("cobblestone", b));
		oreDictStone.forEach(b -> OreDictionary.registerOre("stone", b));

		for(Map.Entry<Block, Block> entry: stoneSmelting.entrySet())
		{
			GameRegistry.addSmelting(entry.getValue(), new ItemStack(entry.getKey()), 0.1F);
		}
	}

	/**
	 * Creates a basic stone along with it's polished, brick and cobblestone variants
	 * @param name
	 * @param color
	 * @param registry
	 */
	public static void createStone(String name, MapColor color, IForgeRegistry<Block> registry)
	{
		BlockBiomeStoneVariant cobble = new BlockBiomeStoneVariant(color);
		setStoneProperties(cobble, name, "_cobblestone");
		registry.register(cobble);

		BlockBiomeStoneVariant brick = new BlockBiomeStoneVariant(color);
		setStoneProperties(brick, name, "_brick");
		registry.register(brick);

		BlockBiomeStoneVariant polished = new BlockBiomeStoneVariant(color);
		setStoneProperties(polished, name, "_polished");
		registry.register(polished);

		BlockBiomeStone biomeStone = new BlockBiomeStone(color, cobble);
		setStoneProperties(biomeStone, name, "");
		registry.register(biomeStone);

		blocks.add(cobble);
		blocks.add(biomeStone);
		blocks.add(polished);
		blocks.add(brick);

		oreDictCobble.add(cobble);
		oreDictStone.add(biomeStone);

		stoneSmelting.put(biomeStone, cobble);
	}

	/**
	 * Sets basic stone properties
	 * @param block
	 * @param name
	 * @param variant
	 */
	private static void setStoneProperties(Block block, String name, String variant)
	{
		block.setTranslationKey(UndergroundBiomes.MODID + "." + name + variant);
		block.setRegistryName(UndergroundBiomes.MODID,  name + variant);
		block.setHardness(1.5F);
		block.setResistance(10.0F);
	}
}
