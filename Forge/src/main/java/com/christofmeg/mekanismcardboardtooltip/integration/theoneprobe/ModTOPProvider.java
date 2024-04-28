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
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;


public class ModTOPProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe probe) {
        probe.registerProvider(this);

        return null;
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(MekanismCardboardTooltip.MOD_ID, "data");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState state, IProbeHitData data) {
        BlockEntity blockEntity = level.getBlockEntity(data.getPos());
        if (blockEntity instanceof TileEntityCardboardBox cardboardBox) {
            BlockCardboardBox.BlockData blockData = cardboardBox.storedData;
            CompoundTag blockTag = new CompoundTag();
            CompoundTag blockEntityTag = new CompoundTag();
            CompoundTag blockEntityIconTag = new CompoundTag();
            CompoundTag itemIconTag = new CompoundTag();
            CompoundTag spawnerTypeTag = new CompoundTag();
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
                        Tag tag = blockData.tileTag.getCompound("SpawnData").getCompound("entity").get("id");
                        if (tag != null) {
                            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.tryParse(tag.toString().replace("\"", "")));
                            if (type != null) {

                                Entity entity = type.create(level);
                                if (entity != null) {
                                    ItemStack stack = entity.getPickResult();
                                    if (stack != null) {
                                        ResourceLocation itemLocation = ForgeRegistries.ITEMS.getKey(stack.getItem());
                                        if (itemLocation != null) {
                                            itemIconTag.putString("itemIcon", itemLocation.toString());
                                        }
                                    }
                                }

                                ResourceLocation entityLocation = ForgeRegistries.ENTITY_TYPES.getKey(type);
                                if (entityLocation != null) {
                                    spawnerTypeTag.putString("spawnerType", "entity." + entityLocation.toLanguageKey());
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
                        Tag block = blockTag.get("block");
                        if (block != null) {
                            ItemStack stack = new ItemStack(item);
                            info.horizontal(centerAlignment).item(stack).mcText(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, Component.translatable(location.toShortLanguageKey()).withStyle(ChatFormatting.GRAY)));
                        }
                    } else {
                        info.horizontal(centerAlignment).mcText(MekanismLang.BLOCK.translateColored(EnumColor.INDIGO, Component.translatable(location.toShortLanguageKey()).withStyle(ChatFormatting.GRAY)));
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
                            info.horizontal(centerAlignment).item(stack).mcText(MekanismLang.BLOCK_ENTITY.translateColored(EnumColor.INDIGO, Component.translatable(location.toString()).withStyle(ChatFormatting.GRAY)));
                        }
                    } else {
                        info.horizontal(centerAlignment).mcText(MekanismLang.BLOCK_ENTITY.translateColored(EnumColor.INDIGO, Component.translatable(location.toString()).withStyle(ChatFormatting.GRAY)));
                    }
                }
            }

            if (spawnerTypeTag.contains("spawnerType")) {
                if (itemIconTag.contains("itemIcon")) {
                    ResourceLocation iconLocation = ResourceLocation.tryParse(itemIconTag.getString("itemIcon"));
                    Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                    if (item != null) {
                        Tag block = blockTag.get("block");
                        if (block != null) {
                            ItemStack stack = new ItemStack(item);
                            info.horizontal(centerAlignment).item(stack).mcText(TextComponentUtil.build(EnumColor.INDIGO,
                                    Component.translatable("cardboard_box.mekanism.block_entity.spawn_type",
                                            Component.translatable(spawnerTypeTag.getString("spawnerType")).withStyle(ChatFormatting.GRAY))
                            ));
                        }
                    }
                } else {
                    info.horizontal(centerAlignment).mcText(TextComponentUtil.build(EnumColor.INDIGO,
                            Component.translatable("cardboard_box.mekanism.block_entity.spawn_type",
                                    Component.translatable(spawnerTypeTag.getString("spawnerType")).withStyle(ChatFormatting.GRAY))
                    ));
                }
            }
        }
    }

}
