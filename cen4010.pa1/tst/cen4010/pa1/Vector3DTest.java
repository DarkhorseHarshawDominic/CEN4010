package cen4010.pa1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector3DTest {

	@Test
	void testToString() {
		Vector3D X = new Vector3D(2,2,2);
		assertEquals("x = 2.0\ny = 2.0\nz = 2.0", X.toString());
		//fail("Not yet implemented");
	}
	@Test
	void testEquals() {
		Vector3D X = new Vector3D(1.6, 4.5, 23.75);
		Vector3D Y = new Vector3D(1.6, 4.5, 23.75);
		//self equality
		assertTrue(X.equals(Y));
		//fail("Not yet implemented");
	}
	@Test
	void testScale() {
		Vector3D X = new Vector3D(3.4, 3.14, 7.77);
		Vector3D newX = X.scale(5);
		assertTrue(newX.equals(X.scale(5)));
		//fail("Not yet implemented");
	}
	@Test
	void testAdd() {
		Vector3D X = new Vector3D(2.10, 55.3, 12.9);
		Vector3D Y = new Vector3D(9.5, 3.12, 62.6);
		Vector3D Z = new Vector3D(11.6, 58.42, 75.5);
		assertTrue(Z.equals(X.add(Y)));
		//fail("Not yet implemented");
	}
	@Test
	void testSubtract() {
		Vector3D X = new Vector3D(2.10, 55.3, 12.9);
		Vector3D Y = new Vector3D(9.5, 3.12, 62.6);
		Vector3D Z = new Vector3D(-7.4, 52.18, -49.7);
		assertTrue(Z.equals(X.subtract(Y)));
		//fail("Not yet implemented");
	}
	@Test
	void testNegate() {
		Vector3D X = new Vector3D(3.14, -3.14, 41.3);
		Vector3D Y = new Vector3D(-3.14, 3.14, -41.3);
		assertTrue(Y.equals(X.scale(-1)));
		//fail("Not yet implemented");
	}
	@Test
	void testDot() {
		Vector3D X = new Vector3D(21, 21, 21);
		Vector3D Y = new Vector3D(21, 21, 21);
		Vector3D Z = new Vector3D(441, 441, 441);
		assertTrue(1323 + 1 > X.dot(Y) && 1323 - 1 < X.dot(Y));
		//fail("Not yet implemented");
	}
	@Test
	void testMagnitude() {
		Vector3D X = new Vector3D(2, 4, 8);
		assertEquals(42, X.Magnitude());
		//fail("Not yet implemented");
	}

}
