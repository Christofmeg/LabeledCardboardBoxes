package com.christofmeg.mekanismcardboardtooltip.client;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.item.block.ItemBlockCardboardBox;
import mekanism.common.util.text.BooleanStateDisplay;
import net.minecraft.block.Block;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MekanismCardboardTooltip.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (ModList.get().isLoaded("mekanism")) {
            Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse("mekanism:cardboard_box"));
            ItemStack stack = event.getItemStack();
            if (stack.getItem() == item) {
                Entity entity = event.getEntity();
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    if (item instanceof ItemBlockCardboardBox) {
                        World level = player.level;
                        ItemBlockCardboardBox cardboardBox = (ItemBlockCardboardBox) item;
                        BlockCardboardBox.BlockData data = cardboardBox.getBlockData(stack);
                        event.getToolTip().remove(MekanismLang.BLOCK_DATA.translateColored(EnumColor.INDIGO, BooleanStateDisplay.YesNo.of(data != null)));
                        if (stack.hasTag()) {
                            if (data != null) {
                                event.getToolTip().add(MekanismLang.BLOCK_DATA.translateColored(EnumColor.INDIGO, BooleanStateDisplay.YesNo.of(true, true)));

                                Block block = data.blockState.getBlock();
                                event.getToolTip().remove(MekanismLang.BLOCK.translate(block));
                                event.getToolTip().add(
                                        TextComponentUtil.build(EnumColor.INDIGO, MekanismLang.BLOCK.translate(
                                                new TranslationTextComponent(block.getDescriptionId()).withStyle(TextFormatting.GRAY)))
                                );

                                if (block instanceof SpawnerBlock) {
                                    CompoundNBT tileTag = data.tileTag;
                                    if (tileTag != null) {

                                        event.getToolTip().remove(MekanismLang.TILE.translate(tileTag.getString("id")));
                                        event.getToolTip().add(
                                                MekanismLang.TILE.translateColored(EnumColor.INDIGO,
                                                        new TranslationTextComponent(tileTag.getString("id")).withStyle(TextFormatting.GRAY))
                                        );

                                        INBT tag = data.tileTag.getCompound("SpawnData").get("id");
                                        if (tag != null) {
                                            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(tag.toString().replace("\"", "")));
                                            if (type != null) {
                                                ResourceLocation entityLocation = ForgeRegistries.ENTITIES.getKey(type);
                                                if (entityLocation != null) {
                                                    event.getToolTip().add(
                                                            TextComponentUtil.build(EnumColor.INDIGO,
                                                                    new TranslationTextComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                                                            new TranslationTextComponent(capitaliseAllWords(entityLocation.getPath().replace("_", " "))).withStyle(TextFormatting.GRAY)
                                                                    )
                                                            )
                                                    );
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        } else {
                            event.getToolTip().add(MekanismLang.BLOCK_DATA.translateColored(EnumColor.INDIGO, TextComponentUtil.build(EnumColor.RED, MekanismLang.NO)));
                        }
                    }
                }
            }
        }
    }

    public static String capitaliseAllWords( String str )
    {
        if ( str == null )
        {
            return null;
        }
        int sz = str.length();
        StringBuilder buffer = new StringBuilder( sz );
        boolean space = true;
        for ( int i = 0; i < sz; i++ )
        {
            char ch = str.charAt( i );
            if ( Character.isWhitespace( ch ) )
            {
                buffer.append( ch );
                space = true;
            }
            else if ( space )
            {
                buffer.append( Character.toTitleCase( ch ) );
                space = false;
            }
            else
            {
                buffer.append( ch );
            }
        }
        return buffer.toString();
    }

}
