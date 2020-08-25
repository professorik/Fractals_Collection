/*
 * professorik  2020.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;


public class Main extends Application {

    int i;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        Group mainGroup = new Group();
        ScrollPane scrollPane = new ScrollPane(pane);
        Scene scene = new Scene(scrollPane, 720, 720);
        primaryStage.setTitle("Fractals");
        primaryStage.setScene(scene);
        primaryStage.show();
        //mainGroup.getChildren().add(Sierpinski_triangle());
        //mainGroup.getChildren().add(Sierpinski_carpet(new Rectangle(300, 300, 180, 180)));
        mainGroup.getChildren().add(Pythagoras_Tree(new Rectangle(600, 600, 180, 180), 45, 1, 600, 600, 0));
        pane.getChildren().addAll(mainGroup);
        //mainGroup.getChildren().add(circleFractal(360, 360,  360));
        //mainGroup.getChildren().add(tree_fractal(new Line(360, 540, 360, 360), 7*Math.PI/9));
        //mainGroup.getChildren().add(Mandelbrot_set());
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Group Pythagoras_Tree(Rectangle mainRect, double theta, int i, double x, double y, double A){
        Group result = new Group();

        double w = mainRect.getWidth();
        double angle = Math.PI * theta / 180;
        double n_w = w/(2*Math.cos(angle));

        result.getChildren().addAll(mainRect);
        if (w > 5){
            double x2 = x - n_w * Math.sin((A + theta) * Math.PI / 180);
            double y2 = y - n_w * Math.cos((A + theta) * Math.PI / 180);
            Rotate rotate = new Rotate();
            rotate.setAngle(-A-theta);
            rotate.setPivotX(x);
            rotate.setPivotY(y);
            Rectangle temp = new Rectangle(x, y-n_w, n_w, n_w);
            temp.getTransforms().add(rotate);
            result.getChildren().addAll(Pythagoras_Tree(temp, theta, i+1, x2, y2, A+theta));

            double X = x + w*Math.cos(A * Math.PI / 180);
            double Y = y - w*Math.sin(A * Math.PI / 180);
            double x3 = X - Math.cos(angle + Math.PI*0.25);
            double y3 = Y - Math.sin(angle + Math.PI*0.25);
            Rotate rotate2 = new Rotate();
            rotate2.setAngle(theta-A);
            rotate2.setPivotX(x3);
            rotate2.setPivotY(y3);
            Rectangle temp2 = new Rectangle(x3-n_w, y3-n_w, n_w, n_w);
            temp2.getTransforms().add(rotate2);
            double phi = Math.PI * 0.25 + angle - A * Math.PI / 180;
            result.getChildren().addAll(Pythagoras_Tree(temp2, theta, i+1, x3 - Math.sqrt(2)*n_w*Math.cos(phi), y3 - Math.sqrt(2)*n_w*Math.sin(phi), A-theta));
        }
        return result;
    }

    private static Group Sierpinski_carpet(Rectangle mainRect) throws InterruptedException {
        Group result = new Group();
        result.getChildren().add(mainRect);
        double x = mainRect.getX(), y = mainRect.getY(), w = mainRect.getWidth();
        if (w > 1){
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x+w/3, y-2*w/3, w/3, w/3)));
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x+w/3, y+4*w/3, w/3, w/3)));
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x-2*w/3, y+w/3, w/3, w/3)));
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x+4*w/3, y+w/3, w/3, w/3)));
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x-2*w/3, y-2*w/3, w/3, w/3)));
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x+4*w/3, y+4*w/3, w/3, w/3)));
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x+4*w/3, y-2*w/3, w/3, w/3)));
            result.getChildren().addAll(Sierpinski_carpet(new Rectangle(x-2*w/3, y+4*w/3, w/3, w/3)));
        }
        return result;
    }

    private static Group Mandelbrot_set(){
        Canvas canvas = new Canvas(); canvas.setHeight(720.0); canvas.setWidth(1440);
        double width = canvas.getWidth(), height = canvas.getHeight();
        int zoom = 1440;
        int max = 100;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double c_re = (col - width/2.0)*4.0/zoom;
                double c_im = (row - height/2.0)*4.0/zoom;
                double x = 0, y = 0;
                int iteration = max;
                while (x*x+y*y <= 4 && iteration-- > 0) {
                    double x_new = x*x - y*y + c_re;
                    y = 2*x*y + c_im;
                    x = x_new;
                }
                BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
                img.setRGB(0, 0, iteration | (iteration << 4));
                java.awt.Color c = new java.awt.Color(img.getRGB(0, 0));
                canvas.getGraphicsContext2D().getPixelWriter().setColor(col, row, Color.rgb(c.getRed(), c.getGreen(), c.getBlue()));
            }
        }
        Group temp = new Group();
        temp.getChildren().add(canvas);
        return temp;
    }

    private static Group tree_fractal(Line mainLine, double theta) throws InterruptedException {
        Group result = new Group();
        double l = Math.sqrt(Math.pow(mainLine.getEndX()-mainLine.getStartX(), 2) + Math.pow(mainLine.getEndY()-mainLine.getStartY(), 2));
        result.getChildren().add(mainLine);
        if (l > 1){
            double x0, y0, y1, x1;
            x0 = mainLine.getEndX() + (mainLine.getStartX()-mainLine.getEndX())*Math.cos(theta) - (mainLine.getStartY()-mainLine.getEndY())*Math.sin(theta);
            y0 = mainLine.getEndY() + (mainLine.getStartX()-mainLine.getEndX())*Math.sin(theta) + (mainLine.getStartY()-mainLine.getEndY())*Math.cos(theta);
            y1 = mainLine.getEndY() - (mainLine.getStartX()-mainLine.getEndX())*Math.sin(theta) + (mainLine.getStartY()-mainLine.getEndY())*Math.cos(theta);
            x1 = mainLine.getEndX() + (mainLine.getStartX()-mainLine.getEndX())*Math.cos(theta) + (mainLine.getStartY()-mainLine.getEndY())*Math.sin(theta);
            result.getChildren().addAll(tree_fractal(new Line(mainLine.getEndX(), mainLine.getEndY(), (x0+mainLine.getEndX())*0.5, (y0+mainLine.getEndY())*0.5), theta));
            result.getChildren().addAll(tree_fractal(new Line(mainLine.getEndX(), mainLine.getEndY(), (x1+mainLine.getEndX())*0.5, (y1+mainLine.getEndY())*0.5), theta));
        }
        return result;
    }

    private static Group Sierpinski_triangle() throws InterruptedException {
        Group result = new Group();
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(0.0, 0.0, 360.0, Math.tan(Math.PI / 3) * 360, 720.0, 0.0);
        result.getChildren().add(triangle);
        result.getChildren().addAll(Sierpinski_triangle(180.0, Math.tan(Math.PI / 3) * 180, 360.0, 0.0, 540.0, Math.tan(Math.PI / 3) * 180));
        return result;
    }

    private static Group Sierpinski_triangle(double x1, double y1, double x2, double y2, double x3, double y3) throws InterruptedException {
        Group result = new Group();
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(x1, y1, x2, y2, x3, y3);
        triangle.setFill(Color.WHITE);
        result.getChildren().add(triangle);
        double l = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        if (l > 1){
            result.getChildren().addAll(Sierpinski_triangle(1.5*x1 - x2*0.5 , (y1 + y2)*0.5 , x1, y2, (x1 + x2)*0.5, (y1 + y2)*0.5));
            double v = y1 + (x3 - x1) * 0.25 / Math.tan(Math.PI / 6);
            result.getChildren().addAll(Sierpinski_triangle((x1+x2)*0.5 , v, x2, y1, (x3 + x2)*0.5, v));
            result.getChildren().addAll(Sierpinski_triangle((x2+x3)*0.5, (y2+y3)*0.5, x3, y2, 1.5*x3 - x2*0.5, (y2+y3)*0.5));
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