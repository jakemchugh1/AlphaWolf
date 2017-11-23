package collisions;

import org.lwjgl.util.vector.Vector3f;

public class CollisionBall implements Collision{
	//Current min and max values
	private float magnitude;
	
	private boolean isCustom;
	
	private Vector3f centerOfMass;
	
	public CollisionBall(float magnitude, Vector3f position){
		centerOfMass = position;
		this.magnitude = magnitude;
		
		isCustom = true;
	}
	
	public void updatePosition(Vector3f position){
		centerOfMass = position;
	}
	
	public boolean checkColliding(float xIn, float yIn,float zIn){
		
		if(Math.sqrt(Math.pow(xIn - centerOfMass.x,2) + Math.pow(yIn - centerOfMass.y,2) + Math.pow(zIn - centerOfMass.z,2))<=magnitude){
			return true;
		}else return false;
	}

	public float getxMin() {
		return centerOfMass.x - magnitude;
	}

	public float getxMax() {
		return centerOfMass.x + magnitude;
	}

	public float getyMin() {
		return centerOfMass.y - magnitude;
	}

	public float getyMax() {
		return centerOfMass.y + magnitude;
	}

	public float getzMin() {
		return centerOfMass.z - magnitude;
	}

	public float getzMax() {
		return centerOfMass.z + magnitude;
	}


	public Vector3f getCenterOfMass() {
		return centerOfMass;
	}

	public float getxMinMagnitude() {
		return magnitude;
	}

	public float getxMaxMagnitude() {
		return magnitude;
	}

	public float getyMinMagnitude() {
		return magnitude;
	}

	public float getyMaxMagnitude() {
		return magnitude;
	}

	public float getzMinMagnitude() {
		return magnitude;
	}

	public float getzMaxMagnitude() {
		return magnitude;
	}

	public boolean isCustom() {
		return isCustom;
	}
	
	
}
