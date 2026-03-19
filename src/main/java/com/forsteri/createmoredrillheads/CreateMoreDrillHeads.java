package com.forsteri.createmoredrillheads;

import com.forsteri.createmoredrillheads.entry.TieredDrillLang;
import com.forsteri.createmoredrillheads.entry.TieredDrillRegistration;
import com.forsteri.createmoredrillheads.entry.TieredDrillTab;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CreateMoreDrillHeads.MOD_ID)
public class CreateMoreDrillHeads {

    public static final String MOD_ID = "createmoredrillheads";

    public CreateMoreDrillHeads(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);
        REGISTRATE.defaultCreativeTab("createmoredrillheads");

        TieredDrillTab.register(modEventBus);
        TieredDrillRegistration.register();

        // 4. Lang etc
        TieredDrillLang.register();
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateMoreDrillHeads.MOD_ID);
}
