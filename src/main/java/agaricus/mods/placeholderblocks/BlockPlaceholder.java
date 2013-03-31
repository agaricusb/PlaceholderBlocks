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

import java.util.*;

public class BlockPlaceholder extends Block {

    private Icon[] textures = new Icon[0xf];
    private Map<Integer, String> textureStrings;

    public BlockPlaceholder(int id, Map<Integer, String> textureStrings) {
        super(id, Material.rock);

        this.setUnlocalizedName("placeholderblock");
        this.setHardness(1.5F);
        this.setResistance(2.0F);

        this.textureStrings = textureStrings;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        for (int i : textureStrings.keySet()) {
            textures[i & 0xf] = iconRegister.registerIcon(textureStrings.get(i));
        }
    }

    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide) {
        return textures[world.getBlockMetadata(x, y, z) & 0xf];
    }

    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
        return textures[metadata & 0xf];
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
        for (int i = 0; i < 0xf; ++i) {
            if (textures[i] != null) {
                itemList.add(new ItemStack(this, 1, i));
            }
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
        for (int i = 0; i < 0xf; ++i) {
            if (textures[i] != null) {
                itemList.add(new ItemStack(this, 1, i));
            }
        }
    }
}
