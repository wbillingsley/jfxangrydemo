package falling;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class JFXApp extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Circle circle = new Circle();
        circle.setRadius(100);
        circle.setFill(Color.ORANGE);



        Rectangle r1 = new Rectangle();
        r1.setX(100);
        r1.setY(50);
        r1.setFill(Color.ORANGE);
        r1.setHeight(40);
        r1.setWidth(50);

        Rectangle r2 = new Rectangle();
        r2.setX(50);
        r2.setY(100);
        r2.setFill(Color.RED);
        r2.setHeight(40);
        r2.setWidth(50);

        Button b = new Button("Hello");
        b.setOnMouseClicked((evt) -> r2.setFill(Color.GREEN));
        //b.setTranslateX(100);

        HBox hbox = new HBox(circle, r1, r2, b);

        /*Group group = new Group(circle, r1, r2, b);

        group.setTranslateX(200);
        group.setTranslateY(200);
        group.setRotate(90);*/

        r2.setRotate(45);

        Scene scene = new Scene(hbox);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

}
