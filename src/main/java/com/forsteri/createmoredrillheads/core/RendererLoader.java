package com.forsteri.createmoredrillheads.core;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class RendererLoader {
    public static void addRenderer
            (
                    BlockEntityBuilder<TieredDrillBlockEntity, CreateRegistrate> entity,
                    PartialModel head
            ) {
        entity.renderer(() -> context -> new TieredDrillRenderer(context, head));
    }

}
