package com.example.bigpickaxe;

import net.fabricmc.api.ModInitializer;

public class BigPickaxeMod implements ModInitializer {
	public static final String MOD_ID = "bigpickaxe";

	@Override
	public void onInitialize() {
		ModItems.init();
		AreaBreak.init();
	}
}
