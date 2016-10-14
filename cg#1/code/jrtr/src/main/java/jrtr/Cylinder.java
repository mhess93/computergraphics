package jrtr;

public class Cylinder extends Shape{
	

	public Cylinder(VertexData vertexData) {
		super(vertexData);
	}
	
	public static Cylinder createCylinder(VertexData vertexData, int segments, int height){
		vertexData.addElement(initiateVertices(height, segments), VertexData.Semantic.POSITION, 3);
		vertexData.addElement(initiateColors(segments), VertexData.Semantic.COLOR, 3);
		vertexData.addIndices(initiateIndices(segments));
		return new Cylinder(vertexData);
	}
	
	private static float[] initiateVertices(int height, int segments) {
		int numberOfVertices = ((2 * segments) + 2) * 3;
		
		float[] vertices = new float[numberOfVertices];
		double[] init = {1,0,0};
		
		double angle = (Math.toRadians(360)) / segments;
		float x, y;
		
		for(int i = 0; i < segments; i++){
			
			x = (float)(init[0] * Math.cos(i * angle) - init[1] * Math.sin(i * angle));
			y = (float)(init[0] * Math.sin(i * angle) + init[1] * Math.cos(i * angle));
			
			vertices[(6 * i) + 0] = x;
			vertices[(6 * i) + 1] = y;
			vertices[(6 * i) + 2] = height;
			
			vertices[(6 * i) + 3] = x;
			vertices[(6 * i) + 4] = y;
			vertices[(6 * i) + 5] = -height;	
		}
		
		vertices[numberOfVertices - 6] = 0;
		vertices[numberOfVertices - 5] = 0;
		vertices[numberOfVertices - 4] = height;
		
		vertices[numberOfVertices - 3] = 0;
		vertices[numberOfVertices - 2] = 0;
		vertices[numberOfVertices - 1] = -height;
		
		return vertices;
	}
	
	private static float[] initiateColors(int segments) {
		int numberOfVertices = ((2 * segments) + 2) * 3;
		float[] c = new float[numberOfVertices];
		boolean nextIsBlack = true;
		for(int i = 0; i < c.length; i += 6){
			if(nextIsBlack && i + 6 < numberOfVertices){
				for(int j = 0; j < 6; j++){
					c[i + j] = 1;
				}
				nextIsBlack = false;
			}
			else{
				nextIsBlack = true;
			}
		}
		
		c[c.length - 6] = 1;
		c[c.length - 5] = 1;
		c[c.length - 4] = 1;
		
		c[c.length - 3] = 0;
		c[c.length - 2] = 0;
		c[c.length - 1] = 0;
		
		return c;	
	}
	
	private static int[] initiateIndices(int segments) {
		int numberOfIndices = 3 * ((2 * segments) + (2 * segments));
		int indices[] = new int[numberOfIndices];
		
		for(int i = 0; i < 2 * segments; i++){
			indices[(3 * i)] = i;
			indices[(3 * i) + 1] = (i + 1)%(2*segments);
			indices[(3 * i) + 2] = (i + 2)%(2*segments);
		}
		
		int j = (6 * segments); 
		
		for(int i = 0; i < segments; i++){
			indices[j] = (2 * segments) + 1;
			indices[j + 1] = ((i * 2) + 1)%(2*segments);
			indices[j + 2] = ((i * 2) + 3)%(2*segments);
			
			indices[j + 3] = 2 * segments;
			indices[j + 4] = ((i * 2) + 4)%(2*segments);
			indices[j + 5] = ((i * 2) + 2)%(2*segments);
			
			j += 6;
		}
		return indices;
	}
}
