package agaricus.mods.placeholderblocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockPlaceholder extends Block {

    public enum BlockType {
        LIGHT_STONE,
        DARK_STONE,
        // TODO: limestone brick
        // TODO: granite cobblestone
        // TODO: granite brick

        RED_ORE,
        GREEN_ORE,
        BLUE_ORE,
    };

    private Icon[] textures = new Icon[5];

    public BlockPlaceholder(int id) {
        super(id, Material.rock);

        this.setUnlocalizedName("placeholderblock");
        this.setHardness(1.5F);
        this.setResistance(2.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        textures[0] = iconRegister.registerIcon("placeholderblocks:light_stone");
        textures[1] = iconRegister.registerIcon("placeholderblocks:dark_stone");
        textures[2] = iconRegister.registerIcon("placeholderblocks:red_ore");
        textures[3] = iconRegister.registerIcon("placeholderblocks:green_ore");
        textures[4] = iconRegister.registerIcon("placeholderblocks:blue_ore");
    }

    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide) {
        int blockMeta = Math.min(world.getBlockMetadata(x, y, z), textures.length - 1);

        return textures[blockMeta];
    }

    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
        metadata = Math.min(metadata, textures.length - 1);

        return textures[metadata];
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addCreativeItems(ArrayList itemList) {
        for (final BlockType blockType : BlockType.values()) {
            itemList.add(new ItemStack(this, 1, blockType.ordinal()));
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubBlocks(int id, CreativeTabs tab, List itemList) {
        for (final BlockType blockType : BlockType.values()) {
            itemList.add(new ItemStack(this, 1, blockType.ordinal()));
        }
    }
}
