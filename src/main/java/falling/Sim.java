package falling;

import org.jbox2d.collision.shapes.CircleShape;
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
     * The world the ball inhabits
     */
    World world;

    /**
     * Set up the simulation
     */
    public void Sim() {
        Vec2 gravity = new Vec2(0f, -10f);
        this.world = new World(gravity);

        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyType.DYNAMIC;

        FixtureDef ballFixDef = new FixtureDef();
        ballFixDef.density = 0.3f;

        ballFixDef.shape = new CircleShape();
        ballFixDef.shape.m_radius = 10f;

        this.ball = world.createBody(ballDef);
        ball.createFixture(ballFixDef);
    }

    /**
     * Set the simulation running
     */
    public void start() {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                this.world.step(1/60f, 6, 3);
                Vec2 pos = ball.getPosition();
                System.out.println(pos.y);
            }
        }, 1000/60, 1000/60);
    }




}
