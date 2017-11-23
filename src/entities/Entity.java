package entities;

import java.util.HashSet;
import java.util.Set;

import models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

import collisions.Collision;
import collisions.CollisionBox;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	private boolean hasCollisions;
	
	private Collision collisions;
	
	private int textureIndex = 0;
	
	
	
	
	public Entity(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale, boolean hasCollisions, Collision collisions) {
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		
		if(hasCollisions){
			this.collisions = collisions;
			updateCollisions();
		}
		
	}
	public Entity(TexturedModel model,int index, Vector3f position, float rotX,
			float rotY, float rotZ, float scale, boolean hasCollisions, Collision Collisions) {
		super();
		this.textureIndex = index;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.hasCollisions = hasCollisions;
		
		if(hasCollisions){
			this.collisions = collisions;
			updateCollisions();
		}
		
	}
	public boolean checkCollisions(CollisionBox inputCollision){
		if(hasCollisions == false) return false;
		else{
			if(collisions == null) return false;
			else if(inputCollision.checkColliding(collisions.getxMin(), collisions.getyMin(), collisions.getzMin()))return true;
			else if(inputCollision.checkColliding(collisions.getxMax(), collisions.getyMax(), collisions.getzMax()))return true;
			else return false;
		}
	}
	
	public float getTextureXOffset(){
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	public float getTextureYOffset(){
		int row= textureIndex / model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}
	public void increasePosition(double dx, double dy, double dz){
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz){
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	public TexturedModel getModel() {
		return model;
	}
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public float getRotX() {
		return rotX;
	}
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	public float getRotY() {
		return rotY;
	}
	public void setRoty(float rotY) {
		this.rotY = rotY;
	}
	public float getRotZ() {
		return rotZ;
	}
	public void setRotz(float rotZ) {
		this.rotZ = rotZ;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public Collision getCollisions() {
		return collisions;
	}
	public void updateCollisions(){
		collisions.updatePosition(position);
	}
	
	
	
	

}
