package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionariograph.model.Parola;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {

	public class EdgeTraversedGraphListener implements TraversalListener<Parola, DefaultEdge> {

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> ev) {
			 Parola sourceVertex = grafo.getEdgeSource(ev.getEdge());
			   Parola targetVertex = grafo.getEdgeTarget(ev.getEdge());
		
		if(!backVisit.containsKey(targetVertex) && backVisit.containsValue(sourceVertex))
			backVisit.put(targetVertex, sourceVertex);
		else if(!backVisit.containsKey(sourceVertex) && backVisit.containsValue(targetVertex))
			backVisit.put(sourceVertex, targetVertex);
			 
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<Parola> arg0) {}

		@Override
		public void vertexTraversed(VertexTraversalEvent<Parola> arg0) {}

	}

	private Graph <Parola, DefaultEdge> grafo;
	WordDAO dao = new WordDAO() ; 
	private Map <Parola, Parola> backVisit;
	
	public void createGraph(int numeroLettere) {

		this.grafo = new SimpleGraph (DefaultEdge.class);
		/*
		 * devo cercare nel dbms tutte le parole con la stessa lunghezza --> le parole diventano vertici collegate da un arco se differiscono per una sola lettera
		 */
		List <Parola> vertici = dao.getAllWordsFixedLength(numeroLettere);
		//aggiungi i vertici
		Graphs.addAllVertices(this.grafo, vertici);
		
		
		 //aggiungo gli archi
		 this.confrontoParole(vertici, numeroLettere);
		
	}

	public void  confrontoParole(List <Parola> vertici, int numeroLettere){
		//confronto ogni parola della lista con tutte le altre presenti : se cambia solo una lettera le aggiungo alla lista result
		
		List<Parola> result = new LinkedList<Parola>();
		for(int i =0; i<vertici.size(); i++) {
		
			for(int k =i+1; k< vertici.size(); k++) {
				int counter =0;
				String p1 = vertici.get(i).getParola();
				String p2 = vertici.get(k).getParola();
				
					for(int j =0; j< numeroLettere; j++){
						if( p1.charAt(j) != p2.charAt(j))
							counter++;
					}
				if(counter ==1) {
					this.grafo.addEdge(vertici.get(i), vertici.get(k));
					/*result.add(vertici.get(i));
					result.add(vertici.get(i+1));*/
																}
			}
			}
		
		//aggiungo archi 
	/*	for(int c=0; c<result.size(); c=+2) {
			this.grafo.addEdge(result.get(c), result.get(c+1));
		}
		
		*/
	}
	
	
	public List<Parola> displayNeighbours(Parola parolaInserita) {
		
		/*GraphIterator <Parola, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo, parolaInserita);
		List<Parola> result = new ArrayList<Parola>();
		backVisit = new HashMap<>();
		it.addTraversalListener(new Model.EdgeTraversedGraphListener());
		backVisit.put(parolaInserita, null);
		while(it.hasNext()) {
			result.add(it.next());
		}*/
		
		return Graphs.neighborListOf(grafo, parolaInserita);
	}

	public int findMaxDegree() {
		/*
		 * devo trovare il vertice da cui si dipartono più archi
		 */
		int maxdegree=0;
		
		for(Parola p : grafo.vertexSet()) {
		int grado = grafo.degreeOf(p);
			if(grado> maxdegree) {
				maxdegree=grado;
			}
		}
		
		
			
		
		return maxdegree;
	}

	public Graph <Parola, DefaultEdge> getGrafo() {
		
		return this.grafo;
	}
}
