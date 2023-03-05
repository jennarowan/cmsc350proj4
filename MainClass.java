/**
 * Code By: Joshua Longo
 * Project Name: CMSC 350 Project 4
 * Date: 5/5/21
 *
 * Class Description:
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {

    static DirectedGraph<Object> graph = new DirectedGraph<>();

    public void readGraph() {
        JFrame errorFrame = new JFrame();
        Scanner fileInVertexes = null;
        Scanner fileInEdges = null;

        //Use JFileChooser to get user input
        try {
            //Setup JFileChooser
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            //Show window for user to choose path through directory
            jfc.showOpenDialog(null);
            //Receive selected file, set to File object
            File selectedFile = jfc.getSelectedFile();
            //Set file selected to a readable file with Scanner class
            fileInVertexes = new Scanner(selectedFile);
            fileInEdges = new Scanner(selectedFile);
        } catch (FileNotFoundException error) {
            JOptionPane.showMessageDialog(errorFrame, "Selected file was not found.");
            System.out.println("System exit due to selected file error.");
            System.exit(0);
        }//End try-catch for filechooser

        //Upon valid input, process file into a graph
        try {
            while (fileInVertexes.hasNextLine()) {
                String edgeString = fileInVertexes.nextLine();
                String[] edges = edgeString.split(" ");
                DirectedGraph.Node vertex = new DirectedGraph.Node(edges[0]);

                if (graph.startNode == null) {
                    graph.startNode = vertex;
                }//End of if (graph.startNode == null)


                if (!graph.adjacencyList.contains(vertex)){

                    if (!vertex.equals(graph.startNode)) {
                        DirectedGraph.Node lastNode = graph.adjacencyList.getLast();
                        lastNode.nextVertex = vertex;
                    }
                    graph.adjacencyList.add(vertex);
                }//end of if (!graph.adjacencyList.contains(vertex))
            }//End of while (fileInVertexes.hasNextLine())

            DirectedGraph.Node vertex = (DirectedGraph.Node) graph.startNode;

            while (fileInEdges.hasNextLine()){
                String edgeString = fileInEdges.nextLine();
                String[] edges = edgeString.split(" ");

                for (int i = 1; i < edges.length; i++){
                    DirectedGraph.Node newNode = new DirectedGraph.Node(edges[i]);
                    graph.addEdge(vertex, newNode);
                }
                vertex = vertex.nextVertex;

            }//end of while (fileInEdges.hasNextLine()) loop
        } catch(ArrayIndexOutOfBoundsException error) {
            System.out.println("ERROR: " + error);
            System.exit(0);
        }//End of try-Catch statement
    }//End of readGraph method

    //Main Method
    public static void main(String[] args) {
        new MainClass().readGraph();

        graph.depthFirstSearch();

        System.out.println("Hierarchy:" + graph.heirarch.toString());
        System.out.println("Parenthesized: \n" + graph.parenth.toString());

        graph.displayUnreachable();
    }//End of main method
}//End of MainClass
