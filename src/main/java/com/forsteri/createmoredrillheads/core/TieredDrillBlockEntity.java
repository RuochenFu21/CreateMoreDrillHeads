package com.forsteri.createmoredrillheads.core;

import com.simibubi.create.content.kinetics.drill.DrillBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TieredDrillBlockEntity extends DrillBlockEntity {
    public final Tier tier;
    public final DrillTips tip;

    public TieredDrillBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, Tier tier, DrillTips tip) {
        super(typeIn, pos, state);
        this.tier = tier;
        this.tip = tip;
    }

    @Override
    protected float getBreakSpeed() {
        return super.getBreakSpeed() / 6f * tier.getSpeed();
    }

    public boolean canBreak(BlockState stateToBreak, float blockHardness) {
        return
                isBreakable(stateToBreak, blockHardness)
                        && !stateToBreak.is(tier.getIncorrectBlocksForDrops());
    }

    @Override
    public void onBlockBroken(BlockState stateToBreak) {
        assert level != null;
        Vec3 vec = VecHelper.offsetRandomly(VecHelper.getCenterOf(breakingPos), level.random, .125f);
        BlockHelper.destroyBlockAs(level, breakingPos, null, tip.getItemStack(),1f, (stack) -> {
            if (stack.isEmpty())
                return;
            if (!level.getGameRules()
                    .getBoolean(GameRules.RULE_DOBLOCKDROPS))
                return;
            if (level.restoringBlockSnapshots)
                return;

            ItemEntity itementity = new ItemEntity(level, vec.x, vec.y, vec.z, stack);
            itementity.setDefaultPickUpDelay();
            itementity.setDeltaMovement(Vec3.ZERO);
            level.addFreshEntity(itementity);
        });
    }
}
