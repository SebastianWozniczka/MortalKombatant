package com.mygdx.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.screens.GamePlay;

public class Player extends Sprite {
    private static World world;
    private GamePlay screen;
    private float positionX, positionY;
    public Body b2body;



    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD, KRZYK, SEN, ATAK, ATAK2,BUL,LOT}
    public static State currentState;
    public static State previousState;
    public static float stateTimer,stateTime;
    public static boolean runningRight;

    private TextureAtlas player_atlas;

    private Animation playerAnimation,ruchAnimation;

    public Player(float positionX, float positionY, World world, GamePlay screen) {


        this.world = world;
        this.screen = screen;
        this.positionX = positionX;
        this.positionY = positionY;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight=true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        player_atlas = new TextureAtlas("player/player.pack");


        for (int i = 1; i < 3; i++)
            frames.add(new TextureRegion(player_atlas.findRegion("player"), i * 64, 0, 64, 128));
        playerAnimation = new Animation(0.01f, frames);
        frames.clear();


        Texture walkSheet=new Texture("player/player_ruch.png");
        TextureRegion[][] tmp=TextureRegion.split(walkSheet,walkSheet.getWidth()/6,walkSheet.getHeight()/5);


        TextureRegion[] walkFrames=new TextureRegion[18];
        int index=0;
        for(int i=0;i<3;i++) {
            for (int j = 0; j < 6 ; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        ruchAnimation=new Animation<TextureRegion>(0.1f,walkFrames);
        stateTime=0f;




        definePlayer();
        setBounds(0, 0, 200, 256);

    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(positionX,positionY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(64,128);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(Float dt) {
        currentState = getState(dt);

     TextureRegion region;

        region = (TextureRegion)playerAnimation.getKeyFrame(stateTimer,true);


        switch (currentState) {
            case JUMPING:

                break;
            case RUNNING:
               region= (TextureRegion) ruchAnimation.getKeyFrame(stateTimer,true);
                break;

            case LOT:

                break;
            case FALLING:

                break;

            case DEAD:

                break;

            case STANDING:
                region = (TextureRegion)ruchAnimation.getKeyFrame(0);
                break;
            default:
                region = (TextureRegion)ruchAnimation.getKeyFrame(0);

        }


        if((b2body.getLinearVelocity().x < 0 || !runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }


        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(float dt) {





         if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
         else if(b2body.getLinearVelocity().x != 0)
             return State.RUNNING;


            return State.STANDING;


    }

    public void jump() {
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 1000), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }

    }


}
