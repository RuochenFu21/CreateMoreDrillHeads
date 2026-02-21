package com.forsteri.createmoredrillheads.datagen;

import com.forsteri.createmoredrillheads.CreateMoreDrillHeads;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = CreateMoreDrillHeads.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TieredDrillDataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();

        generator.addProvider(true, new DrillTipApplicationRecipeProvider(generator.getPackOutput(), event.getLookupProvider()));
    }
}
