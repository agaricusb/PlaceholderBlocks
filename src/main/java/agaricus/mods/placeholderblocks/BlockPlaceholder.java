package agaricus.mods.placeholderblocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockPlaceholder extends Block {

    public enum BlockType {
        LIMESTONE,
        GRANITE,
    };

    private Icon[] textures = {null, null};

    public BlockPlaceholder(int id) {
        super(id, Material.rock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        textures[0] = iconRegister.registerIcon("placeholderblocks:limestone");
        textures[1] = iconRegister.registerIcon("placeholderblocks:granite");
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        for (final BlockType blockType : BlockType.values()) {
            itemList.add(new ItemStack(this, 1, blockType.ordinal()));
        }
    }

    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
        if (metadata > 1) {
            metadata = 1;
        }

        return textures[metadata];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs tab, List itemList) {
        for (final BlockType blockType : BlockType.values()) {
            itemList.add(new ItemStack(this, 1, blockType.ordinal()));
        }
    }
}
