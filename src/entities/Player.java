package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import collisions.Collision;
import collisions.CollisionBox;
import renderEngine.DisplayManager;
import terrain.Terrain;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 60;
	private static final float TURN_SPEED = 160;
	
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	private static final float TERRAIN_HEIGHT = 0;
	private static final float DISTANCE_FROM_BORDER = 20;
	
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private float terrainHeight;
	private float dx;
	private float dz;
	
	private boolean isInAir = false;
	private boolean onTop = false;
	private boolean hasCollisions = true;
	private boolean isColliding = false;

	public Player(TexturedModel model, Vector3f position, float rotx,
			float roty, float rotz, float scale, boolean hasCollisions, Collision collisions) {
		super(model, position, rotx, roty, rotz, scale,hasCollisions, collisions);
	}
	
	public void move(Terrain terrain){
		checkInputs();
		super.increaseRotation(0,  currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.updateCollisions();
		super.increasePosition(dx, 0f, dz);
		mapBorders();
		
		upwardsSpeed += GRAVITY *DisplayManager.getFrameTimeSeconds();
		
		if(!onTop){
			super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		}else isInAir = false;
		terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y < terrainHeight){
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump(){
		if(!isInAir){
			this.upwardsSpeed = JUMP_POWER;
			Vector3f tempPosition = super.getPosition();
			tempPosition.y = tempPosition.y + 0.5f;
			super.setPosition(tempPosition);
			isInAir = true;
		}
	}
	
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed = RUN_SPEED;	
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed = -RUN_SPEED;
		}else{
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed = -TURN_SPEED;	
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();	
		}
	}
	private void mapBorders(){
		
		//Borders of terrain collision detection
		if(super.getPosition().x<+DISTANCE_FROM_BORDER) {
			super.increasePosition(-dx, 0f, 0f);
			System.out.println("Colliding! X Negative");
		}
		if(super.getPosition().z<DISTANCE_FROM_BORDER) {
			super.increasePosition(0f, 0f, -dz);
			System.out.println("Colliding! Z Negative");
		}
		if(super.getPosition().x>6400-DISTANCE_FROM_BORDER) {
			super.increasePosition(-dx, 0f, 0f);
			System.out.println("Colliding! X Positive");
		}
		if(super.getPosition().z>6400-DISTANCE_FROM_BORDER) {
			super.increasePosition(0f, 0f, -dz);
			System.out.println("Colliding! Z Positive");
		}
	}
	public boolean checkCollisions(Collision collision){
		
		if(!hasCollisions) {
			System.out.println("no collisions");
			return false;
		}
		else{
			
			isColliding = false;
			if(collision.checkColliding(super.getCollisions().getxMin(), super.getCollisions().getyMin(), super.getCollisions().getzMin())){
				isColliding = true;
			}else if(collision.checkColliding(super.getCollisions().getxMax(), super.getCollisions().getyMax(), super.getCollisions().getzMax())){
				isColliding = true;
			}
			if(isColliding){
				repel(collision);
			}
			
			return isColliding;
		}
	}
	public void repel(Collision collision){
		double[] distanceFromObject = new double[3];
		//calculating the distance from the center of mass of one object to another
		distanceFromObject[0] = super.getCollisions().getCenterOfMass().x-collision.getCenterOfMass().x;
		distanceFromObject[1] = super.getCollisions().getCenterOfMass().y-collision.getCenterOfMass().y;
		distanceFromObject[2] = super.getCollisions().getCenterOfMass().z-collision.getCenterOfMass().z;
		//finding the magnitude(actual distance) between the two center of masses
		double magnitude = Math.sqrt(Math.pow(distanceFromObject[0],2)+Math.pow(distanceFromObject[1],2)+Math.pow(distanceFromObject[2],2));
		//turns distanceFromObject array into unit vector
		//x
		distanceFromObject[0] = (float) (distanceFromObject[0] / magnitude);
		//y
		distanceFromObject[1] = (float) (distanceFromObject[1] / magnitude);
		//z
		distanceFromObject[2] = (float) (distanceFromObject[2] / magnitude);
		//moving player along the unit vector
		double repelStrength = 0.9999f;
		if(collision.getyMax()-1 < super.getCollisions().getyMin()){
			Vector3f tempPosition = super.getPosition();
			tempPosition.y = super.getCollisions().getyMinMagnitude() + collision.getyMax()-0.01f;
			super.setPosition(tempPosition);
			isInAir = false;
			upwardsSpeed = 0;
		}else {
			super.increasePosition(distanceFromObject[0]*repelStrength, 0, distanceFromObject[2]*repelStrength);
			onTop = false;
		}
	}

}
