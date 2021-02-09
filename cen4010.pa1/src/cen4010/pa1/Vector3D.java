package cen4010.pa1;

public class Vector3D {

	private double x, y, z;
	
	Vector3D(double horth, double verth, double zorth){
		x = horth;
		y = verth;
		z = zorth;
		
	}
		public String toString() {
			return "x = " +  x + "\n" + "y = " + y + "\n" + "z = " + z;
			//fail("Not yet implemented");
		}
	
		boolean equals(Vector3D X) {
			return (X.x - 1.0 < x && x < X.x + 1.0) && (X.y - 1.0 < y && y < X.y + 1.0) && (X.z - 1.0 < z && z < X.z + 1.0);			
		}
		
		Vector3D scale(int num) {
			return new Vector3D(x*num, y*num, z*num);
		}
		
		Vector3D add(Vector3D X) {
			return new Vector3D(x + X.x , y + X.y, z + X.z);
		}
		
		Vector3D subtract(Vector3D X) {
			return new Vector3D(x - X.x , y - X.y, z - X.z);
		}
		
		double dot(Vector3D X) {
			return x * X.x + y * X.y + z * X.z;
		}
		
		double Magnitude() {
			return this.dot(this) * 0.5;
		}
}
