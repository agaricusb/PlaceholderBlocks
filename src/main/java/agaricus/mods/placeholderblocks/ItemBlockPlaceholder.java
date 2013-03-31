package agaricus.mods.placeholderblocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockPlaceholder extends ItemBlock {

    private String[] names = { "light_stone", "dark_stone", "red_ore", "green_ore", "blue_ore" }; // TODO: DRY

    public ItemBlockPlaceholder(int id) {
        super(id);
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int i = Math.min(itemStack.getItemDamage(), names.length - 1);
        return "tile.placeholderblocks." + names[i];
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubItems(int itemID, CreativeTabs creativeTabs, List subTypes) {
        for (int i = 0; i < names.length; ++i) {
            subTypes.add(new ItemStack(itemID, 1, i));
        }
    }
}
