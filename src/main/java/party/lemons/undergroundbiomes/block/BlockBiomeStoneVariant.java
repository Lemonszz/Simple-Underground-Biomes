package party.lemons.undergroundbiomes.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by Sam on 4/08/2018.
 */
public class BlockBiomeStoneVariant extends BlockOreDict
{
	public BlockBiomeStoneVariant(MapColor color, String oreDictName)
	{
		super(Material.ROCK, color, oreDictName);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setSoundType(SoundType.STONE);
	}
}
