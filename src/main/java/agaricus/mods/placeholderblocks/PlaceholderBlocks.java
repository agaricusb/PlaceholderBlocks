package agaricus.mods.placeholderblocks;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Mod(modid = "PlaceholderBlocks", name = "PlaceholderBlocks", version = "1.0-SNAPSHOT") // TODO: version from resource
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class PlaceholderBlocks {

    private boolean verbose = true;

    private int blockID = 0;
    private List<AbstractBlock> abstractBlocks = new ArrayList<AbstractBlock>();

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());

        FMLLog.log(Level.FINE, "PlaceholderBlocks loading config");

        int startID = 3000;
        try {
            cfg.load();

            if (cfg.getCategoryNames().size() == 0) {
                loadDefaults(cfg);
            }

            ConfigCategory category = cfg.getCategory("Blocks");

            for (Map.Entry<String, Property> entry : category.entrySet()) {
                String key = entry.getKey();
                Property property = entry.getValue();

                if (property.getString().length() == 0) {
                    // not set
                    continue;
                }

                abstractBlocks.add(new AbstractBlock(key, property.getString()));
            }
            blockID = cfg.getBlock("blockID", startID).getInt(startID); // TODO: remove
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "PlaceholderBlocks had a problem loading it's configuration");
        } finally {
            cfg.save();
        }
    }

    @Mod.Init
    public void init(FMLInitializationEvent event) {
        String[] textureStrings = new String[16];

        for (AbstractBlock abstractBlock : abstractBlocks) {
            textureStrings[abstractBlock.metadata] = abstractBlock.texture;
        }

        // TODO: support multiple block IDs
        final Block block = new BlockPlaceholder(blockID, textureStrings);
        GameRegistry.registerBlock(block, ItemBlockPlaceholder.class, "placeholderblock");

        for (AbstractBlock abstractBlock : abstractBlocks) {
            LanguageRegistry.addName(new ItemStack(blockID, 1, abstractBlock.metadata), abstractBlock.name);
        }
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {

    }

    public void loadDefaults(Configuration cfg) {
        ConfigCategory category = cfg.getCategory("Blocks");

        FMLLog.log(Level.FINE, "PlaceholderBlocks initializing defaults");

        HashMap<String, String> m = new HashMap<String, String>();

        // a demonstrative set of defaults
        m.put("3000:0", "placeholderblocks:light_stone,Limestone");
        m.put("3000:1", "placeholderblocks:dark_stone,Granite");
        // TODO: limestone brick
        // TODO: granite cobblestone
        // TODO: granite brick
        m.put("3000:2", "placeholderblocks:red_ore,Red Jasper");
        m.put("3000:3", "placeholderblocks:green_ore,Green Topaz");
        m.put("3000:4", "placeholderblocks:blue_ore,Blue Opal");

        for (Map.Entry<String, String> entry : m.entrySet()) {
            String oreName = entry.getKey();
            String modID = entry.getValue();

            category.put(oreName, new Property(oreName, modID, Property.Type.STRING));
        }
    }
}

