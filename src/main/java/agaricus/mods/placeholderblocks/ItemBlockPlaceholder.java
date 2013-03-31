package agaricus.mods.placeholderblocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockPlaceholder extends ItemBlock {

    private String[] names = { "limestone", "granite" };

    public ItemBlockPlaceholder(int id) {
        super(id);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int i = Math.min(itemStack.getItemDamage(), names.length);
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
