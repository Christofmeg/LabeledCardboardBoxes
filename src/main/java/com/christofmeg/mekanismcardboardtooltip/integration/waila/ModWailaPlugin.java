package com.christofmeg.mekanismcardboardtooltip.integration.waila;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;

@SuppressWarnings("unused")
@WailaPlugin(value = MekanismCardboardTooltip.MOD_ID)
public class ModWailaPlugin implements IWailaPlugin {

    @Override
    public void register(IRegistrar registration) {
        registration.registerBlockDataProvider(CardboardBoxProvider.INSTANCE, TileEntityCardboardBox.class);
        registration.registerComponentProvider(CardboardBoxProvider.INSTANCE, TooltipPosition.HEAD, BlockCardboardBox.class);
    }

}
