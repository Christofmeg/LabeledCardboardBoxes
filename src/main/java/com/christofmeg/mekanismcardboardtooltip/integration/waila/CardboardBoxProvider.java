package com.christofmeg.mekanismcardboardtooltip.integration.waila;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.RenderableTextComponent;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;
import net.minecraft.block.Block;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class CardboardBoxProvider implements IComponentProvider, IServerDataProvider<TileEntity> {

    public static final CardboardBoxProvider INSTANCE = new CardboardBoxProvider();

    @Override
    public void appendHead(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig iPluginConfig) {

        if (accessor.getServerData().contains("block")) {
            ResourceLocation location = ResourceLocation.tryParse(accessor.getServerData().getString("block"));
            if (location != null) {
                ResourceLocation iconLocation = null;
                if (accessor.getServerData().contains("blockEntityIcon")) {
                    iconLocation = ResourceLocation.tryParse(accessor.getServerData().getString("blockEntityIcon"));
                }

                Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                if (item != null) {
                    INBT block = accessor.getServerData().get("block");
                    if (block != null) {
                        ItemStack stack = new ItemStack(item);
                        RenderableTextComponent itemIcon = WailaUtils.item(stack, 0.5F, 0);

                        if (ModList.get().isLoaded("jade")) {
                            RenderableTextComponent textComponent =  WailaUtils.offsetText(new TranslationTextComponent(location.toString().replace(location.getNamespace() + ":", "")).withStyle(TextFormatting.GRAY));
                            RenderableTextComponent blockComponent = new RenderableTextComponent(itemIcon, textComponent);
                            tooltip.add(blockComponent);
                        }  else if (ModList.get().isLoaded("wthit") || ModList.get().isLoaded("waila")) {
                            tooltip.add(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString().replace(location.getNamespace() + ":", "")).withStyle(TextFormatting.GRAY)));
                        }
                    }
                } else {
                    tooltip.add(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString().replace(location.getNamespace() + ":", "")).withStyle(TextFormatting.GRAY)));
                }
            }
        }

        if (accessor.getServerData().contains("blockEntity") && accessor.getServerData().contains("blockEntityIcon")) {
            ResourceLocation iconLocation = ResourceLocation.tryParse(accessor.getServerData().getString("blockEntityIcon"));
            ResourceLocation location = ResourceLocation.tryParse(accessor.getServerData().getString("blockEntity"));
            if (location != null) {
                Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                if (item != null) {
                    ItemStack stack = new ItemStack(item);
                    RenderableTextComponent itemIcon = WailaUtils.item(stack, 0.5F, 0);

                    if (ModList.get().isLoaded("jade")) {
                        RenderableTextComponent textComponent = WailaUtils.offsetText(MekanismLang.TILE.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString()).withStyle(TextFormatting.GRAY)));
                        RenderableTextComponent blockComponent = new RenderableTextComponent(itemIcon, textComponent);
                        tooltip.add(blockComponent);
                    } else if (ModList.get().isLoaded("wthit") || ModList.get().isLoaded("waila")) {
                        tooltip.add(MekanismLang.TILE.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString()).withStyle(TextFormatting.GRAY)));
                    }
                } else {
                    tooltip.add(MekanismLang.TILE.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString()).withStyle(TextFormatting.GRAY)));
                }
            }
        }

        ResourceLocation iconLocation = null;
        if (accessor.getServerData().contains("itemIcon")) {
            iconLocation = ResourceLocation.tryParse(accessor.getServerData().getString("itemIcon"));
        }

        Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
        if (accessor.getServerData().contains("spawnerType")) {
            if (item != null) {
                INBT block = accessor.getServerData().get("block");
                if (block != null) {
                    ItemStack stack = new ItemStack(item);
                    RenderableTextComponent itemIcon = WailaUtils.item(stack, 0.5F, 0);

                    if (ModList.get().isLoaded("jade")) {
                        RenderableTextComponent textComponent =  WailaUtils.offsetText(TextComponentUtil.build(EnumColor.INDIGO,
                                new TranslationTextComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                        new TranslationTextComponent(accessor.getServerData().getString("spawnerType").replace(":", ".")).withStyle(TextFormatting.GRAY))
                        ));
                        RenderableTextComponent blockComponent = new RenderableTextComponent(itemIcon, textComponent);
                        tooltip.add(blockComponent);
                    } else if (ModList.get().isLoaded("wthit") || ModList.get().isLoaded("waila")) {
                        tooltip.add(
                                TextComponentUtil.build(EnumColor.INDIGO,
                                        new TranslationTextComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                                new TranslationTextComponent(accessor.getServerData().getString("spawnerType").replace(":", ".")).withStyle(TextFormatting.GRAY))
                                )
                        );
                    }

                }
            } else {
                tooltip.add(
                        TextComponentUtil.build(EnumColor.INDIGO,
                                new TranslationTextComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                        new TranslationTextComponent(accessor.getServerData().getString("spawnerType").replace(":", ".")).withStyle(TextFormatting.GRAY))
                        )
                );

            }
        }
    }

    @Override
    public void appendServerData(CompoundNBT data, ServerPlayerEntity serverPlayer, World level, TileEntity accessor) {

        TileEntityCardboardBox cardboardBox = (TileEntityCardboardBox) accessor;
        BlockCardboardBox.BlockData blockData = cardboardBox.storedData;
        if (blockData != null) {

            data.putString("block", cardboardBox.storedData.blockState.getBlock().getDescriptionId());
            if (blockData.tileTag != null) {
                String blockString = blockData.tileTag.getString("id").replace("\"", "");
                ResourceLocation location = ResourceLocation.tryParse(blockString);
                if (location != null) {
                    data.putString("blockEntity", location.toString());
                }
            }

            Block block = cardboardBox.storedData.blockState.getBlock();
            ItemStack blockEntityIcon = new ItemStack(block);
            ResourceLocation iconLocation = ForgeRegistries.ITEMS.getKey(blockEntityIcon.getItem());
            if (iconLocation != null) {
                data.putString("blockEntityIcon", iconLocation.toString());
            }

            if (block instanceof SpawnerBlock) {
                if (blockData.tileTag != null) {
                    INBT tag = blockData.tileTag.getCompound("SpawnData").get("id");
                    if (tag != null) {
                        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(tag.toString().replace("\"", "")));
                        if (type != null) {

                            Entity entity = type.create(level);
                            if (entity != null) {
                                Item item = ForgeSpawnEggItem.fromEntityType(entity.getType());
                                if (item != null) {

                                    ResourceLocation itemLocation = ForgeRegistries.ITEMS.getKey(item);
                                    if (itemLocation != null) {
                                        data.putString("itemIcon", itemLocation.toString());
                                    }
                                }
                            }

                            ResourceLocation entityLocation = ForgeRegistries.ENTITIES.getKey(type);
                            if (entityLocation != null) {
                                data.putString("spawnerType", "entity." + entityLocation);
                            }
                        }
                    }
                }
            }
        }

    }

}