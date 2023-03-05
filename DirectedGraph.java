/**
 * Code By: Joshua Longo
 * Project Name: CMSC 350 Project 4
 * Date: 5/5/21
 *
 * Class Description:
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class DirectedGraph<V> {
    public V startNode = null;
    boolean cycle;
    Set<V> discovered = new HashSet<>();
    Set<V> visited = new HashSet<>();

    ParenthesizedList parenth = new ParenthesizedList();
    Hierarchy heirarch = new Hierarchy();

    LinkedList<Node> adjacencyList = new LinkedList<>();

    static class Node{
        String data;
        LinkedList<Node> edgesList = new LinkedList<>();
        Node nextVertex = null;

        Node(String data){
            this.data = data;
        }
    }//End of Node inner class

    public void depthFirstSearch(){
        cycle = false;
        dfs(startNode);
    }//End of depthFirstSearch method

    private void dfs(V node){
        if (discovered.contains(node)){
            cycle = true;

            heirarch.cycleDetected();
            parenth.cycleDetected();
            return;
        }


        heirarch.processVertex((DirectedGraph.Node)node);
        parenth.processVertex((DirectedGraph.Node)node);

        heirarch.descend((DirectedGraph.Node)node);
        parenth.descend((DirectedGraph.Node)node);

        discovered.add(node);
        visited.add(node);

        DirectedGraph.Node edgeCheck = findItem((Node) node);
        if (edgeCheck != null){
            LinkedList<V> listOfItems = (LinkedList<V>) edgeCheck.edgesList;

            if (!listOfItems.isEmpty()){
                for (V x : listOfItems){
                    dfs(x);
                }
            }
        }//End of if (edgeCheck != null)

        heirarch.ascend((DirectedGraph.Node)node);
        parenth.ascend((DirectedGraph.Node)node);

        discovered.remove(node);
    }//End of dfs method

    public void displayUnreachable(){
        for (int i = 0; i < adjacencyList.size(); i++){
            Node item = adjacencyList.get(i);
            if(!visited.contains(item)){
                System.out.println(item.data + " is unreachable.");
                visited.add((V)item);
            }

            LinkedList<Node> innerItems = adjacencyList.get(i).edgesList;

            for (Node innerItem : innerItems){
                if (!visited.contains(innerItem)){
                    System.out.println(innerItem.data + " is unreachable.");
                    visited.add((V)item);
                }
            }

        }//End of for (int i = 0; i < adjacencyList.size(); i++)
    }//End of displayUnreachable method

    public Node findItem(Node itemToFind){

        for (Node x : adjacencyList){
            if (x.data.equals(itemToFind.data))
                return x;
        }//End of for (Node x : adjacencyList)

        //System.out.println(itemToFind.data + " not found.");
        return null;
    }//End of findItem method

    public void addEdge(Node parentNode, Node nodeToAdd){

        Node x = findItem(nodeToAdd);
        if(x != null){
            nodeToAdd = x;
        }

        parentNode.edgesList.add(nodeToAdd);
    }//End of addEdge method
}//End of DirectedGraph class
