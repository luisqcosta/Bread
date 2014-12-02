package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import math.geom3d.Point3D;
import math.geom3d.line.LineSegment3D;
import mesh3d.Constants;
import mesh3d.Model3D;
import mesh3d.Stli;
import mesh3d.Surface3D;
import mesh3d.Tri3D;

import org.junit.Test;

public class TriOverlap {

	@Test
	public void overlap() {
		Point3D p1 = new Point3D(0,10,1);
		Point3D p2 = new Point3D(0,0,1);
		Point3D p3 = new Point3D(5,0,1);
		Tri3D t1 = new Tri3D(p1,p2,p3);
		Point3D p4 = new Point3D(0,6,0);
		Point3D p5 = new Point3D(0,3,1);
		Point3D p6 = new Point3D(0,6,2);
		Tri3D t2 = new Tri3D(p4,p5,p6);
		Point3D[] ps = t1.overlap(t2);
		//t1 normal points up, t2 normal is [-1,0]
		//overlap should return points ordered along leftNormal x rightNormal, so should be oriented in -y dir
		//output should be 0,6,1 to 0,3,1
		Point3D pA = new Point3D(0,6,1);
		Point3D pB = new Point3D(0,3,1);
		assertTrue(pA.distance(ps[0])<Constants.tol);
		assertTrue(pB.distance(ps[1])<Constants.tol);
	}
	@Test
	public void contains(){
		Point3D p1 = new Point3D(0,10,1);
		Point3D p2 = new Point3D(0,0,1);
		Point3D p3 = new Point3D(5,0,1);
		Tri3D t1 = new Tri3D(p2,p1,p3);
		Point3D q1 = new Point3D(5,10,1);
		Point3D q2 = new Point3D(5,1e-14,1);
		Point3D q3 = new Point3D(-1e-14,3,1);
		Point3D q4 = new Point3D(2,3,1);
		assertTrue(t1.contains(p1));
		assertTrue(t1.contains(p2));
		assertTrue(t1.contains(p3));
		assertFalse(t1.contains(q1));
		assertFalse(t1.contains(q2));
		assertFalse(t1.contains(q3));
		assertTrue(t1.contains(q4));
	}
	@Test
	public void SegmentPlane(){
		Point3D p1 = new Point3D(0,10,1);
		Point3D p2 = new Point3D(0,0,1);
		Point3D p3 = new Point3D(5,0,1);
		Tri3D t1 = new Tri3D(p2,p1,p3);
		Point3D p4 = new Point3D(3,3,3);
		Point3D p5 = new Point3D(3,3,2);
		LineSegment3D l = new LineSegment3D(p4, p5);
		assertNull(Tri3D.SegmentPlaneIntersection(t1.plane(),l));
		assertNull(t1.SegmentIntersection(l));
	}
	@Test
	public void normalTest(){
		Point3D p1 = new Point3D(-1,-1,5);
		Point3D p2 = new Point3D(20,20,5);
		Point3D p3 = new Point3D(20,-1,5);
		Tri3D t1 = new Tri3D(p1,p2,p3);
		assertTrue(t1.normal().getZ()>0);
	}
	@Test public void MeshOverlap() throws NumberFormatException, IOException{
		int z = 4;
		Model3D m1 = Stli.importModel("model.stl", true);
		Point3D p1 = new Point3D(50,50,z);
		Point3D p2 = new Point3D(-10,-10,z);
		Point3D p3 = new Point3D(50,-10,z);
		Tri3D t1 = new Tri3D(p1,p2,p3);
		Point3D q1 = new Point3D(-10,-10,z);
		Point3D q2 = new Point3D(50,50,z);
		Point3D q3 = new Point3D(-10,50,z);
		Tri3D t2 = new Tri3D(q1,q2,q3);
		Surface3D m2 = new Surface3D(new Tri3D[]{t1,t2});
		LineSegment3D[] over = m2.overlap(m1);
		double sum = 0;
		for(LineSegment3D l:over){
			sum += Tri3D.length(l);
		}
		System.out.println(sum);
		assertTrue(Math.abs(sum-60)<Constants.tol);
	}
}
