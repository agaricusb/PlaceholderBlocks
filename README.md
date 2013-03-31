PlaceholderBlocks - temporary blocks while you wait

Allows you to easily register arbitrary block IDs + metadata, configurable in config/PlaceholderBlocks.cfg. 
Example usage:

    S:"3000:0"=placeholderblocks:light_stone,Limestone
    S:"3000:1"=placeholderblocks:dark_stone,Granite
    S:"3001:0"=placeholderblocks:red_ore,Red Jasper Ore
    S:"3001:1"=placeholderblocks:green_ore,Green Topaz Ore
    S:"3001:2"=placeholderblocks:blue_ore,Blue Opal Ore

Any number of block IDs, metadata, textures, and names are supported.

Build with:

    mvn initialize -P -built
    mvn package

