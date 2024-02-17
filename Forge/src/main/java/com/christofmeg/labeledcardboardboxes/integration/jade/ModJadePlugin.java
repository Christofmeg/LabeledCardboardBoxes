package com.christofmeg.labeledcardboardboxes.integration.jade;

import com.christofmeg.labeledcardboardboxes.LabeledCardboardBoxes;
import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;

@SuppressWarnings("unused")
@WailaPlugin(LabeledCardboardBoxes.MOD_ID)
public class ModJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(CardboardBoxProvider.INSTANCE, TileEntityCardboardBox.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerComponentProvider(CardboardBoxProvider.INSTANCE, TooltipPosition.HEAD, BlockCardboardBox.class);
    }

}
