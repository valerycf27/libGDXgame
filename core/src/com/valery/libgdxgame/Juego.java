package com.valery.libgdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {
    BitmapFont font;
    SpriteBatch batch;

    @Override
    public void create() {
        batch= new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fuentes/fuente1.fnt"), false);

        ConfigurationManager.prefs.putBoolean("sonido", true);

        setScreen(new MenuPrincipal(this));
    }
}
