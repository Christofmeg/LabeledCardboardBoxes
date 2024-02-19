package com.christofmeg.labeledcardboardboxes.integration.jade;

import com.christofmeg.labeledcardboardboxes.LabeledCardboardBoxes;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;

@SuppressWarnings("unused")
@WailaPlugin(LabeledCardboardBoxes.MOD_ID)
public class ModJadePlugin implements IWailaPlugin {

    @Override
    public void register(IRegistrar registration) {
        registration.registerBlockDataProvider(CardboardBoxProvider.INSTANCE, TileEntityCardboardBox.class);
        registration.registerComponentProvider(CardboardBoxProvider.INSTANCE, TooltipPosition.HEAD, BlockCardboardBox.class);
    }
}
