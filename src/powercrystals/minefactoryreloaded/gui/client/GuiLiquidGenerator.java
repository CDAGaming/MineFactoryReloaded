package powercrystals.minefactoryreloaded.gui.client;

import java.util.ArrayList;
import java.util.List;

import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.tile.base.TileEntityLiquidGenerator;

public class GuiLiquidGenerator extends GuiFactoryInventory
{
	private static final int _barEnergyIndex = 0;
	
	public GuiLiquidGenerator(ContainerFactoryInventory container, TileEntityLiquidGenerator tileentity)
	{
		super(container, tileentity);
		ySize = 165;
		_tanksOffsetX = 142;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		drawBar(160, 75, ((TileEntityLiquidGenerator)_tileEntity).getBufferMax(), ((TileEntityLiquidGenerator)_tileEntity).getBuffer(), _barEnergyIndex);
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
		if(isPointInRegion(160, 15, 8, 60, mouseX, mouseY))
		{
			int buffer = ((TileEntityLiquidGenerator)_tileEntity).getBuffer();
			int bufferMax = ((TileEntityLiquidGenerator)_tileEntity).getBufferMax();
			List<String> lines = new ArrayList<String>();
			lines.add("Energy");
			lines.add(buffer + " / " + bufferMax + " " + "RF");
			drawTooltip(lines, mouseX, mouseY);
		}
		else
			super.drawTooltips(mouseX, mouseY);
	}
}
