package falling;

import javafx.scene.Node;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public abstract class Pair {

    Body body;

    Node node;

    public void updateShapePos() {
        node.setLayoutX(body.getPosition().x);
        node.setLayoutY(body.getPosition().y);
        node.setRotate(body.getAngle() * 180 / Math.PI);
    }

    public void nudge(float x, float y) {
        Vec2 p = body.getPosition();
        float a = body.getAngle();
        body.setTransform(new Vec2(p.x +x, p.y + y), a);
        updateShapePos();
    }

}
