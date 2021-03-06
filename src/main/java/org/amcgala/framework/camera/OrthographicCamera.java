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
package org.amcgala.framework.camera;

import org.amcgala.framework.math.Matrix;
import org.amcgala.framework.math.Vector3d;
import org.amcgala.framework.renderer.Pixel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementierung einer orthographischen Projektion. Es gehen jegliche
 * Tiefeninformationen verloren. Die Kamera ist besonders dafür geeignet,
 * 2D-Projektionen oder 2D GUI-Elemente zu realisieren, die über einer
 * perspektivischen Szene zu sehen sind.
 *
 * @author Robert Giacinto
 */
public final class OrthographicCamera extends AbstractCamera {

    /**
     * Erzeugt eine neue Kamera an einer Position mit einem bestimmten
     * Blickpunkt.
     *
     * @param vup       Das Oben der Kamera
     * @param position  Die Position der Kamera
     * @param direction Der Punkt, zu dem die Kamera blickt
     */
    public OrthographicCamera(Vector3d vup, Vector3d position, Vector3d direction) {
        this.up = vup;
        this.location = position;
        this.direction = direction;

        update();
    }

    @Override
    public Matrix getProjectionMatrix() {
        return projectionMatrix;
    }

    @Override
    public void update() {
        this.n = direction.sub(location).times(-1);
        this.u = up.cross(n).normalize();
        this.v = n.cross(u).normalize();

        double[][] vdValues = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1}
        };

        Matrix vd = Matrix.constructWithCopy(vdValues);

        Vector3d d = Vector3d.createVector3d(location.dot(u), location.dot(v), location.dot(n));

        double[][] viewValues = {
                {u.x, u.y, u.z, d.x},
                {v.x, v.y, v.z, d.y},
                {n.x, n.y, n.z, d.z},
                {0, 0, 0, 1}
        };
        Matrix kt = Matrix.constructWithCopy(viewValues);
        projectionMatrix = vd.times(kt);
    }

    @Override
    public CVPoint getClippingSpaceCoordinates(Vector3d vector3d) {
        Matrix point = projectionMatrix.times(vector3d.toMatrix());
        return new CVPoint(point.get(0, 0) / point.get(3, 0), point.get(1, 0) / point.get(3, 0),1);
    }

    @Override
    public Pixel getImageSpaceCoordinates(Vector3d vector3d) {
        Matrix point = projectionMatrix.times(vector3d.toMatrix());
        return new Pixel(point.get(0, 0) / point.get(3, 0), point.get(1, 0) / point.get(3, 0));
    }

    private static final Logger log = LoggerFactory.getLogger(OrthographicCamera.class);
}
