package com.valery.libgdxgame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bala extends Personaje{

    public Bala(int velocidad,Vector2 posicion, Array<TextureRegion> textureRegionArray){
        super(velocidad, posicion, textureRegionArray);
    }
}
