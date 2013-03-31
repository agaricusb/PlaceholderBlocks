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
import net.minecraftforge.common.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Mod(modid = "PlaceholderBlocks", name = "PlaceholderBlocks", version = "1.0-SNAPSHOT") // TODO: version from resource
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class PlaceholderBlocks {

    private boolean verbose = true;

    private String blockNames[] = { "Limestone", "Granite" };
    private Map<String, Integer> blockIDs = new HashMap<String, Integer>();

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());

        FMLLog.log(Level.FINE, "PlaceholderBlocks loading config");

        int startID = 3000;
        try {
            cfg.load();

            for (int i = 0; i < blockNames.length; ++i) {
                blockIDs.put(blockNames[i], cfg.getBlock(blockNames[i].toLowerCase(), startID + i).getInt(startID + i));
            }

            // TODO: configurable subtype IDs and textures
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "PlaceholderBlocks had a problem loading it's configuration");
        } finally {
            cfg.save();
        }
    }

    @Mod.Init
    public void init(FMLInitializationEvent event) {
        for (int i = 0; i < blockNames.length; ++i) {
            String localizedName = blockNames[i];
            int id = blockIDs.get(localizedName);
            String unlocalizedName = localizedName.toLowerCase();

            final Block block = new BlockPlaceholder(id).setUnlocalizedName(unlocalizedName);
            GameRegistry.registerBlock(block, ItemBlockPlaceholder.class, unlocalizedName);
            LanguageRegistry.instance().addStringLocalization("tile.placeholderblocks."+unlocalizedName+".name", "en_US", localizedName);
            LanguageRegistry.instance().addStringLocalization("item.placeholderblocks."+unlocalizedName+".name", "en_US", localizedName);

            // TODO: limestone, granite, limestone brick, granite cobblestone, granite brick
        }
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        FMLLog.log(Level.FINE, "Loading PlaceholderBlocks...");
    }
}

