package main;

import io.Stli;

import java.io.FileNotFoundException;

import math.geom3d.Vector3D;
import mesh3d.Model3D;
import mesh3d.SimplePlane;
import mesh3d.Surface3D;

public class Run {

	public static void main(String[] args) {
		Model3D part = null;
		Surface3D surface = null;		
		Slicer s = null;
		try {
//			part = Stli.importModel("ASTM D638-10-1.stl", false);
//			surface = Stli.importSurface("vastm.stl", false);
			part = Stli.importModel("./Prints/wave.stl", false);
			surface = Stli.importSurface("./Prints/wavesurface.stl", false);
//			surface = SimplePlane.MakePlane(-10, -10, 30, 30, 0);
//			part = Stli.importModel("./Prints/vtop.stl", true);
//			surface = Stli.importSurface("./Prints/v.stl", true);
//			surface = Stli.importSurface("slant.stl", false);
//			surface = Stli.importSurface("v2.stl", true);
//			surface = SimplePlane.MakePlane(-5, -5, 50, 50, 0);
//			surface.move(new Vector3D(1.5,0,0));
			s = new Slicer(part, surface, "config1.txt");
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		s.slice("wave.g");
	}
}
