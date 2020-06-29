package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.scenes.Hud;
import com.mygdx.sprites.Player;
import com.mygdx.sprites.Przeciwnik1;


public class GamePlay implements Screen {
    MyGdxGame game;
    Array<Przeciwnik1> p1;
    Hud hud;
    Player player;
    private float timer1;


    private OrthographicCamera cam;
    private Viewport port;

    private World world;
    private Box2DDebugRenderer b2dr;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    private Texture spod;



    public GamePlay(MyGdxGame game) {
        this.game=game;
        timer1=0;
        init();
        world=new World(new Vector2(0,-20f),true);
        cam=new OrthographicCamera();
        port=new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),cam);
        hud=new Hud(game.batch);

        mapLoader=new TmxMapLoader();
        map=mapLoader.load("map/mapa1.tmx");
        renderer=new OrthogonalTiledMapRenderer(map);

        cam.position.set(port.getWorldWidth()/2,port.getWorldHeight()/2,0);



        b2dr=new Box2DDebugRenderer();
        p1=new Array<>();


                p1.add(new Przeciwnik1(world,this,600,200));



        getPlayer();



    }

    private void init(){
        MyGdxGame.manager.get("sounds/menu.mp3", Music.class).play();
        MyGdxGame.manager.get("sounds/menu.mp3", Music.class).setLooping(true);
        spod=new Texture("map/spod.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        update(v);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.render();


        game.batch.setProjectionMatrix(cam.combined);
       // b2dr.render(world,cam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Przeciwnik1 enemy:p1){
            enemy.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for (MapObject object : map.getLayers().get("ziemia").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) , (rect.getY() + rect.getHeight() / 2) );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 , rect.getHeight() / 2 );
            fdef.shape = shape;


            body.createFixture(fdef);
        }

        RevoluteJointDef rdef=new RevoluteJointDef();
        rdef.bodyA=player.b2body;
        rdef.bodyB=Przeciwnik1.b2body;
        rdef.localAnchorA.set(100,100);
        //world.createJoint(rdef);
    }


    public void getPlayer(){
        final Rectangle rectangle=map.getLayers().get("player").getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        player=new Player(rectangle.getX(),rectangle.getY(),world,this);

    }

    public void update(float dt){

       timer1+=dt;
       if(timer1>6)
        handleInput();


        world.step(1 /60f, 6, 2);
        player.update(dt);

        for(Przeciwnik1 enemy : p1) {
            enemy.update(dt);
        }


        hud.update(dt);
        cam.update();
        renderer.setView(cam);

        cam.position.x=player.b2body.getPosition().x;
    }

    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        //  player.jump();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 20) {
            player.b2body.setLinearVelocity(new Vector2(100, 0) );

        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -20) {

            player.b2body.setLinearVelocity(new Vector2(-100, 0));
        }
    }

    @Override
    public void resize(int i, int i1) {
        port.update(i, i1);
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
