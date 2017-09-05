package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		//OpenGL expects vertices to be defined counter clockwise by default
		
		ModelData data = OBJFileLoader.loadOBJ("pine");
		ModelData data1 = OBJFileLoader.loadOBJ("dragon");
		ModelData data2 = OBJFileLoader.loadOBJ("bunny");
		ModelData data3 = OBJFileLoader.loadOBJ("grassModel");
		ModelData data4 = OBJFileLoader.loadOBJ("fern");
		ModelData data5 = OBJFileLoader.loadOBJ("wolf");
		ModelData data6 = OBJFileLoader.loadOBJ("lowPolyTree");
		ModelData data7 = OBJFileLoader.loadOBJ("box");
		
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(),
				data.getNormals(), data.getIndices());
		RawModel dragonModel = loader.loadToVAO(data1.getVertices(), data1.getTextureCoords(),
				data1.getNormals(), data1.getIndices());
		RawModel bunnyModel = loader.loadToVAO(data2.getVertices(), data2.getTextureCoords(),
				data2.getNormals(), data2.getIndices());
		RawModel grassModel = loader.loadToVAO(data3.getVertices(), data3.getTextureCoords(),
				data3.getNormals(), data3.getIndices());
		RawModel fernModel = loader.loadToVAO(data4.getVertices(), data4.getTextureCoords(),
				data4.getNormals(), data4.getIndices());
		RawModel wolfModel = loader.loadToVAO(data5.getVertices(), data5.getTextureCoords(),
				data5.getNormals(), data5.getIndices());
		RawModel lowPolyTreeModel = loader.loadToVAO(data6.getVertices(), data6.getTextureCoords(),
				data6.getNormals(), data6.getIndices());
		RawModel boxModel = loader.loadToVAO(data7.getVertices(), data7.getTextureCoords(),
				data7.getNormals(), data7.getIndices());
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		ModelTexture polyTreeTextureAtlas = new ModelTexture(loader.loadTexture("lowPolyTree"));
		polyTreeTextureAtlas.setNumberOfRows(2);
		
		
		
		TexturedModel treeTex = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("frost_pine2")));
		TexturedModel dragonTex = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("red")));
		TexturedModel bunnyTex = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));
		TexturedModel grassTex = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("snowGrassTexture")));
		TexturedModel fernTex = new TexturedModel(fernModel, fernTextureAtlas);
		TexturedModel flowerTex = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("snowFlower")));
		TexturedModel wolfTex = new TexturedModel(wolfModel, new ModelTexture(loader.loadTexture("lightGrey")));
		TexturedModel lowPolyTreeTex = new TexturedModel(lowPolyTreeModel, polyTreeTextureAtlas);
		TexturedModel boxTex = new TexturedModel(boxModel, new ModelTexture(loader.loadTexture("box")));
		
		
		//------------Terrain texture-------------
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("snow"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("snowy_ground_1"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("ice"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("snowy_dirt"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap3"));
		
		//----------------------------------------
		
		grassTex.getTexture().setHasTransparency(true);
		fernTex.getTexture().setHasTransparency(true);
		flowerTex.getTexture().setHasTransparency(true);
		
		grassTex.getTexture().setUseFakeLighting(true);
		fernTex.getTexture().setUseFakeLighting(true);
		flowerTex.getTexture().setUseFakeLighting(true);
		
		//ModelTexture tTexture = treeTex.getTexture();
		ModelTexture dTexture = dragonTex.getTexture();
		//ModelTexture bTexture = bunnyTex.getTexture();
		
		dTexture.setShineDamper(5);
		dTexture.setReflectivity(1);
		
		Terrain terrain = new Terrain(0,0,loader,texturePack, blendMap, "heightMap2");
		
		//Entity tree = new Entity(treeTex, new Vector3f(0,0,-50),0,0,0,1);
		Entity dragon = new Entity(dragonTex, new Vector3f(200,terrain.getHeightOfTerrain(200,200),200),0,0,0,1);
		Entity bunny = new Entity(bunnyTex, new Vector3f(250,terrain.getHeightOfTerrain(250,200),200),0,0,0,1);
		Entity wolf = new Entity(wolfTex, new Vector3f(175,terrain.getHeightOfTerrain(175,220),220),0,0,0,0.05f);
		Entity box = new Entity(boxTex, new Vector3f(225,terrain.getHeightOfTerrain(225,175),175),0,0,0,5);
		
		box.setCollisions(10, 1, 10);
		dragon.setCollisions(20, 20, 5);
		bunny.setCollisions(10, 20, 10);
		wolf.setCollisions(5, 20, 20);
		
		Light sun = new Light(new Vector3f(3000,2000,3000), new Vector3f(1,1,1));
		
		List<Entity> allTrees = new ArrayList<Entity>();
		List<Entity> allGrass = new ArrayList<Entity>();
		List<Entity> allFerns = new ArrayList<Entity>();
		List<Entity> allFlowers = new ArrayList<Entity>();
		List<Entity> allTrees2 = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 100; i++){
			float x = random.nextFloat()*(terrain.SIZE-100)+100;
			float z = (random.nextFloat()*(terrain.SIZE-100)+100);
			float y = terrain.getHeightOfTerrain(x,z);
			allTrees.add(new Entity(treeTex, new Vector3f(x,y,z), random.nextInt(10), random.nextInt(180),0f,random.nextInt(5)+1));
		}
		for(int i = 0; i < 0; i++){
			float x = random.nextFloat()*terrain.SIZE;
			float z = (random.nextFloat()*terrain.SIZE);
			float y = terrain.getHeightOfTerrain(x,z);
			allTrees2.add(new Entity(lowPolyTreeTex, random.nextInt(4), new Vector3f(x,y,z), 0f,0f,0f,1));
		}
		for(int i = 0; i < 0; i++){
			float x = random.nextFloat()*terrain.SIZE;
			float z = (random.nextFloat()*terrain.SIZE);
			float y = (terrain.getHeightOfTerrain(x,z));
			allGrass.add(new Entity(grassTex, new Vector3f(x,y,z), 0f,0f,0f,1f));
		}
		for(int i = 0; i < 0; i++){
			float x = random.nextFloat()*terrain.SIZE;
			float z = (random.nextFloat()*terrain.SIZE);
			float y = terrain.getHeightOfTerrain(x,z);
			allFlowers.add(new Entity(flowerTex, new Vector3f(x,y,z), 0f,0f,0f,1f));
		}
		for(int i = 0; i < 0; i++){
			float x = random.nextFloat()*terrain.SIZE;
			float z = (random.nextFloat()*terrain.SIZE);
			float y = terrain.getHeightOfTerrain(x,z);
			allFerns.add(new Entity(fernTex, random.nextInt(4), new Vector3f(x,y,z), 0f,0f,0f,1f));
		}
		
		MasterRenderer renderer = new MasterRenderer();

		Player player = new Player(wolfTex, new Vector3f(250,0,250),0,90,0,0.05f);
		Camera camera = new Camera(player);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("alpha"), new Vector2f(0.75f, 0.75f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
			renderer.processEntity(player);
			for(Entity tree : allTrees){
				renderer.processEntity(tree);
				tree.setCollisions(5, 30, 5);
			}
			for(Entity tree2 : allTrees2){
				renderer.processEntity(tree2);
			}
			for(Entity grass : allGrass){
				renderer.processEntity(grass);
			}
			for(Entity fern : allFerns){
				renderer.processEntity(fern);
			}
			for(Entity flower : allFlowers){
				renderer.processEntity(flower);
			}
			renderer.processEntity(bunny);
			renderer.processEntity(dragon);
			renderer.processEntity(wolf);
			renderer.processEntity(box);
			
			renderer.processTerrain(terrain);
			renderer.render(sun, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
