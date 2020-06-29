package com.mygdx.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.screens.GamePlay;

public class Przeciwnik1 extends Sprite {
    private static World world;
    private TextureRegion regionJ;
    private GamePlay screen;
    private float positionX, positionY;
    public static Body b2body;
    private float stateTime;
    private Animation p1Standing;
    private enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD, KRZYK, SEN, ATAK, ATAK2,BUL,LOT}
    private static State currentState;
    private static State previousState;
    public Przeciwnik1(World world, GamePlay screen, float positionX, float positionY){
        this.world = world;
        this.screen = screen;
        this.positionX = positionX;
        this.positionY = positionY;
        regionJ=new TextureRegion(new Texture("enemy/p1_skok.png"));
        stateTime=0;
        currentState = State.STANDING;
        previousState = State.STANDING;
        Texture walkSheet=new Texture("enemy/przeciwnik1.png");
        TextureRegion[][] tmp=TextureRegion.split(walkSheet,walkSheet.getWidth()/3,walkSheet.getHeight());


        TextureRegion[] walkFrames=new TextureRegion[3];
        int index=0;
        for(int i=0;i<1;i++) {
            for (int j = 0; j < 3 ; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        p1Standing=new Animation<TextureRegion>(0.1f,walkFrames);
        stateTime=0f;


        defineP1();
        setBounds(0, 0, 128, 128);
        setRegion(regionJ);


    }


    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(Float dt) {
        currentState = getState(dt);

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region=regionJ;
                break;
            case RUNNING:
                region = (TextureRegion) p1Standing.getKeyFrame(stateTime, true);
                break;

            default:
                region=regionJ;
        }
        stateTime = currentState == previousState ? stateTime+ dt : 0;
        previousState = currentState;
        return region;

    }

    private State getState(Float dt) {
        if((b2body.getLinearVelocity().y > 0 && currentState ==State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;


        return State.STANDING;
    }

    private void defineP1() {

            BodyDef bdef = new BodyDef();
            bdef.position.set(positionX,positionY);
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);





            FixtureDef fdef = new FixtureDef();
            PolygonShape shape=new PolygonShape();
            shape.setAsBox(64,64);


        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                b2body.applyLinearImpulse(new Vector2(50,50),b2body.getWorldCenter(),true);
            }
        },6,5,10);
            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData("p1");


    }


}
