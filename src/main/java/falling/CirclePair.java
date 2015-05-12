package falling;

import javafx.scene.shape.Circle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class CirclePair extends Pair {

    Circle shape;

    public CirclePair(World world, BodyType t, float friction, float restitution, float density, float radius) {
        BodyDef pivotDef = new BodyDef();
        pivotDef.type = t;
        FixtureDef pivotFixDef = new FixtureDef();
        pivotFixDef.friction = friction;
        pivotFixDef.restitution = restitution;
        pivotFixDef.density = density;

        CircleShape pivotShape = new CircleShape();
        pivotShape.setRadius(radius);
        pivotFixDef.shape = pivotShape;

        body = world.createBody(pivotDef);
        body.createFixture(pivotFixDef);

        shape = new Circle(radius);
        node = shape;
    }

}
