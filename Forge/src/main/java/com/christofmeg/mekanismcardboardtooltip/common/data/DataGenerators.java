package com.christofmeg.mekanismcardboardtooltip.common.data;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import com.christofmeg.mekanismcardboardtooltip.client.data.ModLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MekanismCardboardTooltip.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {}
    private static final String[] LOCALE_CODES = new String[] {"en_us",};

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        for(String locale : LOCALE_CODES) {
            gen.addProvider(true, new ModLanguageProvider(output, locale));
        }
    }

}