package com.christofmeg.mekanismcardboardtooltip.common.data;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import com.christofmeg.mekanismcardboardtooltip.client.data.ModLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MekanismCardboardTooltip.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {}
    private static final String[] LOCALE_CODES = new String[] {"en_us",};

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        for(String locale : LOCALE_CODES) {
            gen.addProvider(new ModLanguageProvider(gen, locale));
        }
    }

}