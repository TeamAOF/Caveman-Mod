package alloffabric.caveman.mixin;

import alloffabric.caveman.Caveman;
import alloffabric.caveman.api.MobSpawnerLogicHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin implements MobSpawnerLogicHelper {
    private static final Identifier TIMED = new Identifier(Caveman.MODID, "timed");
    private static final Identifier SPAWNED_ENTITIES_ID = new Identifier(Caveman.MODID, "spawned_entities");
    private boolean behaviorEnabled;
    private int entitiesLimit;
    private Identifier lootTable;
    private int spawnedEntities;

    // WHEN OUT OF THE CONSTRUCTOR THIS WORKS
    private MobSpawnerLogicMixin() {
        this.behaviorEnabled = Caveman.config.timedSpawners.defaultBehavior;
        this.entitiesLimit = Caveman.config.timedSpawners.defaultEntitiesLimit;
        this.lootTable = new Identifier(Caveman.config.timedSpawners.defaultLootTable);
        this.spawnedEntities = 0;
    }

    @Inject(method = "spawnEntity", at = @At("RETURN"))
    private void spawnEntity(Entity entity, CallbackInfo info) {
        MobSpawnerLogic logic = (MobSpawnerLogic)(Object) this;
        World world = logic.getWorld();
        BlockPos pos = logic.getPos();
        this.incrementSpawnedEntities();
        if (this.hadEntityLimitPassed()) {
            world.setBlockState(pos, Blocks.CHEST.getDefaultState());
            BlockEntity chest = world.getBlockEntity(pos);
            if (chest instanceof ChestBlockEntity) {
                ((ChestBlockEntity) chest).setLootTable(this.lootTable, new Random().nextLong());
                world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
            }
        }
    }

    @Inject(method = "deserialize", at = @At("RETURN"))
    private void deserialize(CompoundTag tag, CallbackInfo info) {
        this.setSpawnedEntities(tag.getInt(SPAWNED_ENTITIES_ID.toString()));
    }

    @Inject(method = "serialize", at = @At("RETURN"))
    private void serialize(CompoundTag tag, CallbackInfoReturnable<CompoundTag> info) {
        tag.putInt(SPAWNED_ENTITIES_ID.toString(), this.getSpawnedEntities());
    }

    @Override
    public void incrementSpawnedEntities() {
        this.spawnedEntities += 1;
    }

    @Override
    public void setSpawnedEntities(int number) {
        this.spawnedEntities = number;
    }

    @Override
    public int getSpawnedEntities() {
        return this.spawnedEntities;
    }

    @Override
    public boolean hadEntityLimitPassed() {
        return this.entitiesLimit < spawnedEntities;
    }
}
