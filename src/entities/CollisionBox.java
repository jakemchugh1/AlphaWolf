package entities;

public class CollisionBox {
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private int zMin;
	private int zMax;
	
	public CollisionBox(int xMin,int xMax, int yMin, int yMax, int zMin, int zMax){
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.zMin = zMin;
		this.zMax = zMax;
	}

	public int getxMin() {
		return xMin;
	}

	public int getxMax() {
		return xMax;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getzMin() {
		return zMin;
	}

	public int getzMax() {
		return zMax;
	}
	
}
