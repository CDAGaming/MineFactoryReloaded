package powercrystals.minefactoryreloaded.block.transport;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

import java.util.ArrayList;
import java.util.List;

public class BlockRailPassengerDropoff extends BlockFactoryRail {

	public BlockRailPassengerDropoff() {

		super(true, false);
		setUnlocalizedName("mfr.rail.passenger.dropoff");
		setRegistryName(MineFactoryReloadedCore.modId, "rail_passenger_dropoff");
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart minecart, BlockPos pos) {

		if (world.isRemote || minecart.getPassengers().size() < 1)
			return;

		Class<? extends EntityLivingBase> target = isPowered(world, pos) ? EntityLiving.class : EntityPlayer.class;
		for (int i = minecart.getPassengers().size(); i-- > 0; ) {
			Entity entity = minecart.getPassengers().get(i);
			if (!target.isInstance(entity))
				continue;

			AxisAlignedBB dropCoords = findSpaceForEntity(entity, pos, world);
			if (dropCoords == null)
				continue;

			entity.dismountRidingEntity();
			entity.setPositionAndUpdate(dropCoords.minX, dropCoords.minY, dropCoords.minZ);
		}
	}

	private AxisAlignedBB findSpaceForEntity(Entity entity, BlockPos pos, World world) {

		final int searchX = MFRConfig.passengerRailSearchMaxHorizontal.getInt() * 2;
		final int searchY = MFRConfig.passengerRailSearchMaxVertical.getInt() * 2;

		AxisAlignedBB bb = entity.getEntityBoundingBox();

		final double halfX = (bb.maxX - bb.minX) / 2;
		final double halfZ = (bb.maxZ - bb.minZ) / 2;

		bb = bb.offset(
				pos.getX() - bb.minX + .5 - halfX,
				pos.getY() - bb.minY + 0.09375,
				pos.getZ() - bb.minZ + .5 - halfZ
		); // center on a block, raise by 3/32

		bb = bb.offset(0, -(searchY >> 1), 0);
		for (int offsetY = -searchY; offsetY <= searchY; offsetY++) {
			bb = bb.offset(-(searchX >> 1), 0, 0);
			for (int offsetX = -searchX; offsetX <= searchX; offsetX++) {
				bb = bb.offset(0, 0, -(searchX >> 1));
				for (int offsetZ = -searchX; offsetZ <= searchX; offsetZ++) {

					if (!isBadBlockToStandIn(world, bb, entity) &&
							world.getEntitiesWithinAABBExcludingEntity(entity, bb).isEmpty()) {
						int targetX = MathHelper.floor_double(bb.minX + halfX);
						int targetY = MathHelper.floor_double(bb.minY);
						int targetZ = MathHelper.floor_double(bb.minZ + halfZ);

						if (!isBadBlockToStandOn(world, new BlockPos(targetX, targetY, targetZ))) // may be on top of a slab or other thin block
							return bb.offset(halfX, 0, halfZ);

						targetY = MathHelper.floor_double(bb.minY - 0.15625);
						if (!isBadBlockToStandOn(world, new BlockPos(targetX, targetY, targetZ)))
							return bb.offset(halfX, 0, halfZ);
					}
					bb = bb.offset(0, 0, 0.5);
				}
				bb = bb.offset(0.5, 0, -(searchX >> 1) - 0.5);
			}
			bb = bb.offset(-(searchX >> 1) - 0.5, 0.5, 0);
		}

		return null;
	}

	private boolean isBadBlockToStandOn(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block.isAir(state, world, pos) ||
				block.isBurning(world, pos) ||
				BlockRailBase.isRailBlock(state) ||
				NULL_AABB == state.getCollisionBoundingBox(world, pos)) {
			return true;
		}
		return false;
	}

	private List<AxisAlignedBB> collisionList = new ArrayList<>();

	private boolean isBadBlockToStandIn(World world, AxisAlignedBB bb, Entity entity) {

		int i = MathHelper.floor_double(bb.minX);
		int j = MathHelper.floor_double(bb.maxX) + 1;
		int k = MathHelper.floor_double(bb.minY) - 1; // fences.
		int l = MathHelper.floor_double(bb.maxY) + 1;
		int i1 = MathHelper.floor_double(bb.minZ);
		int j1 = MathHelper.floor_double(bb.maxZ) + 1;

		if (bb.minX < 0.0D) {
			--i;
		}

		if (bb.minY < 0.0D) {
			--k;
		}

		if (bb.minZ < 0.0D) {
			--i1;
		}
		BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();

		for (int k1 = i; k1 < j; ++k1) {
			for (int l1 = k; l1 < l; ++l1) {
				for (int i2 = i1; i2 < j1; ++i2) {
					IBlockState state = world.getBlockState(pos.setPos(k1, l1, i2));
					Block block = state.getBlock();

					if (block == Blocks.FIRE ||
							state.getMaterial().isLiquid() ||
							block.isBurning(world, pos) ||
							BlockRailBase.isRailBlock(state)) {
						pos.release();
						return true;
					}

					state.addCollisionBoxToList(world, pos, bb, collisionList, entity);
					if (!collisionList.isEmpty()) {
						collisionList.clear();
						pos.release();
						return true;
					}
				}
			}
		}

		pos.release();
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		registerRailModel(this, "passenger_dropoff");
	}
}
