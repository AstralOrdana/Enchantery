package com.ordana.enchantery.access;

public interface EnchantmentTableBlockEntityAccess {
    default int getCharge() {
        return 0;
    }

    default int setCharge(int newCharge) {
        return 0;
    }
}
