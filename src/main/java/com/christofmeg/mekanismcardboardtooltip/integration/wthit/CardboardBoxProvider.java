package com.christofmeg.mekanismcardboardtooltip.integration.wthit;
/*
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.ItemComponent;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraftforge.registries.ForgeRegistries;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.ItemComponent;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraftforge.registries.ForgeRegistries;
*/
public class CardboardBoxProvider {} /*implements IBlockComponentProvider, IServerDataProvider<TileEntityCardboardBox> {

    public static final CardboardBoxProvider INSTANCE = new CardboardBoxProvider();

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        CompoundTag serverData = accessor.getServerData();
        if (serverData.contains("block")) {
            ResourceLocation location = ResourceLocation.tryParse(serverData.getString("block"));
            if (location != null) {
                ResourceLocation iconLocation = null;
                if (serverData.contains("blockEntityIcon")) {
                    iconLocation = ResourceLocation.tryParse(serverData.getString("blockEntityIcon"));
                }

                Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                if (item != null) {
                    Tag block = serverData.get("block");
                    if (block != null) {
                        ItemStack stack = new ItemStack(item);
                        ItemComponent itemComponent = new ItemComponent(stack);
                        tooltip.setLine(new ResourceLocation("mekanismcardboardtooltip", "block"))
                                .with(itemComponent)
                                .with(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, new TranslatableComponent(location.toString().replace(location.getNamespace() + ":", "")).withStyle(ChatFormatting.GRAY)));
                    }
                } else {
                    tooltip.addLine(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, new TranslatableComponent(location.toString().replace(location.getNamespace() + ":", "")).withStyle(ChatFormatting.GRAY)));
                }
            }
        }

        if (serverData.contains("blockEntity")) {
            ResourceLocation location = ResourceLocation.tryParse(serverData.getString("blockEntity"));
            if (location != null) {
                if (serverData.contains("blockEntityIcon")) {
                ResourceLocation iconLocation = ResourceLocation.tryParse(serverData.getString("blockEntityIcon"));
                    Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                    if (item != null) {
                        ItemStack stack = new ItemStack(item);
                        ItemComponent itemComponent = new ItemComponent(stack);
                        tooltip.setLine(new ResourceLocation("mekanismcardboardtooltip", "blockentity"))
                                .with(itemComponent)
                                .with(MekanismLang.BLOCK_ENTITY.translateColored(EnumColor.INDIGO, new TranslatableComponent(location.toString()).withStyle(ChatFormatting.GRAY)));
                    }
                } else {
                    tooltip.addLine(MekanismLang.BLOCK_ENTITY.translateColored(EnumColor.INDIGO, new TranslatableComponent(location.toString()).withStyle(ChatFormatting.GRAY)));
                }
            }
        }

        if (serverData.contains("spawnerType")) {
            if (serverData.contains("itemIcon")) {
                ResourceLocation iconLocation = ResourceLocation.tryParse(serverData.getString("itemIcon"));
                Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                if (item != null) {
                    Tag block = serverData.get("block");
                    if (block != null) {
                        ItemStack stack = new ItemStack(item);
                        ItemComponent itemComponent = new ItemComponent(stack);
                        tooltip.setLine(new ResourceLocation("mekanismcardboardtooltip", "spawns"))
                                .with(itemComponent)
                                .with(TextComponentUtil.build(EnumColor.INDIGO,
                                        new TranslatableComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                                new TranslatableComponent(serverData.getString("spawnerType")).withStyle(ChatFormatting.GRAY))
                                ));
                    }
                }
            } else {
                tooltip.addLine(
                        TextComponentUtil.build(EnumColor.INDIGO,
                                new TranslatableComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                        new TranslatableComponent(serverData.getString("spawnerType")).withStyle(ChatFormatting.GRAY))
                        )
                );
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag data, IServerAccessor<TileEntityCardboardBox> accessor, IPluginConfig config) {
        TileEntityCardboardBox cardboardBox = accessor.getTarget();
        if (cardboardBox != null) {
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
                        Tag tag = blockData.tileTag.getCompound("SpawnData").getCompound("entity").get("id");
                        if (tag != null) {
                            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(tag.toString().replace("\"", "")));
                            if (type != null) {

                                Entity entity = type.create(accessor.getWorld());
                                if (entity != null) {
                                    ItemStack stack = entity.getPickResult();
                                    if (stack != null) {
                                        ResourceLocation itemLocation = ForgeRegistries.ITEMS.getKey(stack.getItem());
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

}
**/
