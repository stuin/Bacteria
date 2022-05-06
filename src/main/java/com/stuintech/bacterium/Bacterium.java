package com.stuintech.bacterium;

import com.stuintech.bacterium.block.ModBlocks;
import com.stuintech.bacterium.item.ModItems;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class Bacterium implements ModInitializer {
	public static String MODID = "bacterium";
	public static Logger LOGGER = Logger.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
	}
}
