package powercrystals.minefactoryreloaded.gui.client;

import cofh.lib.gui.element.ElementButtonManaged;

import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.net.MFRPacket;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityDeepStorageUnit;

public class GuiDeepStorageUnit extends GuiFactoryInventory {

	private TileEntityDeepStorageUnit _dsu;
	private ElementButtonManaged button;
	private int maxWidth;

	public GuiDeepStorageUnit(ContainerFactoryInventory container, TileEntityDeepStorageUnit dsu) {

		super(container, dsu);
		_dsu = dsu;
		ySize = 206;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {

		super.initGui();

		maxWidth = fontRendererObj.getStringWidth(String.valueOf(_dsu.getMaxStoredCount()));

		addElement(button = new ElementButtonManaged(this, 8, 16, 40, 16, "") {

			@Override
			public void onClick() {

				MFRPacket.sendChronotyperButtonToServer(_dsu);
			}
		});
	}

	@Override
	protected void updateElementInformation() {

		button.setText(_dsu.isActive() ? "Unlock" : "Lock");
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		fontRendererObj.drawString(MFRUtil.localize("info.cofh.stored") + ':', 8, 54, 4210752);
		String v = String.valueOf(_dsu.getQuantity());
		fontRendererObj.drawString(v, 8 + maxWidth - fontRendererObj.getStringWidth(v), 80, 4210752);
	}
}
