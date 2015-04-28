package falling;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Timer;
import java.util.TimerTask;

public class Sim {

    /**
     * The ball we're showing
     */
    Body ball;

    /**
     * The floor
     */
    Body floor;

    Body box1;
    Body box2;
    Body box3;

    float boxSize = 0.5f;

    float ballRadius = 0.1f;

    /**
     * The world the ball inhabits
     */
    World world;


    float height = 10;

    float width = 20;

    public void fire(float strength) {
        ball.setLinearVelocity(new Vec2(strength, strength));
    }


    public void reset() {
        ball.setTransform(new Vec2(1, 0), 0);
        box1.setTransform(new Vec2(15, boxSize/2), 0);
        box2.setTransform(new Vec2(15, 3 * boxSize/2), 0);
        box3.setTransform(new Vec2(15, 5 * boxSize/2), 0);
    }


    /**
     * Set up the simulation
     */
    public Sim() {
        Vec2 gravity = new Vec2(0f, -10f);
        this.world = new World(gravity);

        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyType.DYNAMIC;

        FixtureDef ballFixDef = new FixtureDef();
        ballFixDef.density = 0.3f;
        ballFixDef.restitution = 0.3f;
        ballFixDef.friction = 0.2f;


        ballFixDef.shape = new CircleShape();
        ballFixDef.shape.m_radius = ballRadius;

        this.ball = world.createBody(ballDef);
        ball.createFixture(ballFixDef);

        ball.setLinearDamping(0.3f);


        BodyDef floorDef = new BodyDef();
        floorDef.type = BodyType.STATIC;
        FixtureDef floorFixDef = new FixtureDef();
        floorFixDef.friction = 0.2f;
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(width * 2, 0.5f);
        floorFixDef.shape = floorShape;

        this.floor = world.createBody(floorDef);
        this.floor.createFixture(floorFixDef);
        this.floor.setTransform(new Vec2(width, -0.5f), 0);

        BodyDef boxDef = new BodyDef();
        boxDef.type = BodyType.DYNAMIC;
        FixtureDef boxFixDef = new FixtureDef();
        boxFixDef.friction = 0.2f;
        boxFixDef.restitution = 0.3f;
        boxFixDef.density = 0.05f;
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(boxSize / 2, boxSize / 2);
        boxFixDef.shape = boxShape;

        this.box1 = world.createBody(boxDef);
        box1.createFixture(boxFixDef);

        this.box2 = world.createBody(boxDef);
        box2.createFixture(boxFixDef);

        this.box3 = world.createBody(boxDef);
        box3.createFixture(boxFixDef);

        reset();

    }

    /**
     * Set the simulation running
     */
    public void start(Runnable updateUi) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                world.step(1/60f, 6, 3);
                updateUi.run();
            }
        }, 1000/60, 1000/60);
    }




}
