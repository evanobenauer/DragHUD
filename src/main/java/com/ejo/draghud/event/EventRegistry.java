package com.ejo.draghud.event;

import com.ejo.draghud.event.events.*;
import com.ejo.glowlib.event.EventE;

public class EventRegistry {

    public static KeyPressEvent EVENT_KEY_PRESS = new KeyPressEvent();
    public static MouseClickEvent EVENT_MOUSE_CLICK = new MouseClickEvent();

    public static EventE EVENT_RENDER_HUD = new EventE();

    public static EventE EVENT_CALCULATE_ANIMATION = new EventE();

}
