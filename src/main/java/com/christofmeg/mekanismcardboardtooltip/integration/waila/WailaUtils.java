package com.christofmeg.mekanismcardboardtooltip.integration.waila;

import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class WailaUtils {

    static final ResourceLocation ITEM = new ResourceLocation("item");
    static final ResourceLocation SPACER = new ResourceLocation("spacer");
    static final ResourceLocation OFFSET_TEXT = new ResourceLocation("jade", "text");
    static final ResourceLocation BORDER = new ResourceLocation("jade", "border");
    static final ResourceLocation SUB = new ResourceLocation("jade", "sub");

    public static RenderableTextComponent item(ItemStack stack) {
        return item(stack, 1.0F, 0);
    }

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

    public static CompoundNBT itemNBT(ItemStack stack, float scale, int offsetY) {
        if (!stack.isEmpty()) {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("id", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            tag.putInt("count", stack.getCount());
            if (stack.hasTag()) {
                tag.putString("nbt", Objects.requireNonNull(stack.getTag()).toString());
            }

            tag.putFloat("scale", scale);
            tag.putInt("offsetY", offsetY);
            return tag;
        }
        return new CompoundNBT();
    }

    public static RenderableTextComponent offsetText(ITextComponent s, int x, int y) {
        return offsetText(s.getString(), x, y);
    }

    public static RenderableTextComponent offsetText(IFormattableTextComponent s) {
        return offsetText(s.getString(), 0, 0);
    }

    public static RenderableTextComponent offsetText(String s, int x, int y) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("text", s);
        tag.putInt("x", x);
        tag.putInt("y", y);
        return new RenderableTextComponent(OFFSET_TEXT, tag);
    }

    public static RenderableTextComponent spacer(int width, int height) {
        CompoundNBT spacerTag = new CompoundNBT();
        spacerTag.putInt("width", width);
        spacerTag.putInt("height", height);
        return new RenderableTextComponent(SPACER, spacerTag);
    }

    public static RenderableTextComponent sub(String text) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("text", text);
        return new RenderableTextComponent(SUB, tag);
    }

}
