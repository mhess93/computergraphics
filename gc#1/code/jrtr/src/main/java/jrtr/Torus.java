package jrtr;

public class Torus extends Shape {

	public Torus(VertexData vertexData) {
		super(vertexData);
	}
	
	public static Torus createTorus(VertexData vertexData, float outerRadius, float innerRadius, int numberOfLanes, int elementsInLane){
		vertexData.addElement(initiateVertices(numberOfLanes, elementsInLane, outerRadius, innerRadius), 
				VertexData.Semantic.POSITION, 3);
		vertexData.addElement(initiateColor(numberOfLanes, elementsInLane), 
				VertexData.Semantic.COLOR, 3);
		vertexData.addIndices(initiateIndices(numberOfLanes, elementsInLane));
		return new Torus(vertexData);
	}
	
	private static float[] initiateVertices(int numberOfLanes, int elementsInLane, float outerRadius, float innerRadius) {		
		float[] vertices = new float[3 * numberOfLanes * elementsInLane];
		
		float laneAngle = (float)Math.toRadians(360)/numberOfLanes;
		float inLaneAngle = (float)Math.toRadians(360)/elementsInLane;
		
		for(int i = 0; i < numberOfLanes; i++){			
			for(int j = 0; j < elementsInLane; j++){
				vertices[3 * ((i * elementsInLane) + j) + 0 ] = (float)((outerRadius + 
											( innerRadius * Math.cos( j*inLaneAngle ) ) ) *
											Math.cos(i * laneAngle));
				vertices[3 * ((i * elementsInLane) + j) + 1 ] = (float)((outerRadius + 
											( innerRadius * Math.cos( j*inLaneAngle ) ) ) *
											Math.sin(i * laneAngle));
				vertices[3 * ((i * elementsInLane) + j) + 2 ] = (float)(innerRadius * Math.sin(j * inLaneAngle));
			}
			
		}
		return vertices;
	}
	
	private static float[] initiateColor(int numberOfLanes, int elementsInLane) {
		float[] c = new float[3 * (numberOfLanes * elementsInLane)];
		boolean white = true;
		int index;
		for(int i = 0; i < numberOfLanes; i++){
			for(int j = 0; j < elementsInLane; j++){
				if(white){
					index = (i * elementsInLane + j) * 3;
					c[index] = 1;
					c[index + 1] = 1;
					c[index + 2] = 1;
				}
				white = white == true ? false : true;
			}
		}
		return c;
	}
	
	private static int[] initiateIndices(int numberOfLanes, int elementsInLane) {
		int indices[] = new int[( (numberOfLanes * elementsInLane) * 2 ) * 3];
		int index, baseIndex, maxIndex;
		maxIndex = numberOfLanes * elementsInLane; 
		
		for(int i = 0; i < numberOfLanes; i++){
			baseIndex = ( i *elementsInLane );
			for(int j = 0; j < elementsInLane ; j++){
				index = (2 * (baseIndex + j)) * 3;
				
				indices[index + 0] = (baseIndex + j) % (maxIndex); 
				indices[index + 1] = (baseIndex + ((j + 1) % elementsInLane))  % (maxIndex);
				indices[index + 2] = (baseIndex + numberOfLanes + j) % (maxIndex); 
				
				indices[index + 3] = (baseIndex + numberOfLanes + j) % (maxIndex);  
				indices[index + 4] = (baseIndex + (numberOfLanes + ((j + 1) % elementsInLane))) % (maxIndex);
				indices[index + 5] = (baseIndex + ((j + 1) %  elementsInLane)) % (maxIndex); 
			}
		}
		return indices;	
	}
}
