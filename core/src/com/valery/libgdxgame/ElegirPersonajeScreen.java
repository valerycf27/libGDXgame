package com.valery.libgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class ElegirPersonajeScreen implements Screen {

    Stage stage;
    Juego juego;

    Array<TextureRegion> textureRegionArrayProtaDerecha = new Array<>();
    Array<TextureRegion> textureRegionArrayProtaIzquierda = new Array<>();

    public ElegirPersonajeScreen(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);


        Image protaVerde = new Image(new Texture(Gdx.files.internal("Animaciones/protaVerde1.png")));

        protaVerde.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde1.png"))));
                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde2.png"))));
                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde3.png"))));

                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde1R.png"))));
                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde2R.png"))));
                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde3R.png"))));

                int vida = 2;
                int velocidad = 5;

                juego.setScreen(new MyGame(juego, textureRegionArrayProtaDerecha, textureRegionArrayProtaIzquierda, vida, velocidad));
                VisUI.dispose();
            }
        });

        Image minotauro = new Image(new Texture(Gdx.files.internal("Animaciones/Minotauro/Minotaur1D.png")));

        minotauro.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Minotauro/Minotaur1D.png"))));
                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Minotauro/Minotaur2D.png"))));
                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Minotauro/Minotaur3D.png"))));

                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Minotauro/Minotaur1I.png"))));
                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Minotauro/Minotaur2I.png"))));
                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Minotauro/Minotaur3I.png"))));

                int vida = 6;
                int velocidad = 6;

                juego.setScreen(new MyGame(juego, textureRegionArrayProtaDerecha, textureRegionArrayProtaIzquierda, vida, velocidad));
                VisUI.dispose();
            }
        });

        Image vikingo = new Image(new Texture(Gdx.files.internal("Animaciones/Vikingo/vick1D.png")));

        vikingo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Vikingo/vick1D.png"))));
                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Vikingo/vick2D.png"))));
                textureRegionArrayProtaDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Vikingo/vick3D.png"))));

                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Vikingo/vick1I.png"))));
                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Vikingo/vick2I.png"))));
                textureRegionArrayProtaIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Vikingo/vick3I.png"))));

                int vida = 4;
                int velocidad = 5;

                juego.setScreen(new MyGame(juego, textureRegionArrayProtaDerecha, textureRegionArrayProtaIzquierda, vida, velocidad));
            }
        });

        // Añade filas a la tabla y añade los componentes
        table.row();
        table.add(protaVerde).width(130).height(130);
        table.row();
        table.add(minotauro).width(150).height(160);
        table.row();
        table.add(vikingo).width(120).height(120);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Pinta la UI en la pantalla
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
