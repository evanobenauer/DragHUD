package com.ejo.glowhud.event.events;

import org.util.glowlib.event.EventE;

public class KeyPressEvent extends EventE {

    private int key;
    private int scancode;
    private int action;
    private int modifiers;

    @Override
    public void post(Object... args) {
        key = (int) args[0];
        scancode = (int) args[1];
        action = (int) args[2];
        modifiers = (int) args[3];
        super.post(args);
    }

    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }

    public int getState() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }

}
