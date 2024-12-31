package com.BrigBryu.HappyCabin.helper;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DynamicTextButton extends TextButton {

    public DynamicTextButton(String text, Skin skin) {
        super(text, skin);
    }

    public void setButtonText(String newText) {
        this.setText(newText);
    }
}
