package collisions;

import org.lwjgl.util.vector.Vector3f;

public interface Collision {
	public void updatePosition(Vector3f position);
	public boolean checkColliding(float xIn, float yIn,float zIn);
	
	public float getxMin();
	public float getxMax();
	public float getyMin();
	public float getyMax();
	public float getzMin();
	public float getzMax();
	public Vector3f getCenterOfMass();
	public float getxMinMagnitude();
	public float getxMaxMagnitude();
	public float getyMinMagnitude();
	public float getyMaxMagnitude();
	public float getzMinMagnitude();
	public float getzMaxMagnitude();
	public boolean isCustom();
}
