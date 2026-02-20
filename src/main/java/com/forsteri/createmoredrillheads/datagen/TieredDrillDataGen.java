package com.forsteri.createmoredrillheads.datagen;

import com.forsteri.createmoredrillheads.CreateMoreDrillHeads;
import net.minecraft.data.DataGenerator;
import net.neoforged.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateMoreDrillHeads.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TieredDrillDataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();

        generator.addProvider(true, new DrillTipApplicationRecipeProvider(event.getGenerator().getPackOutput()));
    }
}
