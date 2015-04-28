package falling;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class JFXApp extends javafx.application.Application {

    Group group;

    Circle ballUi;

    /**
     * The background of the level.
     * This is going to help fix the size of the group.
     */
    Rectangle background;

    Rectangle floor;

    Group box1;
    Group box2;
    Group box3;

    double simUiHeight = 400;

    double simUiWidth = 800;

    Sim sim = new Sim();


    float xConversion() {
        return (float)(simUiWidth / sim.width);
    }

    float yConversion() {
        return - (float)(simUiHeight / sim.height);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ballUi = new Circle();
        ballUi.setRadius(sim.ballRadius * xConversion());
        ballUi.setFill(Color.RED);

        background = new Rectangle();
        background.setWidth(simUiWidth);
        background.setHeight(simUiHeight);
        background.setFill(Color.BLUE);

        floor = new Rectangle();
        floor.setFill(Color.BROWN);

        Rectangle box1r = new Rectangle();
        box1r.setFill(Color.ORANGE);
        box1r.setHeight(sim.boxSize * xConversion());
        box1r.setWidth(sim.boxSize * xConversion());
        box1r.setTranslateX(-sim.boxSize / 2 * xConversion());
        box1r.setTranslateY(- sim.boxSize / 2 * xConversion());
        box1 = new Group(box1r);

        Rectangle box2r = new Rectangle();
        box2r.setFill(Color.ORANGE);
        box2r.setHeight(sim.boxSize * xConversion());
        box2r.setWidth(sim.boxSize * xConversion());
        box2r.setTranslateX(-sim.boxSize / 2 * xConversion());
        box2r.setTranslateY(- sim.boxSize / 2 * xConversion());
        box2 = new Group(box2r);

        Rectangle box3r = new Rectangle();
        box3r.setFill(Color.ORANGE);
        box3r.setHeight(sim.boxSize * xConversion());
        box3r.setWidth(sim.boxSize * xConversion());
        box3r.setTranslateX(-sim.boxSize / 2 * xConversion());
        box3r.setTranslateY(- sim.boxSize / 2 * xConversion());
        box3 = new Group(box3r);




















        group = new Group(background, floor, box1, box2, box3, ballUi);

        ScrollPane scrollPane = new ScrollPane(group);


        Button b = new Button("Fire!");
        b.setOnMouseClicked((evt) -> sim.fire(10));

        Button reset = new Button("Reset");
        reset.setOnMouseClicked((evt) -> sim.reset());

        HBox hbox = new HBox(b, reset);

        VBox vBox = new VBox(scrollPane, hbox);
        vBox.setVgrow(scrollPane, Priority.ALWAYS);


        ArrayList<String> al = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            al.add(Integer.toString(i));
        }

        ObservableList<String> names = FXCollections.observableArrayList(al);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(1000);

















        sim.start(() -> {
            Platform.runLater(() -> {
                float yConversion = yConversion();
                float xConversion = xConversion();

                background.setY(sim.height * yConversion);

                ballUi.setCenterX(sim.ball.getPosition().x * xConversion);
                ballUi.setCenterY(sim.ball.getPosition().y * yConversion);

                box1.setTranslateX(sim.box1.getPosition().x * xConversion);
                box1.setTranslateY(sim.box1.getPosition().y * yConversion);
                box1.setRotate(sim.box1.getAngle() * -180 / Math.PI);
                box2.setTranslateX(sim.box2.getPosition().x * xConversion);
                box2.setTranslateY(sim.box2.getPosition().y * yConversion);
                box2.setRotate(sim.box2.getAngle() * -180 / Math.PI);
                box3.setTranslateX(sim.box3.getPosition().x * xConversion);
                box3.setTranslateY(sim.box3.getPosition().y * yConversion);
                box3.setRotate(sim.box3.getAngle() * -180 / Math.PI);
            });
        });

        primaryStage.setOnCloseRequest((evt) -> System.exit(0));

        primaryStage.show();

    }

}
