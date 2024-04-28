package com.christofmeg.mekanismcardboardtooltip;

import com.christofmeg.mekanismcardboardtooltip.integration.theoneprobe.ModTOPProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MekanismCardboardTooltip.MOD_ID)
public class MekanismCardboardTooltip {

    public static final String MOD_ID = "mekanismcardboardtooltip";

    public MekanismCardboardTooltip() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::sendIMCMessages);
    }

    public void sendIMCMessages(InterModEnqueueEvent event) {
        String TOP_MOD_ID = "theoneprobe";
        if (ModList.get().isLoaded(TOP_MOD_ID)) {
            InterModComms.sendTo(TOP_MOD_ID, "getTheOneProbe", ModTOPProvider::new);
        }
    }


}