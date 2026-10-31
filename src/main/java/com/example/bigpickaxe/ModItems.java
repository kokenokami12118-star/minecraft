package com.example.bigpickaxe;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient; // バージョンによって必要に応じて

public final class ModItems {
	private static final float ATTACK_DAMAGE = 1.0F;
	private static final float ATTACK_SPEED = -2.8F;

	// --- 各素材のカスタムToolMaterialを定義 ---
	// 引数の構成: (採掘不適合タグ, 耐久値, 採掘速度, 攻撃力ボーナス, エンチャント耐性, 修理用アイテムタグ等)
	// ※木と銅はそのままバニラの素材を利用しています（必要なら同様にカスタム可能です）

	private static final ToolMaterial STONE_BIG_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_STONE_TOOL, 320, 4.0F, 1.0F, 5, BlockTags.STONE_TOOL_MATERIALS
	);

	private static final ToolMaterial IRON_BIG_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_IRON_TOOL, 640, 6.0F, 2.0F, 14, BlockTags.IRON_TOOL_MATERIALS
	);

	private static final ToolMaterial DIAMOND_BIG_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 3200, 8.0F, 3.0F, 10, BlockTags.DIAMOND_TOOL_MATERIALS
	);

	private static final ToolMaterial NETHERITE_BIG_MATERIAL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 6400, 9.0F, 4.0F, 15, BlockTags.NETHERITE_TOOL_MATERIALS
	);

	// 登録部分
	public static final Item WOODEN_BIG_PICKAXE = register("wooden_big_pickaxe", ToolMaterial.WOOD, false);
	public static final Item STONE_BIG_PICKAXE = register("stone_big_pickaxe", STONE_BIG_MATERIAL, false);
	public static final Item COPPER_BIG_PICKAXE = register("copper_big_pickaxe", ToolMaterial.COPPER, false);
	public static final Item IRON_BIG_PICKAXE = register("iron_big_pickaxe", IRON_BIG_MATERIAL, false);
	public static final Item DIAMOND_BIG_PICKAXE = register("diamond_big_pickaxe", DIAMOND_BIG_MATERIAL, false);
	public static final Item NETHERITE_BIG_PICKAXE = register("netherite_big_pickaxe", NETHERITE_BIG_MATERIAL, true);

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
