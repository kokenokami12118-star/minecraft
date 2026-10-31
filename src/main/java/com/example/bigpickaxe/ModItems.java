package com.example.bigpickaxe;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public final class ModItems {
	// 攻撃力・攻撃速度は普通のつるはし(1.0, -2.8)と同じ
	private static final float ATTACK_DAMAGE = 1.0F;
	private static final float ATTACK_SPEED = -2.8F;

	public static final Item WOODEN_BIG_PICKAXE = register("wooden_big_pickaxe", ToolMaterial.WOOD, false);
	public static final Item STONE_BIG_PICKAXE = register("stone_big_pickaxe", ToolMaterial.STONE, false);
	public static final Item COPPER_BIG_PICKAXE = register("copper_big_pickaxe", ToolMaterial.COPPER, false);
	public static final Item IRON_BIG_PICKAXE = register("iron_big_pickaxe", ToolMaterial.IRON, false);
	public static final Item DIAMOND_BIG_PICKAXE = register("diamond_big_pickaxe", ToolMaterial.DIAMOND, false);
	// ネザライト版は普通のネザライトのつるはしと同じく、溶岩・火で燃えない
	public static final Item NETHERITE_BIG_PICKAXE = register("netherite_big_pickaxe", ToolMaterial.NETHERITE, true);

	private static Item register(String name, ToolMaterial material, boolean fireResistant) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BigPickaxeMod.MOD_ID, name));
		Item.Properties properties = new Item.Properties().pickaxe(material, ATTACK_DAMAGE, ATTACK_SPEED);
		if (fireResistant) {
			properties = properties.fireResistant();
		}
		Item item = new BigPickaxeItem(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	public static void init() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
			output.accept(WOODEN_BIG_PICKAXE);
			output.accept(STONE_BIG_PICKAXE);
			output.accept(COPPER_BIG_PICKAXE);
			output.accept(IRON_BIG_PICKAXE);
			output.accept(DIAMOND_BIG_PICKAXE);
			output.accept(NETHERITE_BIG_PICKAXE);
		});
	}

	private ModItems() {
	}
}
