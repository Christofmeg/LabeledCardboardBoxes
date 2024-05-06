package com.christofmeg.mekanismcardboardtooltip.integration.waila;

import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class WailaUtils {

    static final ResourceLocation ITEM = new ResourceLocation("item");
    static final ResourceLocation SPACER = new ResourceLocation("spacer");

    public static RenderableTextComponent item(ItemStack stack, float scale, int offsetY) {
        if (!stack.isEmpty()) {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("id", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            tag.putInt("count", stack.getCount());
            if (stack.hasTag()) {
                tag.putString("nbt", Objects.requireNonNull(stack.getTag()).toString());
            }

            tag.putFloat("scale", scale);
            tag.putInt("offsetY", offsetY);
            return new RenderableTextComponent(ITEM, tag);
        } else {
            return spacer(18, 0);
        }
    }

    public static RenderableTextComponent spacer(int width, int height) {
        CompoundNBT spacerTag = new CompoundNBT();
        spacerTag.putInt("width", width);
        spacerTag.putInt("height", height);
        return new RenderableTextComponent(SPACER, spacerTag);
    }

}
