package it.polito.tdp.dizionariograph.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.createGraph(4);
		System.out.println(String.format("**Grafo creato** \n Creati %d vertici e %d archi \n", model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size()));
		Parola p = new Parola("casa");
		List<Parola> vicini = model.displayNeighbours(p);
		System.out.println("Neighbours di casa: " + vicini.toString() + "\n");
		
		System.out.println("Cerco il vertice con grado massimo...");
		System.out.println(model.findMaxDegree());
	}

}
