import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException, NullPointerException {

		Scanner input = new Scanner(System.in);
		boolean isFile = true;
		FileWriter FileWriter = new FileWriter("output.txt"); // Output file for results.
		PrintWriter writer = new PrintWriter(FileWriter);
		ArrayList<String> variables = new ArrayList<String>(); // All variables from the given file.
		ArrayList<Vertex> vertices = new ArrayList<Vertex>(); // All vertices from the given file.

		// Get the file name from command-line.
		System.out.print("Please enter the file name: ");
		String FileName = "";
		FileName = input.nextLine();
		while (isFile) {

			try {
				
				File file = new File(FileName); // Creates a new file instance.
				FileReader fileReader = new FileReader(file);
				Scanner scanner = new Scanner(fileReader);// Reads the file.
				isFile = false;
				// Read line by line.
				while (scanner.hasNextLine()) {

					String check = scanner.nextLine();

					// Skip the comment lines.
					if (check.contains("#")) {

						continue;
					}

					// Add the other lines to the variables list.
					variables.add(check);

				}
				scanner.close(); // Closes the scanner.
				
			} catch (Exception e) {
				
				System.out.print("Please enter the appropriate file name: ");
				FileName = input.nextLine();
				//isFile = true;
				
			}
			input.close();

		}
		Graph graph = new Graph(); // Graph object from it's class.
		variables.remove(0);

		System.out.println();

		int id = 1;

		for (int i = 0; i < variables.size(); i++) {

			String FileVariables = variables.get(i);

			// Get the all x coordinate, y coordinate and radius values.
			double xLabel = Double.parseDouble(FileVariables.substring(0, FileVariables.indexOf("\t")));
			double yLabel = Double.parseDouble(
					FileVariables.substring(FileVariables.indexOf("\t") + 1, FileVariables.lastIndexOf("\t")));
			double radius = Double.parseDouble(FileVariables.substring(FileVariables.lastIndexOf("\t") + 1));

			// Add the x, y and radius values to the vertex.
			Vertex vertex = new Vertex(id, xLabel, yLabel, radius);
			vertices.add(vertex);
			id++;
		}

		// Before adding edge between two vertices, calculate the distance with respect.
		// to sum of the radius between two vertices.

		for (int i = 0; i < variables.size(); i++) {

			for (int j = i + 1; j < vertices.size(); j++) {

				double distance = Math.pow((vertices.get(i).xLabel - vertices.get(j).xLabel), 2)
						+ Math.pow((vertices.get(i).yLabel - vertices.get(j).yLabel), 2);

				double RadiusSum = vertices.get(i).radius + vertices.get(j).radius;

				distance = Math.sqrt(distance);

				// If distance between two vertices is smaller than their radius sum, then the
				// vertices have an edge between them.

				if (RadiusSum >= distance) {

					graph.addEdge(vertices.get(j), vertices.get(i)); // Add edge between two vertices.
				}

			}
		}

		System.out.print("Hop distance: ");

		// Calculate the hop distance between first vertex and all vertices by using BFS
		// based shortest path algorithm.

		// If there is no connection between first vertex and that vertex, then hop
		// distance is 0.

		for (int index = 0; index < vertices.size(); index++) {

			try {

				int distance = graph.shortestPathLengthBFS(vertices.get(0), vertices.get(index));
				writer.print(distance);
				writer.print("\n");
				System.out.print(distance + " ");
			}

			catch (NullPointerException e) {

				writer.print("0");
				writer.print("\n");
				System.out.print("0 ");
			}

		}
		writer.close();

	}
}
