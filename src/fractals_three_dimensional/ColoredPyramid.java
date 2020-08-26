package fractals_three_dimensional;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class ColoredPyramid{// extends Application {

    /*@Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 720, 720, true);
        primaryStage.setTitle("Colored Pyramid");
        primaryStage.setScene(scene);
        primaryStage.show();

        Group group = getPyramid(200, 300, 100, 300 , 0);
        group.setTranslateX(100);
        group.setTranslateY(80);
        root.getChildren().add(group);
    }*/

    public ColoredPyramid() {
    }

    public Group getPyramid(float height, float hypotenuse, double x, double y, double z){
        TriangleMesh colouredPyramid = new TriangleMesh();
        float delta = hypotenuse * 0.25f * (float)Math.sqrt(2);
        colouredPyramid.getPoints().addAll(0, 0, 0); //0-index:: top
        colouredPyramid.getPoints().addAll(-delta, height, -delta); //1-index:: x=0, z=-hyp/2 ==> Closest to user
        colouredPyramid.getPoints().addAll(-delta, height, delta); //2-index:: x=hyp/2,  z=0 ==> Leftest
        colouredPyramid.getPoints().addAll(delta, height, -delta);  //3-index:: x=hyp/2,  z=0 ==> rightest
        colouredPyramid.getPoints().addAll(delta, height, delta); ////4-index:: x=0, z=hyp/2  ==> Furthest from user

        //Next statement copied from stackoverflow.com/questions/26831871/coloring-individual-triangles-in-a-triangle-mesh-on-javafx
        colouredPyramid.getTexCoords().addAll(
                0.1f, 0.5f, // 0 red
                0.3f, 0.5f, // 1 green
                0.5f, 0.5f, // 2 blue
                0.7f, 0.5f, // 3 yellow
                0.9f, 0.5f  // 4 orange
        );

        colouredPyramid.getFaces().addAll(0, 0, 2, 0, 1, 0); //Left front face ---> RED
        colouredPyramid.getFaces().addAll(0, 1, 1, 1, 3, 1); //Right front face ---> GREEN
        colouredPyramid.getFaces().addAll(0, 2, 3, 2, 4, 2); //Right back face ---> BLUE
        colouredPyramid.getFaces().addAll(0, 3, 4, 3, 2, 3); //Left back face ---> RED
        colouredPyramid.getFaces().addAll(4, 4, 1, 4, 2, 4); //Base: left triangle face ---> YELLOW
        colouredPyramid.getFaces().addAll(4, 0, 3, 0, 1, 0); //Base: right triangle face ---> ORANGE

        MeshView pyramid = new MeshView(colouredPyramid);
        pyramid.setDrawMode(DrawMode.FILL);

        Group res = new Group(pyramid);
        Translate translate = new Translate();
        translate.setX(x); translate.setY(y); translate.setZ(z);
        res.getTransforms().add(translate);
        return res;
    }

    //public static void main(String[] args) {
    //    launch(args);
    //}
}