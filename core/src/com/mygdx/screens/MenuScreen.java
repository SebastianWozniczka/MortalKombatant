package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MyGdxGame;

public class MenuScreen implements Screen {
    MyGdxGame game;
    private Texture texture;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private BitmapFont font;
    private TextButton start,option,exit;
    private TextButton.TextButtonStyle style;
    public MenuScreen(final MyGdxGame game) {
        this.game=game;
        texture=new Texture("menu/tlo.jpeg");
        style=new TextButton.TextButtonStyle();
        font=new BitmapFont(    Gdx.files.internal("foo.fnt"));
        stage=new Stage();
        table=new Table();
        atlas=new TextureAtlas("menu/start.pack");
        skin=new Skin(atlas);
        style.font=font;
        start=new TextButton("Start",style);
        option=new TextButton("Opcje",style);
        exit=new TextButton("Wyjscie",style);
        table.add(start).spaceBottom(15);
        table.row();
        table.add(option).spaceBottom(15);
        table.row();
        table.add(exit).spaceBottom(15);
        table.row();
        table.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-table.getHeight()/2);

stage.addActor(table);

        start.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GamePlay(game));
                start.remove();
                option.remove();
                exit.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        option.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
                start.remove();
                option.remove();
                exit.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });



        exit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        game.batch.begin();
        game.batch.draw(texture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();

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
