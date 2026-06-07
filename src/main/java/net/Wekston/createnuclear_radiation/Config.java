package net.Wekston.createnuclear_radiation;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public class Config
{
    public static final Config.Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.IntValue radiusRadiation;
        public final ForgeConfigSpec.IntValue tickRadioactiveBlock;
        public final ForgeConfigSpec.IntValue distanceFire;

        public final ForgeConfigSpec.DoubleValue clearRadiation;
        public final ForgeConfigSpec.IntValue giveEffectBlidness;
        public final ForgeConfigSpec.IntValue giveEffectConfusion;
        public final ForgeConfigSpec.IntValue giveEffectPoison;
        public final ForgeConfigSpec.IntValue Death;


        public final ForgeConfigSpec.IntValue RadiusExplodeReactor;
        public final ForgeConfigSpec.IntValue RadiusBurnt;
        public final ForgeConfigSpec.IntValue RadiusDeathGrass;
        public final ForgeConfigSpec.IntValue RadiusDirt;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Radioactive Block");
            radiusRadiation = builder.comment("Radius of operation of the radioactive block").defineInRange("radiusRadiation", 100, 0, 1024);
            tickRadioactiveBlock = builder.comment("Updating players within the Radioactive block radius (in ticks)").defineInRange("tickRadioactiveBlock", 10, 5, 40);
            distanceFire = builder.comment("Entity ignition radius from the radioactive block").defineInRange("distanceFire", 10, 0, 1024);
            builder.pop();

            builder.push("Radiation");
            clearRadiation = builder.comment("The amount of radiation purification over time").defineInRange("clearRadiation", 0.01, 0, 1024);
            giveEffectBlidness = builder.comment("Amount of radiation to produce the 'Blidness' effect").defineInRange("giveEffectBlidness", 200, 1, 1024);
            giveEffectConfusion = builder.comment("Amount of radiation to produce the 'Confusion' effect").defineInRange("giveEffectConfusion", 300, 1, 1024);
            giveEffectPoison = builder.comment("Amount of radiation to produce the 'Poison' effect").defineInRange("giveEffectPoison", 400, 1, 1024);
            Death = builder.comment("The amount of radiation required to kill a player").defineInRange("Death", 500, 1, 1024);
            builder.pop();

            builder.push("REACTOR");
            RadiusExplodeReactor = builder.comment("Reactor explosion radius").defineInRange("RadiusExplodeReactor", 25, 1, 1024);
            RadiusBurnt = builder.comment("Radius of the burnt zone (set to 0 to remove)").defineInRange("RadiusBurnt", 2, 0, 1024);
            RadiusDeathGrass = builder.comment("Dead zone radius (set to 0 to remove). RadiusExplodeReactor * RadiusDeathGrass").defineInRange("RadiusDeathGrass", 5, 0, 1024);
            RadiusDirt = builder.comment("Radius of the area without grass (set to 0 to remove). RadiusExplodeReactor * RadiusDirt (+RadiusDeathGrass)").defineInRange("RadiusDirt", 5, 0, 1024);
            builder.pop();

        }
    }
}