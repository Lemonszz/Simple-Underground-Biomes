package party.lemons.undergroundbiomes.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by Sam on 4/08/2018.
 */
public class BlockBiomeStoneVariant extends Block
{
	public BlockBiomeStoneVariant(MapColor color)
	{
		super(Material.ROCK, color);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setSoundType(SoundType.STONE);
	}
}
