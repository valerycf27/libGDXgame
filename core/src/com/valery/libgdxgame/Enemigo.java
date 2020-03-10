package com.valery.libgdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemigo extends Personaje{
    public Enemigo(int vida, int velocidad, Vector2 posicion, Array<TextureRegion> textureRegionArray) {
        super(vida, velocidad, posicion, textureRegionArray);
    }
}
