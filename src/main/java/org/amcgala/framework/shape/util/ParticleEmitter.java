package org.amcgala.framework.shape.util;

import org.amcgala.framework.animation.Updatable;
import org.amcgala.framework.math.Vector3d;
import org.amcgala.framework.renderer.Renderer;
import org.amcgala.framework.scenegraph.transform.RotationZ;
import org.amcgala.framework.shape.AbstractShape;
import org.amcgala.framework.shape.Line;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Emitter Klasse die ParticleContainer mit den gegebenen Eigenschaften ausgibt.
 * Emitter kann dis/enabled werden.
 * TODO: Sollte auf 3D erweitert werden.
 *
 * @author Steffen Troester
 */
public class ParticleEmitter extends AbstractShape implements Updatable {

    private boolean enabled = true;
    private double width, height;
    private double x, y, z; // Mittepunkt
    private Vector3d direction = Vector3d.createVector3d(0, 1, 0);
    private final List<Particle> particles = new CopyOnWriteArrayList<Particle>();
    private final List<ParticleManipulation> particleManipulations = new CopyOnWriteArrayList<ParticleManipulation>();
    private RotationZ rectrotation = new RotationZ(Math.PI / 2);
    private boolean visible;
    // Emitting Settings
    private int timeIntervalMs = 100;
    private double particleSpeed = 1.0;
    // Temp Settings
    private long timeStamp;
    private Random r = new Random();

    @Override
    public void update() {
        super.update();

        if (timeStamp == 0 || !enabled) {
            setTimeStamp();
        } else {
            // Neuen Partikel anzeigen
            if (getTimeStampDifference() > timeIntervalMs) {
                setTimeStamp();
                // rotate direction
                Vector3d rot = rectrotation.getTransformMatrix().times(direction.copy().toMatrix()).toVector3d();
                // scale
                rot.times(r.nextDouble() * width);
                // translate
                rot.x += x;
                rot.y += y;
                // addNode
                particles.add(new Particle(particleSpeed, direction.copy(),
                        rot.x, rot.y));
            }
        }
        // Partikel updaten und gegebenenfalls manipulieren
        for (Particle p : particles) {
            p.update();
            if (p.getLife() < 0) {
                particles.remove(p);
            } else {
                for (ParticleManipulation pm : particleManipulations) {
                    if (pm.fitInRange(p.getX(), p.getY())) {
                        pm.manipulate(p);
                    }
                }
            }
        }
    }

    public void setTimeStamp() {
        timeStamp = System.nanoTime() / 1000000;
    }

    public long getTimeStampDifference() {
        return (System.nanoTime() / 1000000) - timeStamp;
    }

    /**
     * Konstruktor für einen Emitter in einer 2D Ebene.
     *
     * @param width die Breite des Emitters
     * @param x x-Koordinate der Position
     * @param y y-Koordinate der Position
     * @param direction die Richtung des Emitters
     */
    public ParticleEmitter(double width, double x, double y, Vector3d direction) {
        this.width = width;
        this.x = x;
        this.y = y;
        this.direction = direction.normalize();
    }

    @Override
    public void render(Renderer renderer) {
        for (Particle p : particles) {
            p.render(renderer);
        }
        for (ParticleManipulation pm : particleManipulations) {
            pm.render(renderer);
        }
        // display emitterelement 2d
        if (isVisible()) {
            Vector3d scale = direction.copy().normalize().times(width);
            // rotate
            Vector3d rotateScale = rectrotation.getTransformMatrix().times(scale.toMatrix()).toVector3d();
            // translate
            rotateScale.x += x;
            rotateScale.y += y;
            // display
            new Line(Vector3d.createVector3d(x, y, 1), rotateScale).render(renderer);
        }
    }


    public void addParticleManipulation(ParticleManipulation p) {
        particleManipulations.add(p);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector3d getDirection() {
        return direction;
    }

    public Vector3d getScale() {
        Vector3d scale = direction.copy().times(width);
        return scale;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setDirection(Vector3d direction) {
        this.direction = direction;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setTimeIntervalMs(int timeIntervalMs) {
        this.timeIntervalMs = timeIntervalMs;
    }

    public void setParticleSpeed(double particleSpeed) {
        this.particleSpeed = particleSpeed;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
