package agaricus.mods.placeholderblocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLimestone extends Block {

    public BlockLimestone(int id, Material material) {
        super(id, material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("placeholderblocks:limestone");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide) {
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
        return this.blockIcon;
    }
}
