package com.BrigBryu.HappyCabin.storyHelper;

public class AdventurePair {
    private String path;
    private String textureName;

    public AdventurePair(String path, String textureName) {
        this.path = path;
        this.textureName = textureName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }
}
