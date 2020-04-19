package findShortestPath;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
                                                 // Graph Class.
public class Graph {

	// Each node maps to a list of all his neighbors.
	private HashMap<Vertex, LinkedList<Vertex>> adjacencyMap;

	public Graph() { // Constructor.

		adjacencyMap = new HashMap<>();
	}

	public void addEdge(Vertex source, Vertex destination) { // Add edge between source and destination vertices.

		
		if (!adjacencyMap.keySet().contains(source))	
			adjacencyMap.put(source, null);

		if (!adjacencyMap.keySet().contains(destination))
			adjacencyMap.put(destination, null);

		EdgeAdder(source, destination); // Link  destination to source.

		EdgeAdder(destination, source); // Link source the destionation.

	}
    
    public void EdgeAdder(Vertex source, Vertex destination) { // Links the given destination vertex to source vertex. 
		
		LinkedList<Vertex> Temp = adjacencyMap.get(source);

		if (Temp != null) {
			Temp.remove(destination);
		} else
			Temp = new LinkedList<>();
		Temp.add(destination);
		adjacencyMap.put(source, Temp);
	}

	
    
	// Calculate path size between source and destination vertices.
	
	public int shortestPathLengthBFS(Vertex source, Vertex destination) {

		Map<Vertex, Integer> distance = new HashMap<Vertex, Integer>();
		Map<Vertex, Vertex> previous = new HashMap<Vertex, Vertex>();
		Queue<Vertex> queue = new LinkedList<>();

		distance.put(source, 0); // Source Vertex added to the distance hashmap with value 0.
		previous.put(source, null); // Source Vertex added to the previous hashmap with value null (Updated later).
		queue.offer(source); // Add the given source vertex to the queue.

		while (!queue.isEmpty()) {
			
			// Retrieves and removes the head of queue. 
			Vertex vertex = queue.poll();
			
			//If vertex equals destionation, calculate the size of the path between them.
			
			if (vertex == destination) { 
				
				return CalculatePath(vertex, previous);
			}
			
			// Remove that vertex.
			queue.remove(vertex);
			
			for (Vertex neighbor : adjacencyMap.get(vertex)) {
				
				// Since it is unweighted graph, if given vertex has neighbor we simply increase distance by one.
				
				// BFS search distance.
				if (!distance.containsKey(neighbor)) {
					
					distance.put(neighbor, distance.get(vertex) + 1); // Increase distance by one.
					previous.put(neighbor, vertex); // update previous with value of vertex which is distance value.
					queue.offer(neighbor);
					
				}
			}
		}

		return 0; 
	}
    
	// Return the path size between given target vertex and previous vertex.
	
	int CalculatePath(Vertex target, Map<Vertex, Vertex> previous) {
		
		List<Vertex> VertexList = new LinkedList<Vertex>();
		Vertex vertex = target;
		
		while (previous.get(vertex) != null) {
			
			VertexList.add(vertex);
			vertex = previous.get(vertex);
			
		}
		return VertexList.size();	
	}

}
