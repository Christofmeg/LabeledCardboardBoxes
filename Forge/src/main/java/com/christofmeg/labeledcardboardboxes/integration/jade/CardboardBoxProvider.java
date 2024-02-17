package com.christofmeg.labeledcardboardboxes.integration.jade;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import mcp.mobius.waila.api.ui.IElement;
import mcp.mobius.waila.api.ui.IElementHelper;
import mekanism.common.block.BlockCardboardBox;
import mekanism.common.tile.TileEntityCardboardBox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.registries.ForgeRegistries;

public class CardboardBoxProvider implements IComponentProvider, IServerDataProvider<BlockEntity> {

    public static final CardboardBoxProvider INSTANCE = new CardboardBoxProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        IElementHelper elements = tooltip.getElementHelper();

        if (accessor.getServerData().contains("block")) {
            ResourceLocation location = ResourceLocation.tryParse(accessor.getServerData().getString("block"));
            if (location != null) {

                ResourceLocation iconLocation = null;
                if (accessor.getServerData().contains("itemIcon")) {
                    iconLocation = ResourceLocation.tryParse(accessor.getServerData().getString("itemIcon"));
                } else if (accessor.getServerData().contains("blockEntityIcon")) {
                    iconLocation = ResourceLocation.tryParse(accessor.getServerData().getString("blockEntityIcon"));
                }

                Item item = ForgeRegistries.ITEMS.getValue(iconLocation);
                if (item != null) {
                    Tag block = accessor.getServerData().get("block");
                    if (block != null) {
                        ItemStack stack = new ItemStack(item);
                        IElement itemIcon = elements.item(stack, 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
                        tooltip.add(itemIcon);
                    }
                }

                tooltip.append(new TranslatableComponent("cardboard_box.mekanism.block", new TranslatableComponent(location.toString().replace(location.getNamespace() + ":", ""))));
                if (accessor.getServerData().contains("spawnerType")) {
                    tooltip.append(new TextComponent(" ("));
                    tooltip.append(new TranslatableComponent(accessor.getServerData().getString("spawnerType").replace(":", ".")));
                    tooltip.append(new TextComponent(")"));
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
                    IElement itemIcon = elements.item(stack, 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
                    tooltip.add(itemIcon);
                }
                tooltip.append(new TranslatableComponent("cardboard_box.mekanism.block_entity", location.toString()));
            }
        }

    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayer serverPlayer, Level level, BlockEntity accessor, boolean b) {

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
                Tag tag = blockData.tileTag.getCompound("SpawnData").getCompound("entity").get("id");
                if (tag != null) {
                    EntityType<?> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(tag.toString().replace("\"", "")));
                    if (type != null) {

                        Entity entity = type.create(level);
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
