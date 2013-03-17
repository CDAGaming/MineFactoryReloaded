package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableZombiePigman implements IFactoryGrindable
{
	@Override
	public Class<?> getGrindableEntity()
	{
		return EntityPigZombie.class;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLiving entity, Random random)
	{
		List<MobDrop> drops = new ArrayList<MobDrop>();
		drops.add(new MobDrop(10, new ItemStack(Item.rottenFlesh)));
		drops.add(new MobDrop(10, new ItemStack(Item.goldNugget)));
		
		if(random.nextInt(1000) == 0)
		{
			drops.add(new MobDrop(10, new ItemStack(Item.sign)));
		}
		
		return drops;
	}

}
