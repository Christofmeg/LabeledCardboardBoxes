package com.christofmeg.mekanismcardboardtooltip.client.data;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, MekanismCardboardTooltip.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        String locale = this.getName().replace("Languages: ", "");
        if (locale.equals("en_us")) {
            add("config.jade.plugin_" + MekanismCardboardTooltip.MOD_ID + ".cardboard_box", "Cardboard Box");
            add("jade." + MekanismCardboardTooltip.MOD_ID + ".cardboard_box.type", "Type: %1$s");
            add("cardboard_box.mekanism.block_entity.spawn_type", "Spawns: %1$s");
        }
    }

}