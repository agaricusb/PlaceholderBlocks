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
import net.minecraftforge.common.Configuration;

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

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());

        FMLLog.log(Level.FINE, "PlaceholderBlocks loading config");

        int startID = 3000;
        try {
            cfg.load();

            blockID = cfg.getBlock("blockID", startID).getInt(startID);

            // TODO: configurable subtype IDs and textures
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "PlaceholderBlocks had a problem loading it's configuration");
        } finally {
            cfg.save();
        }
    }

    @Mod.Init
    public void init(FMLInitializationEvent event) {
        String[] textureStrings = new String[16];
        textureStrings[0] = "placeholderblocks:light_stone";
        textureStrings[1] = "placeholderblocks:dark_stone";
        // TODO: limestone brick
        // TODO: granite cobblestone
        // TODO: granite brick
        textureStrings[2] = "placeholderblocks:red_ore";
        textureStrings[3] = "placeholderblocks:green_ore";
        textureStrings[4] = "placeholderblocks:blue_ore";

        final Block block = new BlockPlaceholder(blockID, textureStrings);
        GameRegistry.registerBlock(block, ItemBlockPlaceholder.class, "placeholderblock");

        LanguageRegistry.addName(new ItemStack(blockID, 1, 0), "Limestone");
        LanguageRegistry.addName(new ItemStack(blockID, 1, 1), "Granite");
        LanguageRegistry.addName(new ItemStack(blockID, 1, 2), "Red Jasper");
        LanguageRegistry.addName(new ItemStack(blockID, 1, 3), "Green Topaz");
        LanguageRegistry.addName(new ItemStack(blockID, 1, 4), "Blue Opal");
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {

    }
}

