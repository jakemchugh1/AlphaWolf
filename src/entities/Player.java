package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import terrain.Terrain;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 100;
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
	private boolean colliding = false;

	public Player(TexturedModel model, Vector3f position, float rotx,
			float roty, float rotz, float scale) {
		super(model, position, rotx, roty, rotz, scale);
	}
	
	public void move(Terrain terrain){
		checkInputs();
		super.increaseRotation(0,  currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		/*if(collisionDetection()) {
			System.out.println("colliding!");
			super.increasePosition(-dx*1.02f, 0f, -dz*1.02f);
		}else super.increasePosition(dx, 0f, dz);
		if(collisionDetection()) System.out.println("Colliding!");*/
		
		super.increasePosition(dx, 0f, dz);
		collisionDetection();
		
		upwardsSpeed += GRAVITY *DisplayManager.getFrameTimeSeconds();
		if(super.collisionsQuad[(int) super.getPosition().x][(int) super.getPosition().y+19][(int) super.getPosition().z]==false){
			super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		}
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
			// isInAir = true;
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
	private void collisionDetection(){
		//Borders of objects collision detection
		if(super.collisionsQuad[(int) super.getPosition().x][(int) super.getPosition().y+20][(int) super.getPosition().z+1]) {
			super.increasePosition(-dx, 0f, 0f);
			colliding = true;
			System.out.println("Colliding! Object X Negative");
		}
		if(super.collisionsQuad[(int) super.getPosition().x][(int) super.getPosition().y+20][(int) super.getPosition().z-1]) {
			super.increasePosition(0f, 0f, -dz);
			colliding = true;
			System.out.println("Colliding! Object Z Negative");
		}
		if(super.collisionsQuad[(int) super.getPosition().x][(int) super.getPosition().y+20][(int) super.getPosition().z-1]) {
			super.increasePosition(-dx, 0f, 0f);
			colliding = true;
			System.out.println("Colliding! Object X Positive");
		}
		if(super.collisionsQuad[(int) super.getPosition().x][(int) super.getPosition().y+20][(int) super.getPosition().z+1]) {
			super.increasePosition(0f, 0f, -dz);
			colliding = true;
			System.out.println("Colliding! Object Z Positive");
		}	
		
		//super.collisionsQuad[(int) super.getPosition().x][(int) super.getPosition().y+20][(int) super.getPosition().z];
		
		
		//Borders of terrain collision detection
		if(super.getPosition().x<+DISTANCE_FROM_BORDER) {
			super.increasePosition(-dx, 0f, 0f);
			colliding = true;
			System.out.println("Colliding! X Negative");
		}
		if(super.getPosition().z<DISTANCE_FROM_BORDER) {
			super.increasePosition(0f, 0f, -dz);
			colliding = true;
			System.out.println("Colliding! Z Negative");
		}
		if(super.getPosition().x>3200-DISTANCE_FROM_BORDER) {
			super.increasePosition(-dx, 0f, 0f);
			colliding = true;
			System.out.println("Colliding! X Positive");
		}
		if(super.getPosition().z>3200-DISTANCE_FROM_BORDER) {
			super.increasePosition(0f, 0f, -dz);
			colliding = true;
			System.out.println("Colliding! Z Positive");
		}
	}

}
