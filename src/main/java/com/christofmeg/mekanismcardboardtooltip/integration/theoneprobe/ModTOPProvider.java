package com.christofmeg.mekanismcardboardtooltip.integration.theoneprobe;

import com.christofmeg.mekanismcardboardtooltip.MekanismCardboardTooltip;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.ILayoutStyle;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public class ModTOPProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe probe) {
        probe.registerProvider(this);

        return null;
    }

    @Override
    public String getID() {
        return new ResourceLocation(MekanismCardboardTooltip.MOD_ID, "data").toString();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo info, PlayerEntity player, World level, BlockState state, IProbeHitData data) {
        TileEntity blockEntity = level.getBlockEntity(data.getPos());
        if (blockEntity instanceof TileEntityCardboardBox) {
            TileEntityCardboardBox cardboardBox = (TileEntityCardboardBox) blockEntity;
            BlockCardboardBox.BlockData blockData = cardboardBox.storedData;
            CompoundNBT blockTag = new CompoundNBT();
            CompoundNBT blockEntityTag = new CompoundNBT();
            CompoundNBT blockEntityIconTag = new CompoundNBT();
            CompoundNBT itemIconTag = new CompoundNBT();
            CompoundNBT spawnerTypeTag = new CompoundNBT();
            if (blockData != null) {
                blockTag.putString("block", cardboardBox.storedData.blockState.getBlock().getDescriptionId());
                if (blockData.tileTag != null) {
                    String blockString = blockData.tileTag.getString("id").replace("\"", "");
                    ResourceLocation location = ResourceLocation.tryParse(blockString);
                    if (location != null) {
                        blockEntityTag.putString("blockEntity", location.toString());
                    }
                }

                Block block = cardboardBox.storedData.blockState.getBlock();
                ItemStack blockEntityIcon = new ItemStack(block);
                ResourceLocation iconLocation = ForgeRegistries.ITEMS.getKey(blockEntityIcon.getItem());
                if (iconLocation != null) {
                    blockEntityIconTag.putString("blockEntityIcon", iconLocation.toString());
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
                                            itemIconTag.putString("itemIcon", itemLocation.toString());
                                        }
                                    }
                                }

                                ResourceLocation entityLocation = ForgeRegistries.ENTITIES.getKey(type);
                                if (entityLocation != null) {
                                    spawnerTypeTag.putString("spawnerType", "entity." + entityLocation);
                                }
                            }
                        }
                    }
                }
            }

            ILayoutStyle centerAlignment = info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER);
            if (blockTag.contains("block")) {
                ResourceLocation location = ResourceLocation.tryParse(blockTag.getString("block"));
                if (location != null) {
                    ResourceLocation iconLocation = null;
                    if (blockEntityIconTag.contains("blockEntityIcon")) {
                        iconLocation = ResourceLocation.tryParse(blockEntityIconTag.getString("blockEntityIcon"));
                    }

                    Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                    if (item != null) {
                        INBT block = blockTag.get("block");
                        if (block != null) {
                            ItemStack stack = new ItemStack(item);
                            info.horizontal(centerAlignment).item(stack).mcText(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString().replace(location.getNamespace() + ":", "")).withStyle(TextFormatting.GRAY)));
                        }
                    } else {
                        info.horizontal(centerAlignment).mcText(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString().replace(location.getNamespace() + ":", "")).withStyle(TextFormatting.GRAY)));
                    }
                }
            }

            if (blockEntityTag.contains("blockEntity")) {
                ResourceLocation location = ResourceLocation.tryParse(blockEntityTag.getString("blockEntity"));
                if (location != null) {
                    if (blockEntityIconTag.contains("blockEntityIcon")) {
                        ResourceLocation iconLocation = ResourceLocation.tryParse(blockEntityIconTag.getString("blockEntityIcon"));
                        Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                        if (item != null) {
                            ItemStack stack = new ItemStack(item);
                            info.horizontal(centerAlignment).item(stack).mcText(MekanismLang.TILE.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString()).withStyle(TextFormatting.GRAY)));
                        }
                    } else {
                        info.horizontal(centerAlignment).mcText(MekanismLang.TILE.translateColored(EnumColor.INDIGO, new TranslationTextComponent(location.toString()).withStyle(TextFormatting.GRAY)));
                    }
                }
            }

            if (spawnerTypeTag.contains("spawnerType")) {
                if (itemIconTag.contains("itemIcon")) {
                    ResourceLocation iconLocation = ResourceLocation.tryParse(itemIconTag.getString("itemIcon"));
                    Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                    if (item != null) {
                        INBT block = blockTag.get("block");
                        if (block != null) {
                            ItemStack stack = new ItemStack(item);
                            info.horizontal(centerAlignment).item(stack).mcText(TextComponentUtil.build(EnumColor.INDIGO,
                                    new TranslationTextComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                            new TranslationTextComponent(spawnerTypeTag.getString("spawnerType").replace(":", ".")).withStyle(TextFormatting.GRAY))
                            ));
                        }
                    }
                } else {
                    info.horizontal(centerAlignment).mcText(TextComponentUtil.build(EnumColor.INDIGO,
                            new TranslationTextComponent("cardboard_box.mekanism.block_entity.spawn_type",
                                    new TranslationTextComponent(spawnerTypeTag.getString("spawnerType").replace(":", ".")).withStyle(TextFormatting.GRAY))
                    ));
                }
            }
        }
    }

}
