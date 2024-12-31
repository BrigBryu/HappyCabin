package com.BrigBryu.HappyCabin.helper;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DynamicLabel extends Label {
    private final int MAX_CHARACTERS = 80;
    public DynamicLabel(String message, Skin skin) {
        super(message, skin);
        if(message.length() > MAX_CHARACTERS) {
            throw new IllegalStateException("Message too long: " + message);
        }
    }


    public void setMessage(String message) {
        this.setText(message);
    }
}
