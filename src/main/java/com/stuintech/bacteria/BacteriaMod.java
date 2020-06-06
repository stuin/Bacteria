package com.stuintech.bacteria;

import com.stuintech.bacteria.block.ModBlocks;
import com.stuintech.bacteria.item.ModItems;
import net.fabricmc.api.ModInitializer;

public class BacteriaMod implements ModInitializer {
	public static String MODID = "bacteria";

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
	}
}
