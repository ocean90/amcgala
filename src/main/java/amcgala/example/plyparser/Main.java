package amcgala.example.plyparser;

import java.awt.event.MouseEvent;
import java.io.InputStream;

import com.google.common.eventbus.Subscribe;
import amcgala.Framework;
import amcgala.framework.camera.SimplePerspectiveCamera;
import amcgala.framework.event.InputHandler;
import amcgala.framework.math.Vector3d;
import amcgala.framework.shape.Polygon;
import amcgala.framework.shape.util.PLYPolygonParser;

public class Main extends Framework implements InputHandler {
	private Vector3d direction = new Vector3d(0.3, 0.5, 0.2);
	private Vector3d up = Vector3d.UNIT_Y;
	private Vector3d position = new Vector3d(0, 0, 0);
	private final int DESTINATION = 1900;

	public Main(int width, int height) {
		super(width, height);
		setCamera(new SimplePerspectiveCamera(up, position, direction,
				DESTINATION));
		registerInputEventHandler(this);
	}

	@Override
	public void initGraph() {
		InputStream inputStream = ClassLoader
				.getSystemResourceAsStream("amcgala/exsample/plyparser/icosphere.ply");
		try {
			for (Polygon p : PLYPolygonParser.parseAsPolygonList(inputStream,
					150)) {
				add(p);
			}

		} catch (Exception e) {
			System.out
					.println("Fehler beim Laden der Polygone! Bitte ueberpruefen Sie die Exporteigenschaften!");
		}

	}

	@Subscribe
	public void handleMouse(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_MOVED) {
			direction.x = (double) e.getX() / (double) getScreenWidth();
			direction.y = (double) e.getY() / (double) getScreenHeight();
			setCamera(new SimplePerspectiveCamera(up, position, direction,
					DESTINATION));
		}
	}

	public static void main(String[] args) {
		Main m = new Main(500, 500);
		m.start();
	}
}
