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
        Group root = Sierpinski_tetrahedron(400, 600, 0, -250,  0);

                //new Group(Jerusalem_cube(new Box(300, 300, 300), 0, 0, 0));
        //Group root = new Group(Menger_sponge(new Box(162, 162, 162), 0, 0, 0));
        Scene scene = new Scene(root, 720, 720, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.GREEN);
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

    private static Group Sierpinski_tetrahedron(float height, float hypotenuse, double x, double y, double z){
        Group result = new Group();
        ColoredPyramid coloredPyramid = new ColoredPyramid();
        if (hypotenuse > 30){
            double delta = hypotenuse * 0.125 * Math.sqrt(2);
            result.getChildren().addAll(Sierpinski_tetrahedron(height*0.5f, hypotenuse*0.5f, x,y,z));
            result.getChildren().addAll(Sierpinski_tetrahedron(height*0.5f, hypotenuse*0.5f, x+delta,y+height*0.5f,z+delta));
            result.getChildren().addAll(Sierpinski_tetrahedron(height*0.5f, hypotenuse*0.5f, x+delta,y+height*0.5f,z-delta));
            result.getChildren().addAll(Sierpinski_tetrahedron(height*0.5f, hypotenuse*0.5f, x-delta,y+height*0.5f,z+delta));
            result.getChildren().addAll(Sierpinski_tetrahedron(height*0.5f, hypotenuse*0.5f, x-delta,y+height*0.5f,z-delta));
        }else {
            result.getChildren().addAll(coloredPyramid.getPyramid(height, hypotenuse, x, y, z));
        }
        return result;
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
                    if (w > 20) {
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

    private static Group Jerusalem_cube(Box mainBox, double x, double y, double z){
        Group result = new Group();
        double w = mainBox.getWidth();
        final double d = Math.sqrt(2) - 1;
        final double delta = d*w*0.5;
        final double theta = (1 - Math.sqrt(2)*0.5)*w*0.5;
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                for (int k = -2; k < 3; k++) {
                    int m = Math.abs(i) * Math.abs(j) * Math.abs(k);
                    int s = Math.abs(i) + Math.abs(j) + Math.abs(k);
                    if (s < 4 && m != 1) continue;
                    if (w > 50) {
                        if (Math.abs(i) == 2 && Math.abs(j) == 2 && Math.abs(k) == 2) {
                            result.getChildren().addAll(Jerusalem_cube(new Box(w * d, w * d, w * d), x + i*theta, y + j*theta, z + k*theta));
                        }else if (s == 4 && m == 0){
                            result.getChildren().addAll(Jerusalem_cube(new Box(w * d * d, w * d * d, w * d * d), x + i*delta, y + j*delta, z + k*delta));
                        }
                    }else{
                        Box temp;
                        if (Math.abs(i) == 2 && Math.abs(j) == 2 && Math.abs(k) == 2) {
                            temp = new Box(w * d, w * d, w * d);
                            temp.setTranslateX(x + i * theta); temp.setTranslateY(y + j * theta); temp.setTranslateZ(z + k * theta);
                        }else if (s == 4 && m == 0){
                            temp = new Box(w*d*d, w*d*d, w*d*d);
                            temp.setTranslateX(x + i * delta); temp.setTranslateY(y + j * delta); temp.setTranslateZ(z + k * delta);
                        }else continue;
                        result.getChildren().add(temp);
                    }
                }
            }
        }
        return result;
    }
}