/*
 * Copyright 2011 Cologne University of Applied Sciences Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.amcgala.framework.shape.shape3d;

import org.amcgala.framework.math.Vector3d;
import org.amcgala.framework.renderer.Renderer;
import org.amcgala.framework.shape.AbstractShape;
import org.amcgala.framework.shape.Line;
import org.amcgala.framework.shape.util.CompositeShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Draws a 3D Box.
 *
 * @author Robert Giacinto
 */
public class Box extends AbstractShape {

    private CompositeShape lines;
    private Vector3d position;
    private double width;
    private double height;
    private double depth;

    /**
     * Constructor.
     *
     * @param position
     * @param width
     * @param height
     * @param depth
     */
    public Box(Vector3d position, double width, double height, double depth) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.depth = depth;
        lines = new CompositeShape();
        this.calculate();
    }

    /*
     * Updates the Box at runtime.
     */
    private void calculate() {
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y, position.z), Vector3d.createVector3d(position.x + width, position.y, position.z)));
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y, position.z), Vector3d.createVector3d(position.x, position.y + height, position.z)));
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y, position.z), Vector3d.createVector3d(position.x, position.y, position.z - depth)));
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y + height, position.z), Vector3d.createVector3d(position.x + width, position.y + height, position.z)));
        lines.add(new Line(Vector3d.createVector3d(position.x + width, position.y, position.z), Vector3d.createVector3d(position.x + width, position.y + height, position.z)));
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y + height, position.z), Vector3d.createVector3d(position.x, position.y + height, position.z - depth)));
        lines.add(new Line(Vector3d.createVector3d(position.x + width, position.y + height, position.z), Vector3d.createVector3d(position.x + width, position.y + height, position.z - depth)));
        lines.add(new Line(Vector3d.createVector3d(position.x + width, position.y, position.z), Vector3d.createVector3d(position.x + width, position.y, position.z - depth)));
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y, position.z - depth), Vector3d.createVector3d(position.x + width, position.y, position.z - depth)));
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y, position.z - depth), Vector3d.createVector3d(position.x, position.y + height, position.z - depth)));
        lines.add(new Line(Vector3d.createVector3d(position.x, position.y + height, position.z - depth), Vector3d.createVector3d(position.x + width, position.y + height, position.z - depth)));
        lines.add(new Line(Vector3d.createVector3d(position.x + width, position.y, position.z - depth), Vector3d.createVector3d(position.x + width, position.y + height, position.z - depth)));
    }

    /**
     * Returns the Position in form of a 3D Vector.
     *
     * @return Vector3d
     */
    public Vector3d getPosition() {
        return this.position;
    }

    /**
     * Sets the Box to the specified location.
     *
     * @param position
     */
    public void setPosition(Vector3d position) {
        this.position = position;
        lines = new CompositeShape();
        calculate();
    }

    /**
     * Returns the width of the box.
     *
     * @return
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Sets the width of the box.
     *
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
        this.calculate();
    }

    /**
     * Returns the height of the box.
     *
     * @return
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the height of the box.
     *
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
        this.calculate();
    }

    /**
     * Returns the depth of the box.
     *
     * @return
     */
    public double getDepth() {
        return this.depth;
    }

    /**
     * Sets the depth of the box.
     *
     * @param depth
     */
    public void setDepth(double depth) {
        this.depth = depth;
        this.calculate();
    }

    /**
     * Rendermethod.
     */
    @Override
    public void render(Renderer renderer) {
        lines.setColor(getColor());
        lines.render(renderer);
    }

    /**
     * Overwrites toString of the Objectclass.
     */
    public String toString() {
        String ausgabe = "";
        ausgabe += "Box3d { x = ";
        ausgabe += this.position.x;

        ausgabe += "; y = ";
        ausgabe += this.position.y;

        ausgabe += "; z = ";
        ausgabe += this.position.z;

        ausgabe += "; width = ";
        ausgabe += this.width;

        ausgabe += "; height = ";
        ausgabe += this.height;

        ausgabe += "; depth = ";
        ausgabe += this.depth + " };";

        return ausgabe;
    }

    private static final Logger log = LoggerFactory.getLogger(Box.class.getName());
}
