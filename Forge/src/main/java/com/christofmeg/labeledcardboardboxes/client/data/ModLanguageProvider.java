package com.christofmeg.labeledcardboardboxes.client.data;

import com.christofmeg.labeledcardboardboxes.LabeledCardboardBoxes;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, LabeledCardboardBoxes.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        String locale = this.getName().replace("Languages: ", "");
        if (locale.equals("en_us")) {
            add("config.jade.plugin_" + LabeledCardboardBoxes.MOD_ID + ".cardboard_box", "Cardboard Box");
            add("jade." + LabeledCardboardBoxes.MOD_ID + ".cardboard_box.type", "Type: %1$s");
        }
    }

}