package party.lemons.undergroundbiomes.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * Created by Sam on 8/08/2018.
 */
public class BlockOreDict extends Block
{
	private final String oredictName;

	public BlockOreDict(Material blockMaterialIn, MapColor blockMapColorIn, String oredictName)
	{
		super(blockMaterialIn, blockMapColorIn);
		this.oredictName = oredictName;
	}

	public BlockOreDict(Material materialIn, String oredictName)
	{
		this(materialIn, MapColor.GRAY, oredictName);
	}

	public String getOreDictName()
	{
		return oredictName;
	}
}
