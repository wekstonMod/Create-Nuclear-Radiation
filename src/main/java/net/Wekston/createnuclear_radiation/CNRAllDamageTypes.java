package net.Wekston.createnuclear_radiation;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class CNRAllDamageTypes {

	public static final ResourceKey<DamageType> RADIATION = key("radiation");

	private static ResourceKey<DamageType> key(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(CreateNuclearRadiation.MODID, name));
	}

	public static void bootstrap(BootstapContext<DamageType> context) {
		context.register(RADIATION, new DamageType("radiation", 0.1F));
	}
}