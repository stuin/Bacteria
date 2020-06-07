package com.stuintech.bacteria;

import com.stuintech.bacteria.block.ModBlocks;
import com.stuintech.bacteria.item.ModItems;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class BacteriaMod implements ModInitializer {
	public static String MODID = "bacteria";
	public static Logger LOGGER = Logger.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
	}
}
