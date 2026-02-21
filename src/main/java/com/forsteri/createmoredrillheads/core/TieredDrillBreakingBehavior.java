package com.forsteri.createmoredrillheads.core;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.drill.DrillMovementBehaviour;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class TieredDrillBreakingBehavior extends DrillMovementBehaviour {
    public final Tier tier;
    public final DrillTips tip;

    public TieredDrillBreakingBehavior(Tier tier, DrillTips tip) {
        super();
        this.tip = tip;
        this.tier = tier;
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
        ContraptionMatrices matrices, MultiBufferSource buffer) {
        if (!VisualizationManager.supportsVisualization(context.world))
            TieredDrillRenderer.renderInContraption(context, renderWorld, matrices, buffer);
    }

    @Nullable
    @Override
    public ActorVisual createVisual(VisualizationContext visualizationContext, VirtualRenderWorld simulationWorld, MovementContext context) {
        return new TieredDrillActorVisual(visualizationContext, simulationWorld, context);
    }

    @Override
    protected void destroyBlock(MovementContext context, BlockPos breakingPos) {
        BlockHelper.destroyBlockAs(context.world, breakingPos, null, tip.getItemStack(), 1f, stack -> this.dropItem(context, stack));
    }

    @Override
    public boolean canBreak(Level world, BlockPos breakingPos, BlockState state) {
        return super.canBreak(world, breakingPos, state)
                && !state.is(tier.getIncorrectBlocksForDrops());
    }

    @Override
    protected float getBlockBreakingSpeed(MovementContext context) {
        return super.getBlockBreakingSpeed(context) / 6f * tier.getSpeed();
    }
}
