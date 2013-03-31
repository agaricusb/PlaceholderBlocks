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
    private Map<Integer, List<AbstractBlock>> abstractBlocks = new HashMap<Integer, List<AbstractBlock>>();

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());

        FMLLog.log(Level.FINE, "PlaceholderBlocks loading config");

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

                // parse configuration entry
                AbstractBlock abstractBlock = new AbstractBlock(key, property.getString());

                // add to list keyed by block ID
                List<AbstractBlock> list;
                if (abstractBlocks.containsKey(abstractBlock.id)) {
                    list = abstractBlocks.get(abstractBlock.id);
                } else {
                    list = new ArrayList<AbstractBlock>();
                }
                list.add(abstractBlock);

                abstractBlocks.put(abstractBlock.id, list);
            }
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "PlaceholderBlocks had a problem loading it's configuration");
        } finally {
            cfg.save();
        }
    }

    @Mod.Init
    public void init(FMLInitializationEvent event) {
        // register each block
        for (int blockID : abstractBlocks.keySet()) {
            System.out.println("Registering block ID "+blockID);
            // subtype textures
            String[] textureStrings = new String[16];
            for (AbstractBlock abstractBlock : abstractBlocks.get(blockID)) {
                textureStrings[abstractBlock.metadata] = abstractBlock.texture;
                System.out.println("- "+abstractBlock.metadata+" = texture "+abstractBlock.texture);
            }

            final Block block = new BlockPlaceholder(blockID, textureStrings).setUnlocalizedName("placeholderblock." + blockID);
            GameRegistry.registerBlock(block, ItemBlockPlaceholder.class, "placeholderblock." + blockID);

            // subtype names
            for (AbstractBlock abstractBlock : abstractBlocks.get(blockID)) {
                LanguageRegistry.addName(new ItemStack(blockID, 1, abstractBlock.metadata), abstractBlock.name);
                System.out.println("- "+abstractBlock.metadata+" = name "+abstractBlock.name);
            }
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

        // stone
        m.put("215:0", "placeholderblocks:light_stone,Limestone");
        m.put("215:1", "placeholderblocks:dark_stone,Granite");
        m.put("215:2", "placeholderblocks:light_stone_lines,Limestone Brick");
        m.put("215:3", "placeholderblocks:dark_cobblestone,Granite Cobblestone");
        m.put("215:4", "placeholderblocks:dark_stone_lines,Granite Brick");

        // ore
        m.put("213:0", "placeholderblocks:red_ore,Red Jasper Ore");
        m.put("213:1", "placeholderblocks:green_ore,Green Topaz Ore");
        m.put("213:2", "placeholderblocks:blue_ore,Blue Opal Ore");
        m.put("213:3", "placeholderblocks:gray_ore,Gray Mystic Topaz Ore");
        m.put("213:4", "placeholderblocks:white_ore,White Jade Ore");
        m.put("213:5", "placeholderblocks:orange_ore,Orange Fire Opal Ore");
        m.put("213:6", "placeholderblocks:black_ore,Black Agate Ore");
        m.put("213:6", "placeholderblocks:cyan_ore,Aquamarine Zircon Ore");

        // storage
        m.put("216:0", "placeholderblocks:red_solid,Red Jasper Block");
        m.put("216:1", "placeholderblocks:green_solid,Green Topaz Block");
        m.put("216:2", "placeholderblocks:blue_solid,Blue Opal Block");

        // organics
        m.put("210:0", "melon_top,Generic Crops");
        m.put("211:0", "leaves_opaque,Generic Leaves");
        m.put("212:0", "tree_top,Generic Wood");
        m.put("214:0", "pumpkin_face,Generic Plant");

        for (Map.Entry<String, String> entry : m.entrySet()) {
            String oreName = entry.getKey();
            String modID = entry.getValue();

            category.put(oreName, new Property(oreName, modID, Property.Type.STRING));
        }
    }
}

