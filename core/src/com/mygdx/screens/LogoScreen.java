package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

import java.util.Timer;
import java.util.TimerTask;

public class LogoScreen implements Screen {

    MyGdxGame game;
    SpriteBatch batch;
    ParticleEffect pe;
    float timer;

    public LogoScreen(MyGdxGame game){
        this.game=game;
        timer=0;
        batch=new SpriteBatch();
        pe=new ParticleEffect();
        pe.load(Gdx.files.internal("logo.party"), Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        pe.getEmitters().first().scaleSize(20);
        pe.start();
    }
    @Override
    public void show() {


    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



     timer+=v;

     if(timer>5){
         game.setScreen(new MenuScreen(game));
     }


        pe.update(v);
       game.batch.begin();
        pe.draw(game.batch);
       game.batch.end();
        if (pe.isComplete())
            pe.reset();


    }




    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
        game.setPaused(true);
    }

    @Override
    public void resume() {
        game.setPaused(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
