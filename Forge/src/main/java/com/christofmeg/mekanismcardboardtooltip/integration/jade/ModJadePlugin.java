package com.christofmeg.mekanismcardboardtooltip.integration.jade;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;

@SuppressWarnings("unused")
@WailaPlugin(MekanismCardboardTooltip.MOD_ID)
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
