package com.ordana.enchantery.mixins;

import com.ordana.enchantery.access.EnchantmentTableBlockEntityAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentTableBlockEntity.class)
public class EnchantmentTableBlockEntityMixin implements EnchantmentTableBlockEntityAccess {
    
    private int charge = 0;

    private int tickCounter = 0;

    public int setTickCounter(int tick) {
        return tickCounter = tick;
    }

    private int maxCharge = 100;


    @Inject(method = "load", at = @At(value = "TAIL"))
    public void load(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("Charge")) {
            this.setCharge(tag.getInt("Charge"));
        }
    }

    @Inject(method = "saveAdditional", at = @At(value = "TAIL"))
    protected void saveAdditional(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("Charge", this.getCharge());
    }

    @Override
    public int getCharge()  {
        return charge;
    }

    @Override
    public int setCharge(int newCharge)  {
        return charge = newCharge;
    }
}
