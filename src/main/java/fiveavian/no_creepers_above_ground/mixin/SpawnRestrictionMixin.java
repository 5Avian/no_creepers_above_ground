package fiveavian.no_creepers_above_ground.mixin;

import fiveavian.no_creepers_above_ground.NoCreepersAboveGround;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {
    @Inject(method = "canSpawn", at = @At(value = "RETURN"), cancellable = true)
    private static <T extends Entity> void canSpawn(EntityType<T> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            Identifier typeId = Registries.ENTITY_TYPE.getId(type);
            boolean preventSpawning = NoCreepersAboveGround.entities.contains(typeId);
            if (preventSpawning && world.isSkyVisible(pos))
                cir.setReturnValue(false);
        }
    }
}
