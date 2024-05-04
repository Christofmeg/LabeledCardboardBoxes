package com.christofmeg.mekanismcardboardtooltip.client;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.item.block.ItemBlockCardboardBox;
import mekanism.common.util.text.BooleanStateDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
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
                if (entity instanceof Player player) {
                    if (item instanceof ItemBlockCardboardBox cardboardBox) {
                        Level level = player.level;
                        BlockCardboardBox.BlockData data = cardboardBox.getBlockData(stack);
                        event.getToolTip().remove(MekanismLang.BLOCK_DATA.translateColored(EnumColor.INDIGO, BooleanStateDisplay.YesNo.of(data != null)));
                        if (stack.hasTag()) {
                            if (data != null) {
                                event.getToolTip().add(MekanismLang.BLOCK_DATA.translateColored(EnumColor.INDIGO, BooleanStateDisplay.YesNo.of(true, true)));

                                Block block = data.blockState.getBlock();
                                event.getToolTip().remove(MekanismLang.BLOCK.translate(block));
                                event.getToolTip().add(
                                        TextComponentUtil.build(EnumColor.INDIGO, MekanismLang.BLOCK.translate(
                                                new TranslatableComponent(block.getDescriptionId()).withStyle(ChatFormatting.GRAY)))
                                );

                                if (block instanceof SpawnerBlock) {
                                    CompoundTag tileTag = data.tileTag;
                                    if (tileTag != null) {

                                        event.getToolTip().remove(MekanismLang.BLOCK_ENTITY.translate(tileTag.getString("id")));
                                        event.getToolTip().add(
                                                MekanismLang.BLOCK_ENTITY.translateColored(EnumColor.INDIGO,
                                                        new TranslatableComponent(tileTag.getString("id")).withStyle(ChatFormatting.GRAY))
                                        );

                                        Tag tag = data.tileTag.getCompound("SpawnData").getCompound("entity").get("id");
                                        if (tag != null) {
                                            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(tag.toString().replace("\"", "")));
                                            if (type != null) {
                                                ResourceLocation entityLocation = ForgeRegistries.ENTITIES.getKey(type);
                                                if (entityLocation != null) {
                                                    event.getToolTip().add(
                                                            TextComponentUtil.build(EnumColor.INDIGO,
                                                                    new TranslatableComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                                                            new TranslatableComponent(capitaliseAllWords(entityLocation.getPath().replace("_", " "))).withStyle(ChatFormatting.GRAY)
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
