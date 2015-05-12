package falling;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.Timer;
import java.util.TimerTask;

public class Sim {

    /**
     * The bird we're showing
     */
    CirclePair bird;

    /**
     * The floor
     */
    PolygonPair floor;

    PolygonPair pivot;

    PolygonPair seesaw;

    PolygonPair pig;
    PolygonPair box2;
    PolygonPair box3;

    PolygonPair stage;

    float pivotHeight = 2f;

    float boxSize = 0.5f;
    float pigSize = 0.7f;

    float markerSize = 1f;

    float ballRadius = 0.7f;

    float plankLength = 4f;
    float plankThickness = 0.1f;

    float floorThickness = 1f;

    /**
     * The world the bird inhabits
     */
    World world;


    float height = 10;

    float width = 20;

    Timer timer;

    boolean started;

    public void fire(float strength) {
        bird.body.setLinearVelocity(new Vec2(0, -strength));
    }


    public void setGravity(float g) {
        world.setGravity(new Vec2(0, -g));
    }

    public void setGravity(float gx, float gy) {
        world.setGravity(new Vec2(gx, -gy));
    }


    public void reset() {
        int pad = 5;

        bird.body.setTransform(new Vec2(pad + 7.5f, 8 + ballRadius), 0);
        pivot.body.setTransform(new Vec2(pad + 10, 0), 0);
        seesaw.body.setTransform(new Vec2(pad + 9.5f, pivotHeight + plankThickness/2), 0);
        pig.body.setTransform(new Vec2(pad + 10.5f, pivotHeight + pigSize + plankThickness), 0);
        box2.body.setTransform(new Vec2(1, boxSize), 0);
        box3.body.setTransform(new Vec2(1, 3 * boxSize), 0);

        bird.body.setLinearVelocity(new Vec2(0, 0));
        seesaw.body.setLinearVelocity(new Vec2(0, 0));
        pig.body.setLinearVelocity(new Vec2(0, 0));
        box2.body.setLinearVelocity(new Vec2(0, 0));
        box3.body.setLinearVelocity(new Vec2(0, 0));

        bird.body.setAngularVelocity(0);
        seesaw.body.setAngularVelocity(0);
        pig.body.setAngularVelocity(0);
        box2.body.setAngularVelocity(0);
        box3.body.setAngularVelocity(0);
    }


    /**
     * Set up the simulation
     */
    public Sim() {
        Vec2 gravity = new Vec2(0f, -10f);
        this.world = new World(gravity);

        bird = new CirclePair(world, BodyType.DYNAMIC, 0.2f, 0.3f, 0.2f, ballRadius);

        floor = new PolygonPair(
                world, BodyType.STATIC, 0.2f, 0.3f, 0.3f,
                new Vec2(0,0), new Vec2(width,0), new Vec2(width,-floorThickness), new Vec2(0,-floorThickness)
                );
        floor.body.setTransform(new Vec2(0, 0), 0);

       /* pivot = new PolygonPair(
            world, BodyType.STATIC, 0.9f, 0.3f, 0.3f,
            new Vec2(-1,0), new Vec2(0,pivotHeight), new Vec2(1,0)
        );*/

        pivot = new PolygonPair(
                world, BodyType.STATIC, 0.9f, 0.3f, 0.3f,
                new Vec2(-1,0), new Vec2(-1,pivotHeight), new Vec2(4, pivotHeight), new Vec2(4,0)
        );


        seesaw = new PolygonPair(
                world, BodyType.DYNAMIC, 0.9f, 0.3f, 0.1f,
                new Vec2(-plankLength/2,-plankThickness/2), new Vec2(-plankLength/2,plankThickness/2), new Vec2(plankLength/2,plankThickness/2), new Vec2(plankLength/2,-plankThickness/2)
        );

        pig = new PolygonPair(
                world, BodyType.DYNAMIC, 0.2f, 0.3f, 0.075f,
                new Vec2(-pigSize,-pigSize), new Vec2(-pigSize,pigSize), new Vec2(pigSize,pigSize), new Vec2(pigSize,-pigSize)
        );

        box2 = new PolygonPair(
                world, BodyType.DYNAMIC, 0.5f, 0.3f, 0.2f,
                new Vec2(-boxSize,-boxSize), new Vec2(-boxSize,boxSize), new Vec2(boxSize,boxSize), new Vec2(boxSize,-boxSize)
        );

        box3 = new PolygonPair(
                world, BodyType.DYNAMIC, 0.5f, 0.3f, 0.2f,
                new Vec2(-boxSize,-boxSize), new Vec2(-boxSize,boxSize), new Vec2(boxSize,boxSize), new Vec2(boxSize,-boxSize)
        );

        bird.body.setLinearDamping(0.1f);
        pig.body.setLinearDamping(0.1f);
        seesaw.body.setLinearDamping(0.1f);
        box2.body.setLinearDamping(0.1f);
        box3.body.setLinearDamping(0.1f);

        reset();

    }

    /**
     * Set the simulation running
     */
    public void start(Runnable updateUi, HitPower onContact) {
        if (!started) {
            started = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    world.step(1 / 60f, 6, 3);
                    updateUi.run();

                    ContactListener listener = new ContactListener() {
                        @Override
                        public void beginContact(Contact contact) {
                        }

                        @Override
                        public void endContact(Contact contact) {
                        }

                        @Override
                        public void preSolve(Contact contact, Manifold oldManifold) {

                        }

                        @Override
                        public void postSolve(Contact contact, ContactImpulse impulse) {
                            double str = Math.pow(impulse.normalImpulses[0], 2) + Math.pow(impulse.normalImpulses[1], 2);
                            if (str > 0.05f) {
                                onContact.hit(str);
                            }

                        }
                    };

                    world.setContactListener(listener);
                }
            }, 1000 / 60, 1000 / 60);
        }
    }

    /**
     * Set the simulation running
     */
    public void stop() {
        timer.cancel();
        started = false;
    }




}
