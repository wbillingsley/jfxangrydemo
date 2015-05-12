package falling;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import groovy.util.Eval;

public class JFXApp extends javafx.application.Application {

    Scene scene;

    Group group;

    /**
     * The background of the level.
     * This is going to help fix the size of the group.
     */
    Rectangle background;

    Rectangle floor;

    double simUiHeight = 400;

    double simUiWidth = 800;

    Sim sim = new Sim();

    TextArea codeBox;

    ImageView marker;
    ImageView skyMarker;


    MediaPlayer hitHard;
    MediaPlayer hitMed;
    MediaPlayer hitSoft;
    MediaPlayer whee;

    MediaPlayer music;
    boolean musicPlaying = false;

    public void sayWhee() {
        Platform.runLater(() -> {
            whee.play();
            whee.seek(Duration.ZERO);
        });
    }

    public void hitSound(double strength) {
        Platform.runLater(() -> {
            if (strength > 0.3) {
                hitHard.play();
            } else if (strength > 0.1) {
                hitMed.play();
            } else if (strength > 0.001) {
                hitSoft.play();
            }
        });
    }


    float xConversion() {
        return (float)(simUiWidth / sim.width);
    }

    float yConversion() {
        return - (float)(simUiHeight / sim.height);
    }

    public void initMedia() {
        try {
            Media wheeM = new Media(this.getClass().getResource("/wheee.mp3").toString());
            whee = new MediaPlayer(wheeM);
            Media musicM = new Media(this.getClass().getResource("/Harbor.mp3").toString());
            music = new MediaPlayer(musicM);
            music.setCycleCount(MediaPlayer.INDEFINITE);
            Media hitHardM = new Media(this.getClass().getResource("/hithard.mp3").toString());
            hitHard = new MediaPlayer(hitHardM);
            hitHard.setOnEndOfMedia(() -> {
                hitHard.stop();
                hitHard.seek(Duration.millis(0));
            } );            Media hitMedM = new Media(this.getClass().getResource("/hitmedium.mp3").toString());
            hitMed = new MediaPlayer(hitHardM);
            hitHard.setOnEndOfMedia(() -> {
                hitHard.stop();
                hitHard.seek(Duration.millis(0));
            } );
            Media hitSoftM = new Media(this.getClass().getResource("/hitsoft.mp3").toString());
            hitSoft = new MediaPlayer(hitHardM);
            hitSoft.setOnEndOfMedia(() -> {
                hitSoft.stop();
                hitSoft.seek(Duration.millis(0));
            } );
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void initUi() {
        Image bird = new Image(getClass().getResource("/bird.png").toString());
        ImageView birdView = new ImageView(bird);
        birdView.setFitWidth(sim.ballRadius * 2.6);
        birdView.setFitHeight(sim.ballRadius * 2);
        birdView.setTranslateX(-sim.ballRadius * 1.3);
        birdView.setTranslateY(-sim.ballRadius);
        birdView.setScaleY(-1);
        sim.bird.node = birdView;

        ImageView marker = new ImageView(new Image(getClass().getResource("/sling2.png").toString()));
        marker.setFitHeight(sim.markerSize);
        marker.setFitWidth(sim.markerSize);
        marker.setTranslateX(-sim.markerSize/2);
        marker.setScaleY(-1);
        marker.setVisible(false);
        marker.setY(-sim.markerSize / 3);

        ImageView skyMarker = new ImageView(new Image(getClass().getResource("/birdpet.png").toString()));
        skyMarker.setFitHeight(sim.markerSize);
        skyMarker.setFitWidth(sim.markerSize);
        skyMarker.setTranslateX(-sim.markerSize/2);
        skyMarker.setScaleY(-1);
        skyMarker.setVisible(false);


        background = new Rectangle();
        background.setWidth(sim.width);
        background.setHeight(sim.height + 1);
        background.setFill(new ImagePattern(new Image(getClass().getResource("/sky.jpg").toString())));
        background.setScaleY(-1);



        Rectangle clip = new Rectangle();
        clip.setHeight(sim.height + 1 + sim.floorThickness);
        clip.setWidth(sim.width);
        clip.setScaleX(xConversion());
        clip.setScaleY(yConversion());

        sim.floor.shape.setFill(new ImagePattern(new Image(getClass().getResource("/grass.jpeg").toString())));
        sim.floor.shape.setScaleY(-1);

        sim.pig.shape.setFill(new ImagePattern(new Image(getClass().getResource("/piggie_t.png").toString())));
        sim.pig.shape.setScaleY(-1);
        sim.pig.shape.setStrokeWidth(0.1);
        sim.pig.shape.setStroke(Color.BISQUE);

        ImagePattern crate = new ImagePattern(new Image(getClass().getResource("/crate.png").toString()));
        sim.box2.shape.setFill(crate);
        sim.box3.shape.setFill(crate);

        Image shading = new Image(this.getClass().getResource("/wood_c.jpg").toString());

        sim.pivot.shape.setFill(new ImagePattern(shading));
        sim.pivot.shape.setStrokeWidth(0.05);
        sim.pivot.shape.setStroke(Color.DARKGRAY.darker());
        sim.pivot.updateShapePos();

        Image woodH = new Image(this.getClass().getResource("/wood_h.jpg").toString());
        sim.seesaw.shape.setFill(new ImagePattern(woodH));
        //sim.seesaw.shape.setFill(Color.BURLYWOOD);
        sim.seesaw.updateShapePos();

        group = new Group(
                background,
                skyMarker,
                sim.pig.node,
                sim.box2.node,
                sim.box3.node,
                sim.pivot.shape,
                sim.seesaw.shape,
                sim.bird.node,
                sim.floor.shape,
                marker
        );
        group.setScaleX(xConversion());
        group.setScaleY(yConversion());
        Group wrap = new Group(group);
        //group.setClip(clip);

        ScrollPane scrollPane = new ScrollPane(wrap);



        codeBox = new TextArea("sim.reset();\nsim.setGravity(10); \nsim.fire(0); \n ");
        codeBox.setFont(new Font(20));


        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(scrollPane, codeBox);



        Button b = new Button("Run");
        b.setOnAction((evt) -> start());

        Button reset = new Button("Reset");
        reset.setOnAction((evt) -> reset());

        Button toggleMarker = new Button("Marker");
        toggleMarker.setTranslateX(20);
        toggleMarker.setOnAction((evt) -> marker.setVisible(!marker.isVisible()));

        Button toggleSkyMarker = new Button("Sky Marker");
        toggleSkyMarker.setTranslateX(20);
        toggleSkyMarker.setOnAction((evt) -> skyMarker.setVisible(!skyMarker.isVisible()));

        Button musicB = new Button("Music");
        musicB.setOnAction((evt) -> {
            if (musicPlaying) {
                music.pause();
                musicPlaying = false;
            } else {
                musicPlaying = true;
                music.play();
            }
        });
        musicB.setTranslateX(20);

        HBox hbox = new HBox(b, reset, toggleMarker, toggleSkyMarker, musicB);
        VBox vBox = new VBox(splitPane, hbox);

        VBox.setVgrow(splitPane, Priority.SOMETIMES);

        sim.floor.node.setOnMouseClicked((evt) -> {
            marker.setX(evt.getX());
            marker.setVisible(true);
        });
        background.setOnMouseClicked((evt) -> {
            skyMarker.setX(evt.getX());
            skyMarker.setY(background.getHeight() - evt.getY());
            skyMarker.setVisible(true);
        });

        scene = new Scene(vBox);
        updateUi();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUi();
        initMedia();

        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(simUiWidth);
        primaryStage.setOnCloseRequest((evt) -> System.exit(0));
        primaryStage.show();
    }

    public void eval() {
        Eval.me("sim", sim, codeBox.getText());
    }

    public void start() {
        if (!sim.started) {
            eval();
            sim.start(() -> updateUi(), (strength) -> hitSound(strength));
            sayWhee();
        }
    }

    public void reset() {
        if (sim.started) {
            sim.stop();
        }
        eval();
        updateUi();
    }

    public void updateUi() {
        Platform.runLater(() -> {
            sim.pivot.updateShapePos();
            sim.seesaw.updateShapePos();
            sim.pig.updateShapePos();
            sim.box2.updateShapePos();
            sim.box3.updateShapePos();
            sim.bird.updateShapePos();
        });
    }

}
