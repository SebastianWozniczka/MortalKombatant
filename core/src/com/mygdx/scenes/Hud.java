package com.mygdx.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;


public class Hud  implements Disposable {


    public static Stage stage;
    private Viewport viewport;

    private BitmapFont font;

    private int worldTimer;
    private boolean timeUp;
    private float timeCount;
    private static int score;
    private int pierwszaLiczba;
    private int drugaLiczba;
    private int graczTimer;
    public static Table table;
    public static int zycieGracza1;
    public static int zycieGracza2;
    public static float zycieGracza3;
    public static int punktyGracza;
    public static int punktyPrzeciwnika;
    public static int round;

    private static Label countdownLabel;
    private static Label scoreLabel;
    private static Label timeLabel;
    private static Label levelLabel;
    private static Label worldLabel;
    private static Label marioLabel;
    private static Label graczCzas;
    private static Label labelRound;

    public static Label nazwaGracza1;
    public static Label wins;
    public static  Label fap;

    public static Image hp1;




    public Hud(SpriteBatch sb){

        worldTimer = 300;
        graczTimer=60;
        timeCount = 0;
        round=1;
        score = 0;
        pierwszaLiczba=4;
        drugaLiczba=60;
        zycieGracza1=120;
        zycieGracza2=256;
        zycieGracza3=2.50F;
        punktyGracza=0;
        punktyPrzeciwnika=0;
        font = new BitmapFont(Gdx.files.internal("fonts/foo.fnt"));

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        table = new Table();

        table.top();

        table.setFillParent(true);
        countdownLabel = new Label("5:00", new Label.LabelStyle(font, Color.PURPLE));
        if(pierwszaLiczba<4)
            countdownLabel = new Label("5:00", new Label.LabelStyle(font, Color.RED));
        countdownLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        countdownLabel.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/8);




        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        timeLabel = new Label("Czas", new Label.LabelStyle(font, Color.BLACK));
        timeLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        levelLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        worldLabel = new Label("SWIAT", new Label.LabelStyle(font, Color.BLACK));
        worldLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        marioLabel = new Label("PUNKTY", new Label.LabelStyle(font, Color.BLACK));
        marioLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        graczCzas = new Label(String.format("%02d",graczTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        graczCzas.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        nazwaGracza1 = new Label(("Jarek "+zycieGracza1+" HP"), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        wins=new Label((String.format(punktyGracza+" "+punktyPrzeciwnika+" WINS")), new Label.LabelStyle(font, Color.YELLOW));
        wins.setPosition(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/10);
        wins.setSize(Gdx.graphics.getWidth()/20,Gdx.graphics.getHeight()/20);
        labelRound=new Label((String.format("ROUND "+round)), new Label.LabelStyle(font, Color.RED));
        labelRound.setSize(Gdx.graphics.getWidth()/20,Gdx.graphics.getHeight()/20);
        akcje();
        labelRound.setPosition(0,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3);
        fap=new Label((String.format("FIGHT"+round)), new Label.LabelStyle(font, Color.FOREST));
        fap.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        fap.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+Gdx.graphics.getHeight()/3);

        hp1=new Image(new Texture("menu/hp20.png"));
        hp1.setPosition(0,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/7);
        hp1.setSize(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/30);


        table();



    }

    private void akcje() {
        MyGdxGame.manager.get("sounds/round1.mp3", Music.class).play();
        Action testAction=Actions.moveTo(Gdx.graphics.getWidth()/2.5f,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3,3, Interpolation.pow3);
        labelRound.addAction(testAction);
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
              akcje2();
            }
        },4);
    }

    private void akcje2() {
        Action testAction=Actions.moveTo(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3,3, Interpolation.pow3);
        labelRound.addAction(testAction);
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                akcje3();

            }
        },4);
    }

    private void akcje3(){
        fap.setVisible(true);
        MyGdxGame.manager.get("sounds/figth.mp3", Music.class).play();
        Action testAction=Actions.rotateBy(360,3);
        fap.addAction(testAction);
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                fap.remove();
            }
        },3);
    }

    public static void table() {


        stage.addActor(countdownLabel);
        stage.addActor(scoreLabel);
        stage.addActor(wins);
        stage.addActor(labelRound);

        stage.addActor(fap);
        stage.addActor(hp1);
        fap.setVisible(false);

    }



    public void update(float dt){




        stage.act(dt);
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                graczTimer--;
                worldTimer--;
                drugaLiczba--;
                if(drugaLiczba<0){
                    pierwszaLiczba--;
                    drugaLiczba=59;
                }





                if(graczTimer<30){
                    graczCzas.setColor(Color.YELLOW);
                }
                if(graczTimer<10){
                    graczCzas.setColor(Color.RED);
                    graczCzas.setText("0"+graczTimer);
                }
                if(graczTimer<5){
                    isTimeUp();
                }

                if(graczTimer<0){
                    graczTimer=0;
                }


            } else {
                timeUp = true;
            }
            countdownLabel.setText( pierwszaLiczba+":"+drugaLiczba);
            graczCzas.setText( graczTimer);
            timeCount = 0;

            if(drugaLiczba<10){
                countdownLabel.setText( pierwszaLiczba+":0"+drugaLiczba);
            }
        }

        }

    private void isTimeUp() {

    }

    @Override
    public void dispose() { stage.dispose(); }


}

