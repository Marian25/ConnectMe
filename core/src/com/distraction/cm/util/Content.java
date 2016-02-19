package com.distraction.cm.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class Content {

    private static Content res;
    public static Content getIntance(){
        if(res == null)
            res =  new Content();
        return res;
    }

    private TextureAtlas atlas;
    private Content() {}

    public void loadAtlas(String path){
        atlas = new TextureAtlas(path);
        atlas.getTextures().first().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

}
