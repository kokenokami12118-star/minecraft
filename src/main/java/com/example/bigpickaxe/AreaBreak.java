package com.example.bigpickaxe;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * 3x3x1(掘った面と平行な3x3、奥行き1)をまとめて掘る処理。
 * しゃがんでいても同じように3x3で掘る。
 */
public final class AreaBreak {
	// 周りのブロックを壊すときに、このイベントがもう一度呼ばれて無限ループしないようにする
	private static boolean breaking = false;

	public static void init() {
		PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, blockEntity) -> {
			if (breaking || level.isClientSide()) {
				return true;
			}
			if (!(player instanceof ServerPlayer serverPlayer)) {
				return true;
			}
			ItemStack tool = player.getMainHandItem();
			if (!(tool.getItem() instanceof BigPickaxeItem)) {
				return true;
			}

			Direction face = findFace(level, player, pos);
			if (face == null) {
				return true;
			}

			List<BlockPos> targets = new ArrayList<>();
			for (int a = -1; a <= 1; a++) {
				for (int b = -1; b <= 1; b++) {
					if (a == 0 && b == 0) {
						continue;
					}
					BlockPos target = switch (face.getAxis()) {
						case X -> pos.offset(0, a, b);
						case Y -> pos.offset(a, 0, b);
						case Z -> pos.offset(a, b, 0);
					};
					if (canBreak(level, tool, target)) {
						targets.add(target);
					}
				}
			}

			breaking = true;
			try {
				for (BlockPos target : targets) {
					ItemStack held = player.getMainHandItem();
					if (held.isEmpty() || !(held.getItem() instanceof BigPickaxeItem)) {
						break; // 途中でつるはしが壊れた
					}
					serverPlayer.gameMode.destroyBlock(target);
				}
			} finally {
				breaking = false;
			}
			if (!targets.isEmpty()) {
				// 掘ったあとに、アメジストのような澄んだ音を鳴らす
		　　　　level.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
			}
			return true; // 真ん中のブロックは普通どおり壊れる
		});
	}

	/** 掘っているブロックのどの面を見ているかを調べる。 */
	private static Direction findFace(Level level, Player player, BlockPos pos) {
		Vec3 eye = player.getEyePosition();
		Vec3 end = eye.add(player.getViewVector(1.0F).scale(player.blockInteractionRange() + 1.0));
		BlockHitResult hit = level.clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
		if (hit.getType() == HitResult.Type.BLOCK && hit.getBlockPos().equals(pos)) {
			return hit.getDirection();
		}
		return null;
	}

	/** 周りのブロックを一緒に壊してよいか。土・砂・葉なども含めて、壊せるものは全部壊す。 */
	private static boolean canBreak(Level level, ItemStack tool, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.isAir()) {
			return false;
		}
		if (state.getBlock() instanceof LiquidBlock) {
			return false; // 水・溶岩は壊さない
		}
		if (state.getDestroySpeed(level, pos) < 0) {
			return false; // 岩盤など壊せないもの
		}
		if (level.getBlockEntity(pos) != null) {
			return false; // スポナー・かまど・チェストなどは巻き込まない
		}
		if (state.requiresCorrectToolForDrops() && !tool.isCorrectToolForDrops(state)) {
			return false; // 鉄鉱石を木のハンマーで…のように、ドロップしなくなるものは壊さない
		}
		return true;
	}

	private AreaBreak() {
	}
}
