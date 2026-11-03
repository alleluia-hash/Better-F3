package com.alleluia.betterf3;

import java.util.List;

/**
 * Implemented on the vanilla DebugHud by {@code DebugHudMixin}. Exposes the
 * left and right F3 text lines so Better-F3 can read them.
 */
public interface BetterF3DebugAccess {
    List<String> betterF3Left();

    List<String> betterF3Right();
}
