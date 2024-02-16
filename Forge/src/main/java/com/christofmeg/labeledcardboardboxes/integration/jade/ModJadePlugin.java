package com.christofmeg.labeledcardboardboxes.integration.jade;

import com.christofmeg.labeledcardboardboxes.LabeledCardboardBoxes;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@SuppressWarnings("unused")
@WailaPlugin(LabeledCardboardBoxes.MOD_ID)
public class ModJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(CardboardBoxProvider.INSTANCE, TileEntityCardboardBox.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(CardboardBoxProvider.INSTANCE, BlockCardboardBox.class);
    }

}
