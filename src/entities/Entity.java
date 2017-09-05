package entities;

import java.util.ArrayList;
import java.util.List;

import models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

import terrain.Terrain;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	public static boolean[][][] collisionsQuad = new boolean[600][120][600];
	//public static boolean[][][] collisionsQuad2 = new boolean[600][100][600];
	//public static boolean[][][] collisionsQuad3 = new boolean[600][100][600];
	//public static boolean[][][] collisionsQuad4 = new boolean[600][100][600];
	
	private int textureIndex = 0;
	
	//private List<CollisionBox> collisions= new ArrayList<CollisionBox>();
	
	public Entity(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		
	}
	public Entity(TexturedModel model,int index, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super();
		this.textureIndex = index;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public float getTextureXOffset(){
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	public float getTextureYOffset(){
		int row= textureIndex / model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}
	public void increasePosition(float dx, float dy, float dz){
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
	
	
	public void setCollisions(int length, int height, int width){
		
		for(int i = (int) (position.x-(length)); i <= (int)(position.x+(length)); i++){
			for(int j = (int) (position.y)+20; j <= (int)(position.y+(height)+20);j++){
				for (int k = (int) (position.z-(width)); k <=(int)(position.z+(width)); k++){
					collisionsQuad[i][j][k] = true;
					//collisionsQuad2[i][j][k] = true;
					//collisionsQuad3[i][j][k] = true;
					//collisionsQuad4[i][j][k] = true;
				}
			}
			
		}
		
	}
	
	

}
