package com.benbenlaw.taps.item.custom;

import net.minecraft.world.item.Item;

public class UpgradeItem extends Item {
    private final double timeEffect;
    public UpgradeItem(Properties p_41383_, double timeEffect) {
        super(p_41383_);
        this.timeEffect = Math.min(timeEffect, 0.999);
    }

    public double getTimeEffect() {
        return timeEffect;
    }
}
