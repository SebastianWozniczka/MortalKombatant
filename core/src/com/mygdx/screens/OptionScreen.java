package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.MyGdxGame;

public class OptionScreen implements Screen {

    private Label label;
    private BitmapFont font;
    private Stage stage;
    private Image latwy,sredni, trudny;
    public static int l=0, s=0, t=0;
    int maxX=Gdx.graphics.getWidth();
    int maxY= (int) (Gdx.graphics.getHeight()/2.5f);

    MyGdxGame game;
    public OptionScreen(MyGdxGame game) {
        this.game=game;
        stage=new Stage();
        font=new BitmapFont(Gdx.files.internal("foo.fnt"));
        label = new com.badlogic.gdx.scenes.scene2d.ui.Label("Poziom Trudnsci", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font, Color.RED));
        label.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        label.setPosition(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/8);
        latwy=new Image(new Texture("menu/latwy.png"));
        latwy.setPosition(0,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3);
        latwy.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/5);
        latwy.setColor(Color.YELLOW);

        sredni=new Image(new Texture("menu/sredni.png"));
        sredni.setPosition(0,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/2);
        sredni.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/5);
        sredni.setColor(Color.WHITE);

        latwy.addListener(new InputListener(){


            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                reakcje();

                if(latwy.getImageWidth()>maxX){
                    latwy.setWidth(maxX);
                }

                if(latwy.getImageHeight()>maxY){
                    latwy.setHeight(maxY);
                }
                return super.mouseMoved(event, x, y);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                reakcje2();
                super.exit(event, x, y, pointer, toActor);
            }

            private void reakcje2() {
                Action testAction=Actions.sizeTo(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/5,0.2f);
                latwy.addAction(testAction);
            }

            private void reakcje() {
                Action testAction=Actions.sizeBy(2,2,0.2f);
                latwy.addAction(testAction);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                latwy.setColor(Color.YELLOW);
                sredni.setColor(Color.WHITE);
                l=1;
                return super.touchDown(event, x, y, pointer, button);


            }


        });

        sredni.addListener(new InputListener(){


            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                reakcje4();
                if(sredni.getImageWidth()>maxX){
                    sredni.setWidth(maxX);
                }

                if(sredni.getImageHeight()>maxY){
                    sredni.setHeight(maxY);
                }
                return super.mouseMoved(event, x, y);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                reakcje3();
                super.exit(event, x, y, pointer, toActor);
            }

            private void reakcje3() {
                Action testAction=Actions.sizeTo(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/5,0.2f);
                sredni.addAction(testAction);
            }

            private void reakcje4() {
                Action testAction=Actions.sizeBy(2,2,0.2f);
                sredni.addAction(testAction);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                sredni.setColor(Color.YELLOW);
                latwy.setColor(Color.WHITE);
                s=1;
                return super.touchDown(event, x, y, pointer, button);


            }


        });
        stage.addActor(label);
        stage.addActor(latwy);
        stage.addActor(sredni);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act(delta);
        Gdx.input.setInputProcessor(stage);
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
