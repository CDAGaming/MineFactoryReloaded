package powercrystals.minefactoryreloaded.block.decor;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.api.rednet.connectivity.IRedNetDecorative;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;

public class BlockFactoryGlass extends BlockGlass implements IRedNetDecorative {
/*	public static final String[] _names = { "white", "orange", "magenta", "lightblue", "yellow", "lime",
		"pink", "gray", "lightgray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
	static IIcon _texture;*/

	public BlockFactoryGlass()
	{
		super(Material.GLASS, false);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		setUnlocalizedName("mfr.stainedglass.block");
		setHardness(0.3F);
		setSoundType(SoundType.GLASS);
		setCreativeTab(MFRCreativeTab.tab);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

/*	
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}


	@Override
	public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color)
	{
		int meta = getBlockMetadata(x, y, z);
		if (meta != colour)
		{
			return world.setBlockMetadataWithNotify(x, y, z, colour, 3);
		}
		return false;
	}*/

/*	@Override
	public int getRenderColor(int meta)
	{
		return MFRUtil.COLORS[Math.min(Math.max(meta, 0), 15)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		_texture = ir.registerIcon("minefactoryreloaded:tile.mfr.stainedglass");
	}

	@Override
	public IIcon getIcon(EnumFacing side, int meta)
	{
		return new IconOverlay(_texture, 8, 8, meta > 15 ? 6 : 7, 7);
	}

	public IIcon getBlockOverlayTexture()
	{
		return new IconOverlay(_texture, 8, 8, 0, 0);
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		boolean r = super.shouldSideBeRendered(state, world, pos, side);
		
		IBlockState neighborState = world.getBlockState(pos.offset(side));
		if (!r) {
			return getMetaFromState(state) != neighborState.getBlock().getMetaFromState(neighborState);
		}
		return r;
	}

/*	public IIcon getBlockOverlayTexture(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		BlockPosition bp;
		boolean[] sides = new boolean[8];
		if (side <= 1)
		{
			bp = new BlockPosition(x, y, z, EnumFacing.NORTH);
			bp.moveRight(1);
			sides[0] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveBackwards(1);
			sides[4] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveLeft(1);
			sides[1] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveLeft(1);
			sides[5] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveForwards(1);
			sides[3] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveForwards(1);
			sides[6] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveRight(1);
			sides[2] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveRight(1);
			sides[7] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
		}
		else
		{
			bp = new BlockPosition(x, y, z, EnumFacing.VALID_DIRECTIONS[side]);
			bp.moveRight(1);
			sides[0] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveDown(1);
			sides[4] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveLeft(1);
			sides[1] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveLeft(1);
			sides[5] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveUp(1);
			sides[3] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveUp(1);
			sides[6] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveRight(1);
			sides[2] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
			bp.moveRight(1);
			sides[7] = world.getBlock(bp.x,bp.y,bp.z).equals(this);
		}
		return new IconOverlay(_texture, 8, 8, sides);
	}

	@Override
	public int getRenderType()
	{
		return MineFactoryReloadedCore.renderIdFactoryGlass;
	}*/
}
