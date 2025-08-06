package com.forsteri.createmoredrillheads.core;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.drill.DrillBlock;
import com.simibubi.create.content.kinetics.drill.DrillBlockEntity;
import com.simibubi.create.content.kinetics.drill.DrillRenderer;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TieredDrillRenderer extends DrillRenderer {
    public final PartialModel head;
    public TieredDrillRenderer(BlockEntityRendererProvider.Context context, PartialModel head) {
        super(context);
        this.head = head;
    }

    @Override
    protected SuperByteBuffer getRotatedModel(DrillBlockEntity be, BlockState state) {
        return CachedBuffers.partialFacing(head, state);
    }

    public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                           ContraptionMatrices matrices, MultiBufferSource buffer) {
        BlockState state = context.state;

        SuperByteBuffer superBuffer = CachedBuffers.partial(DrillTierRegisterer.getHead(state), state);
        Direction facing = state.getValue(DrillBlock.FACING);

        float speed = context.contraption.stalled
                || !VecHelper.isVecPointingTowards(context.relativeMotion, facing
                .getOpposite()) ? context.getAnimationSpeed() : 0;
        float time = AnimationTickHolder.getRenderTime() / 20;
        float angle = (time * speed) % 360;

        superBuffer
                .transform(matrices.getModel())
                .center()
                .rotateY(AngleHelper.horizontalAngle(facing))
                .rotateX(AngleHelper.verticalAngle(facing))
                .rotateZ(angle)
                .uncenter()
                .light(LevelRenderer.getLightColor(renderWorld, context.localPos))
                .renderInto(matrices.getViewProjection(), buffer.getBuffer(RenderType.solid()));
    }
}
