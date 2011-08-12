package cga.framework.shape;

import cga.framework.camera.CVPoint;
import cga.framework.camera.Camera;
import cga.framework.math.Matrix;
import cga.framework.math.Vector3d;
import cga.framework.renderer.Pixel;
import cga.framework.renderer.Renderer;

/**
 * Ein Punkt in einer Ebene für die Darstellung von 2d Geometrien.
 * <p/>
 * @author Robert Giacinto
 */
public class Point2d extends Shape {

    public double x;
    public double y;

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Matrix transformation, Camera camera, Renderer renderer) {
        Vector3d point = new Vector3d(x, y, -1).transform(transformation);

        Pixel pixel = camera.getImageSpaceCoordinates(point);
        renderer.putPixel(pixel);
    }

    @Override
    public String toString() {
        return "Point2d{" + "x=" + x + ", y=" + y + '}';
    }
}