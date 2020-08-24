package fractals_three_dimensional;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main_3d extends Application {
    @Override
    public void start(Stage stage) {
        Group root = new Group(Menger_sponge(new Box(162, 162, 162), 0, 0, 0));
        Scene scene = new Scene(root, 720, 720, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);
        Translate pivot = new Translate();
        pivot.setX(0);
        pivot.setY(0);
        pivot.setZ(0);
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(-360, -360, -50)
        );

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15),
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        scene.setCamera(camera);
        stage.setTitle("3d Fractal");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }

    private static Group Menger_sponge(Box mainBox, double x, double y, double z){
        Group result = new Group();
        mainBox.setTranslateX(x);
        mainBox.setTranslateY(y);
        mainBox.setTranslateZ(z);
        double w = mainBox.getWidth();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if (Math.abs(i) + Math.abs(j) + Math.abs(k) < 2) continue;
                    if (w > 10) {
                        result.getChildren().addAll(Menger_sponge(new Box(w / 3, w / 3, w / 3), x + i * w, y + j * w, z + k * w));
                    }else{
                        Box temp = new Box(w,w,w);
                        temp.setTranslateX(x+i*w); temp.setTranslateY(y+j*w); temp.setTranslateZ(z+k*w);
                        result.getChildren().add(temp);
                    }
                }
            }
        }
        return result;
    }
}