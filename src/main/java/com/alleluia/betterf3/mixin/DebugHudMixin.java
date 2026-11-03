package com.alleluia.betterf3.mixin;

import com.alleluia.betterf3.BetterF3DebugAccess;
import com.alleluia.betterf3.F3Lines;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin implements BetterF3DebugAccess {
    @Shadow
    protected abstract List<String> getLeftText();

    @Shadow
    protected abstract List<String> getRightText();

    /** Hand this DebugHud to Better-F3 as soon as it's created. */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void betterF3$register(CallbackInfo ci) {
        F3Lines.register(this);
    }

    @Override
    public List<String> betterF3Left() {
        return getLeftText();
    }

    @Override
    public List<String> betterF3Right() {
        return getRightText();
    }
}
