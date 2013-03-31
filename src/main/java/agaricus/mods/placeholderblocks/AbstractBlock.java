package agaricus.mods.placeholderblocks;

import net.minecraftforge.oredict.OreDictionary;

public class AbstractBlock {
    public int id;
    public int metadata;
    public String texture;
    public String name;

    public AbstractBlock(String idAndMetadata, String textureAndName) {
        int n = idAndMetadata.indexOf(':');
        if (n == -1) {
            id = Integer.parseInt(idAndMetadata);
            metadata = 0;
        } else {
            id = Integer.parseInt(idAndMetadata.substring(0, n));
            metadata = Integer.parseInt(idAndMetadata.substring(n + 1));
        }

        n = textureAndName.indexOf(',');
        if (n == -1) {
            texture = textureAndName;
            name = textureAndName;
        } else {
            texture = textureAndName.substring(0, n);
            name = textureAndName.substring(n + 1);
        }
    }
}
