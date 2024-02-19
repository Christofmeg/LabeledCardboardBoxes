package com.christofmeg.labeledcardboardboxes.client;

import com.christofmeg.labeledcardboardboxes.LabeledCardboardBoxes;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.item.block.ItemBlockCardboardBox;
import mekanism.common.util.text.BooleanStateDisplay;
import net.minecraft.block.Block;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = LabeledCardboardBoxes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (ModList.get().isLoaded("mekanism")) {
            Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse("mekanism:cardboard_box"));
            ItemStack stack = event.getItemStack();
            if (stack.getItem() == item) {
                if (stack.hasTag()) {
                    PlayerEntity player = event.getPlayer();
                    if (player != null) {
                        if (item instanceof ItemBlockCardboardBox) {
                            BlockCardboardBox.BlockData data = ((ItemBlockCardboardBox) item).getBlockData(stack);
                            if (data != null) {

                                event.getToolTip().remove(MekanismLang.BLOCK_DATA.translateColored(EnumColor.INDIGO, BooleanStateDisplay.YesNo.of(((ItemBlockCardboardBox) item).getBlockData(stack) != null)));

                                Block block = data.blockState.getBlock();
                                if (block instanceof SpawnerBlock) {
                                    if (data.tileTag != null) {
                                        INBT tag = data.tileTag.getCompound("SpawnData").get("id");
                                        if (tag != null) {
                                            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(tag.toString().replace("\"", "")));
                                            if (type != null) {
                                                ResourceLocation entityLocation = ForgeRegistries.ENTITIES.getKey(type);
                                                if (entityLocation != null) {
                                                    CompoundNBT tileTag = ((ItemBlockCardboardBox) item).getBlockData(stack).tileTag;
                                                    if (tileTag != null) {
                                                        String blockString = tileTag.getString("id").replace("\"", "");
                                                        ResourceLocation location = ResourceLocation.tryParse(blockString);
                                                        if (location != null) {

                                                            event.getToolTip().remove(MekanismLang.BLOCK.translate(data.blockState.getBlock()));
                                                            event.getToolTip().add(MekanismLang.BLOCK.translate(data.blockState.getBlock())
                                                                    .append(new StringTextComponent(" ("))
                                                                    .append(new TranslationTextComponent(capitaliseAllWords(entityLocation.getPath().replace("_", " "))))
                                                                    .append(new StringTextComponent(")"))
                                                                    .withStyle(TextFormatting.WHITE)
                                                            );

                                                            event.getToolTip().remove(MekanismLang.TILE.translate(tileTag.getString("id")));
                                                            event.getToolTip().add(MekanismLang.TILE.translate(tileTag.getString("id")));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
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
