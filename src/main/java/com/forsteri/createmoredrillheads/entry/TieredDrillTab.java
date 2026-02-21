package com.forsteri.createmoredrillheads.entry;

import com.forsteri.createmoredrillheads.CreateMoreDrillHeads;
import com.forsteri.createmoredrillheads.core.DrillTips;
import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TieredDrillTab {
    private static final DeferredRegister<CreativeModeTab> REGISTER
            = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateMoreDrillHeads.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB =
            REGISTER.register("more_drill_heads",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.more_drill_heads"))
                            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getId())
                            .icon(() -> new ItemStack(TieredDrillRegistration.DRILLS.get(Tiers.DIAMOND).DRILLS.get(DrillTips.NONE).getBlock().get()))
                            .displayItems(
                                    (parameters, output) ->
                                            output.acceptAll(
                                                    CreateMoreDrillHeads.REGISTRATE.getAll(Registries.ITEM).stream().map(
                                                            regObj -> new ItemStack(regObj.get())
                                                    ).toList()
                                            )
                            )
                            .build());

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
