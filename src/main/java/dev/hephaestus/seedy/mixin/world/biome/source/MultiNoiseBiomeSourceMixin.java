package dev.hephaestus.seedy.mixin.world.biome.source;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import dev.hephaestus.seedy.SeedSupplier;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {
	@Unique private static long LAST_SEED = SeedSupplier.MARKER;

	@Redirect(method = "method_37688(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/PrimitiveCodec;fieldOf(Ljava/lang/String;)Lcom/mojang/serialization/MapCodec;", ordinal = 0))
	private static MapCodec<Long> giveUsRandomSeeds(PrimitiveCodec<Long> codec, final String name) {
		return codec.fieldOf(name).orElseGet(SeedSupplier::getSeed);
	}

	@ModifyVariable(method = "<init>(JLnet/minecraft/class_6452$class_6455;Lnet/minecraft/world/biome/source/MultiNoiseBiomeSource$NoiseParameters;Lnet/minecraft/world/biome/source/MultiNoiseBiomeSource$NoiseParameters;Lnet/minecraft/world/biome/source/MultiNoiseBiomeSource$NoiseParameters;Lnet/minecraft/world/biome/source/MultiNoiseBiomeSource$NoiseParameters;Lnet/minecraft/world/biome/source/MultiNoiseBiomeSource$NoiseParameters;IIZLjava/util/Optional;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/source/MultiNoiseBiomeSource;field_34194:Lnet/minecraft/class_6466;", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER), ordinal = 0)
	private long replaceSeed(long seed) {
		if (seed == SeedSupplier.MARKER) {
			return LAST_SEED;
		} else {
			LAST_SEED = seed;
		}

		return seed;
	}
}
