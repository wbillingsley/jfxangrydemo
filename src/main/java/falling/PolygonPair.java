package falling;

        import javafx.scene.shape.Polygon;
        import org.jbox2d.collision.shapes.PolygonShape;
        import org.jbox2d.common.Vec2;
        import org.jbox2d.dynamics.*;

public class PolygonPair extends Pair {

    Polygon shape;

    public PolygonPair(World world, BodyType t, float friction, float restitution, float density, Vec2... points) {
        BodyDef pivotDef = new BodyDef();
        pivotDef.type = t;
        FixtureDef pivotFixDef = new FixtureDef();
        pivotFixDef.friction = friction;
        pivotFixDef.restitution = restitution;
        pivotFixDef.density = density;

        PolygonShape pivotShape = new PolygonShape();
        pivotShape.set(points, points.length);
        pivotFixDef.shape = pivotShape;

        body = world.createBody(pivotDef);
        body.createFixture(pivotFixDef);

        double[] p = new double[points.length * 2];
        for (int i = 0; i < points.length; i++) {
            p[2 * i] = points[i].x;
            p[2 * i + 1] = points[i].y;
        }
        shape = new Polygon(p);
        node = shape;
    }

}
