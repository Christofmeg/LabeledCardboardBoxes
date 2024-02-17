package com.christofmeg.labeledcardboardboxes.common.data;

import com.christofmeg.labeledcardboardboxes.LabeledCardboardBoxes;
import com.christofmeg.labeledcardboardboxes.client.data.ModLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = LabeledCardboardBoxes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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