package com.valery.libgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class MenuPrincipal implements Screen {

    Stage stage;
    Juego juego;

    public MenuPrincipal(Juego juego) {
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

        //Image playButton = new Image(new Texture(Gdx.files.internal("Screen/Classic/play_button.png")));
        VisTextButton playButton = new VisTextButton("JUGAR");

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MyGame(juego));
                VisUI.dispose();
            }
        });

        //Image configButton = new Image(new Texture(Gdx.files.internal("Screen/Classic/options_button.png")));
        VisTextButton configButton = new VisTextButton("OPCIONES");

        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new ConfiguracionScreen(juego));
                VisUI.dispose();
            }
        });

        //Image quitButton = new Image(new Texture(Gdx.files.internal("Screen/Classic/exit_button.png")));

        VisTextButton quitButton = new VisTextButton("SALIR");

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VisUI.dispose();
                System.exit(0);
            }
        });

        // Añade filas a la tabla y añade los componentes
        table.row();
        table.add(playButton);
        table.row();
        table.add(configButton);
        table.row();
        table.add(quitButton);

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
