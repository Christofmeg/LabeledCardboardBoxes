package com.christofmeg.mekanismcardboardtooltip.client.data;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator output, String locale) {
        super(output, MekanismCardboardTooltip.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        String locale = this.getName().replace("Languages: ", "");
        if (locale.equals("en_us")) {
            add("jade." + MekanismCardboardTooltip.MOD_ID + ".cardboard_box.type", "Type: %1$s");
        }
    }

}