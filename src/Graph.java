package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class Graph<Label> {

    private class Edge {
        public int source;
        public int destination;
        public Label label;

        public Edge(int from, int to, Label label) {
            this.source = from;
            this.destination = to;
            this.label = label;
        }
    }

    private int cardinal;
    private ArrayList<LinkedList<Edge>> incidency;
    private ArrayList<Integer> visited = new ArrayList<Integer>();


    public Graph(int size) {
        cardinal = size;
        incidency = new ArrayList<LinkedList<Edge>>(size + 1);
        for (int i = 0; i < cardinal; i++) {
            incidency.add(i, new LinkedList<Edge>());
        }
    }

    public ArrayList<LinkedList<Edge>> getIncidency() {
        return this.incidency;
    }

    public int order() {
        return cardinal;
    }

    public void addArc(int source, int dest, Label label) {
        incidency.get(source).addLast(new Edge(source, dest, label));
    }

    public String toString() {
        String result = new String("");
        result = result + cardinal + "\n";
        for (int i = 0; i < cardinal; i++) {
            for (Edge e : incidency.get(i)) {
                result = result + e.source + " " + e.destination + " "
                        + e.label.toString() + "\n";
            }
        }
        return result;
    }

    public Graph graphTranspose() {
        com.company.Graph<Integer> graph = new Graph(cardinal);
        int label = 0;
        for (int i = 0; i < this.incidency.size(); i++) {
            int dest = i;
            LinkedList<Edge> edges = incidency.get(i);
            for (Edge edge : edges) {
                graph.addArc(edge.destination, dest, label++);
            }
        }
        return graph;
    }

    //Parcours en profondeur - DFS
    public void Explore(Edge e, LinkedList<Integer> result) {
        int v = e.destination;
        if (!visited.contains(v)) {
            visited.add(v);
            for (Edge edge : incidency.get(v)) Explore(edge, result);
            result.addFirst(v);
        }
    }

    public LinkedList<Integer> DFS() {
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (int i = 0; i < cardinal; i++) {
            if (!visited.contains(i)) {
                visited.add(i);
                LinkedList<Edge> destinations = incidency.get(i);
                for (Edge edge : destinations) {
                    Explore(edge, result);
                }
                result.addFirst(i);
            }
        }
        System.out.println(result);
        return result;
    }

    public ArrayList<LinkedList<Integer>> DFS(LinkedList<Integer> order) {
        ArrayList<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();
        for (Integer element : order) {
            //System.out.println(element);
            if (!visited.contains(element)) {
                visited.add(element);
                //System.out.println(visited);
                LinkedList<Integer> components = new LinkedList<Integer>();
                for (Edge edge : this.getIncidency().get(element)) {
                    //System.out.println("destination " + edge.destination + " from " + edge.source);
                    if(!visited.contains(edge.destination)) {
                        Explore(edge, components);
                        //System.out.println("at vertex " + edge.source + " visited " + visited);
                        components.addFirst(edge.destination);
                    }
                }
                //System.out.println(components);
                result.add(components);
            }
        }
        System.out.println(result);
        return result;
    }

}
