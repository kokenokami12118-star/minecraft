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

	// 耐久値(スタック数 x 64)。木と銅は指定がなかったので仮の値です
	public static final Item WOODEN_BIG_PICKAXE = register("wooden_big_pickaxe", ToolMaterial.WOOD, 128, false);
	public static final Item STONE_BIG_PICKAXE = register("stone_big_pickaxe", ToolMaterial.STONE, 320, false);
	public static final Item COPPER_BIG_PICKAXE = register("copper_big_pickaxe", ToolMaterial.COPPER, 480, false);
	public static final Item IRON_BIG_PICKAXE = register("iron_big_pickaxe", ToolMaterial.IRON, 640, false);
	public static final Item DIAMOND_BIG_PICKAXE = register("diamond_big_pickaxe", ToolMaterial.DIAMOND, 3200, false);
	// ネザライト版は普通のネザライトのつるはしと同じく、溶岩・火で燃えない
	public static final Item NETHERITE_BIG_PICKAXE = register("netherite_big_pickaxe", ToolMaterial.NETHERITE, 6400, true);

	private static Item register(String name, ToolMaterial material, int durability, boolean fireResistant) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BigPickaxeMod.MOD_ID, name));
		Item.Properties properties = new Item.Properties()
				.pickaxe(material, ATTACK_DAMAGE, ATTACK_SPEED)
				.durability(durability); // 素材の耐久値を、ここで決めた値で上書きする
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
