package collisions;

import org.lwjgl.util.vector.Vector3f;


public class CollisionBox implements Collision{
	//Current min and max values
	private float xMin;
	private float xMax;
	private float yMin;
	private float yMax;
	private float zMin;
	private float zMax;
	private float size;
	//min and max values for updating custom boxes
	private float xMinMagnitude;
	private float xMaxMagnitude;
	private float yMinMagnitude;
	private float yMaxMagnitude;
	private float zMinMagnitude;
	private float zMaxMagnitude;
	
	private boolean isCustom;
	
	private Vector3f centerOfMass;
	
	public CollisionBox(float size, Vector3f position){
		this.size = size;
		centerOfMass = position;
		xMin = position.x-size;
		xMax = position.x+size;
		yMin = position.y-size;
		yMax = position.y+size;
		zMin = position.z-size;
		zMax = position.z+size;
		
		
		isCustom = false;
	}
	
	public CollisionBox(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax, Vector3f position){
		xMinMagnitude = xMin;
		xMaxMagnitude = xMin;
		yMinMagnitude = yMin;
		yMaxMagnitude = yMin;
		zMinMagnitude = zMin;
		zMaxMagnitude = zMin;
		centerOfMass = position;
		xMin = position.x-xMinMagnitude;
		xMax = position.x+xMaxMagnitude;
		yMin = position.y-yMinMagnitude;
		yMax = position.y+yMaxMagnitude;
		zMin = position.z-zMinMagnitude;
		zMax = position.z+zMaxMagnitude;
		
		isCustom = true;
	}
	
	public void updatePosition(Vector3f position){
		centerOfMass = position;
		if(isCustom){
			xMin = position.x-xMinMagnitude;
			xMax = position.x+xMaxMagnitude;
			yMin = position.y-yMinMagnitude;
			yMax = position.y+yMaxMagnitude;
			zMin = position.z-zMinMagnitude;
			zMax = position.z+zMaxMagnitude;
		}else{
			xMin = position.x-size;
			xMax = position.x+size;
			yMin = position.y-size;
			yMax = position.y+size;
			zMin = position.z-size;
			zMax = position.z+size;
		}
	}
	
	public boolean checkColliding(float xIn, float yIn,float zIn){
		if(xMin<xIn && xIn<xMax && yMin<yIn && yIn<yMax && zMin<zIn && zIn<zMax){
			return true;
		}
		else return false;
	}

	public float getxMin() {
		return xMin;
	}

	public float getxMax() {
		return xMax;
	}

	public float getyMin() {
		return yMin;
	}

	public float getyMax() {
		return yMax;
	}

	public float getzMin() {
		return zMin;
	}

	public float getzMax() {
		return zMax;
	}


	public Vector3f getCenterOfMass() {
		return centerOfMass;
	}
	public float getxMinMagnitude() {
		return xMinMagnitude;
	}

	public float getxMaxMagnitude() {
		return xMaxMagnitude;
	}

	public float getyMinMagnitude() {
		return yMinMagnitude;
	}

	public float getyMaxMagnitude() {
		return yMaxMagnitude;
	}

	public float getzMinMagnitude() {
		return zMinMagnitude;
	}

	public float getzMaxMagnitude() {
		return zMaxMagnitude;
	}

	public boolean isCustom() {
		return isCustom;
	}
	
	
}
