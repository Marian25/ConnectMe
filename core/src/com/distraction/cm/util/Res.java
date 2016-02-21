package com.distraction.cm.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class Res {

    private static TextureAtlas atlas;
    private static HashMap<String, BitmapFont> fontMap;

    public static void init(){
        fontMap = new HashMap<String, BitmapFont>();
        createFont("fonts/Roboto-Black.ttf", "RobotoTitle", "LevlSct 0123456789", 32);
        createFont("fonts/RobotoCondensed-Regular.ttf", "RobotoLevelItem", "Levl 0123456789", 24);
    }

    private static void createFont(String path, String key, String chars, int size){
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.characters = chars;
        params.minFilter = Texture.TextureFilter.Linear;
        params.magFilter = Texture.TextureFilter.Linear;
        params.size = size;
        BitmapFont font = gen.generateFont(params);
        fontMap.put(key, font);
        gen.dispose();
    }

    public static void loadAtlas(String path){
        atlas = new TextureAtlas(path);
        atlas.getTextures().first().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static TextureAtlas getAtlas(){
        return atlas;
    }

    public static BitmapFont getFont(String s){
        return fontMap.get(s);
    }
}
