package com.alleluia.betterf3.mixin;

import com.alleluia.betterf3.BetterF3DebugAccess;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin implements BetterF3DebugAccess {
    @Shadow
    protected abstract List<String> getLeftText();

    @Shadow
    protected abstract List<String> getRightText();

    @Override
    public List<String> betterF3Left() {
        return getLeftText();
    }

    @Override
    public List<String> betterF3Right() {
        return getRightText();
    }
}
