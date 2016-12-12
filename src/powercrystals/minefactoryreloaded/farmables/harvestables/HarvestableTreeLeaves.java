package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class HarvestableTreeLeaves extends HarvestableShearable
{
	public HarvestableTreeLeaves(Block block)
	{
		super(block, powercrystals.minefactoryreloaded.api.HarvestType.TreeLeaf);
	}

	@Override
	public void postHarvest(World world, BlockPos pos)
	{
		Block id = getPlant();

		notifyBlock(world, x, y - 1, z, id);
		notifyBlock(world, x - 1, y, z, id);
		notifyBlock(world, x + 1, y, z, id);
		notifyBlock(world, x, y, z - 1, id);
		notifyBlock(world, x, y, z + 1, id);
		notifyBlock(world, x, y + 1, z, id);
	}

	protected void notifyBlock(World world, BlockPos pos, Block id)
	{
		Block block = world.getBlock(x, y, z);
		if (!block.isLeaves(world, x, y, z))
			world.notifyBlockOfNeighborChange(x, y, z, id);
	}
}
