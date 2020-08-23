/*
 * professorik  2020.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane(anchorPane);
        Scene scene = new Scene(scrollPane, 720, 720);
        primaryStage.setTitle("Fractals");
        primaryStage.setScene(scene);
        primaryStage.show();
        //anchorPane.getChildren().add(Sierpiński_triangle());
        //anchorPane.getChildren().add(circleFractal(360, 360,  360));
        anchorPane.getChildren().add(tree_fractal(new Line(360, 540, 360, 360), 7*Math.PI/9));
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Group tree_fractal(Line mainLine, double teta) throws InterruptedException {
        Group result = new Group();
        double l = Math.sqrt(Math.pow(mainLine.getEndX()-mainLine.getStartX(), 2) + Math.pow(mainLine.getEndY()-mainLine.getStartY(), 2));
        result.getChildren().add(mainLine);
        if (l > 1){
            double x0, y0, y1, x1;
            x0 = mainLine.getEndX() + (mainLine.getStartX()-mainLine.getEndX())*Math.cos(teta) - (mainLine.getStartY()-mainLine.getEndY())*Math.sin(teta);
            y0 = mainLine.getEndY() + (mainLine.getStartX()-mainLine.getEndX())*Math.sin(teta) + (mainLine.getStartY()-mainLine.getEndY())*Math.cos(teta);
            y1 = mainLine.getEndY() - (mainLine.getStartX()-mainLine.getEndX())*Math.sin(teta) + (mainLine.getStartY()-mainLine.getEndY())*Math.cos(teta);
            x1 = mainLine.getEndX() + (mainLine.getStartX()-mainLine.getEndX())*Math.cos(teta) + (mainLine.getStartY()-mainLine.getEndY())*Math.sin(teta);
            result.getChildren().addAll(tree_fractal(new Line(mainLine.getEndX(), mainLine.getEndY(), (x0+mainLine.getEndX())*0.5, (y0+mainLine.getEndY())*0.5), teta));
            result.getChildren().addAll(tree_fractal(new Line(mainLine.getEndX(), mainLine.getEndY(), (x1+mainLine.getEndX())*0.5, (y1+mainLine.getEndY())*0.5), teta));
        }
        return result;
    }

    private static Group Sierpiński_triangle() throws InterruptedException {
        Group result = new Group();
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(0.0, 0.0, 360.0, Math.tan(Math.PI / 3) * 360, 720.0, 0.0);
        result.getChildren().add(triangle);
        result.getChildren().addAll(Sierpiński_triangle(180.0, Math.tan(Math.PI / 3) * 180, 360.0, 0.0, 540.0, Math.tan(Math.PI / 3) * 180));
        return result;
    }

    private static Group Sierpiński_triangle(double x1, double y1, double x2, double y2, double x3, double y3) throws InterruptedException {
        Group result = new Group();
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(x1, y1, x2, y2, x3, y3);
        triangle.setFill(Color.WHITE);
        result.getChildren().add(triangle);
        double l = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        if (l > 1){
            result.getChildren().addAll(Sierpiński_triangle(1.5*x1 - x2*0.5 , (y1 + y2)*0.5 , x1, y2, (x1 + x2)*0.5, (y1 + y2)*0.5));
            double v = y1 + (x3 - x1) * 0.25 / Math.tan(Math.PI / 6);
            result.getChildren().addAll(Sierpiński_triangle((x1+x2)*0.5 , v, x2, y1, (x3 + x2)*0.5, v));
            result.getChildren().addAll(Sierpiński_triangle((x2+x3)*0.5, (y2+y3)*0.5, x3, y2, 1.5*x3 - x2*0.5, (y2+y3)*0.5));
        }
        return result;
    }


    private static Group Koch_Line(Line mainLine) throws InterruptedException {
        Group result = new Group();
        double l = Math.sqrt(Math.pow(mainLine.getEndX()-mainLine.getStartX(), 2) + Math.pow(mainLine.getEndY()-mainLine.getStartY(), 2));
        if (l < 2){
            result.getChildren().add(mainLine);
        }else{
            double x0, x1, y0, y1, x2 , y2;
            x0 = (mainLine.getStartX()*2 + mainLine.getEndX())/3;
            y0 = (mainLine.getStartY()*2 + mainLine.getEndY())/3;
            x1 = (mainLine.getStartX() + 2*mainLine.getEndX())/3;
            y1 = (mainLine.getStartY() + 2*mainLine.getEndY())/3;
            x2 = (x1-x0)*Math.cos(Math.PI/3) - (y1-y0)*Math.sin(Math.PI/3);
            y2 = (x1-x0)*Math.sin(Math.PI/3) + (y1-y0)*Math.cos(Math.PI/3);
            result.getChildren().addAll(Koch_Line(new Line(mainLine.getStartX(), mainLine.getStartY(),x0, y0)));
            result.getChildren().addAll(Koch_Line(new Line(x0, y0, x2 + x0, y0 + y2)));
            result.getChildren().addAll(Koch_Line(new Line(x2 + x0, y0 + y2 , x1, y1)));
            result.getChildren().addAll(Koch_Line(new Line(x1, y1, mainLine.getEndX(), mainLine.getEndY())));
        }
        return result;
    }

    /**
     * @param x
     * @param y
     * @param x2
     * @param y2
     * @return
     * Method return the same result as 'tree_fractal' when teta = PI/2
     */
    private static Group lineFractal(double x, double y, double x2, double y2){
        Group result = new Group();
        Line kar = new Line(x, y, x2, y2);
        result.getChildren().add(kar);
        if (x2 - x + y2 - y > 10){
            if (x == x2) {
                result.getChildren().addAll(lineFractal(x - (y2 - y)*0.35, y, x + (y2 - y)*0.35, y));
                result.getChildren().addAll(lineFractal(x - (y2 - y)*0.35, y2, x + (y2 - y)*0.35, y2));
            }else{
                result.getChildren().addAll(lineFractal(x, y - (x2 - x)*0.35, x,y + (x2 - x)*0.35));
                result.getChildren().addAll(lineFractal(x2, y - (x2 - x)*0.35, x2, y + (x2 - x)*0.35));
            }
        }
        return result;
    }

    private static Group Cantor_set(double x, double y, double l){
        Group result = new Group();
        if (l > 1){
            result.getChildren().add(new Line(x,y,x+l,y));
            result.getChildren().addAll(Cantor_set(x, y + 20,l/3.0));
            result.getChildren().addAll(Cantor_set(x + l*2/3.0, y + 20,l/3.0));
        }
        return result;
    }

    private static Group circleFractal(double x, double y, double r){
        Group result = new Group();
        Ellipse kar = new Ellipse(x, y, r, r);
        kar.setFill(Color.TRANSPARENT);
        kar.setStroke(Color.BLACK);
        result.getChildren().add(kar);
        if (r > 10) {
            result.getChildren().addAll(circleFractal(x - r*0.5, y, r / 2.0));
            result.getChildren().addAll(circleFractal(x + r*0.5, y, r / 2.0));
            result.getChildren().addAll(circleFractal(x,y - r*0.5, r / 2.0));
            result.getChildren().addAll(circleFractal(x,y + r*0.5, r / 2.0));
        }
        return result;
    }
}
