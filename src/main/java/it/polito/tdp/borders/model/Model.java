package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	Graph<Country, DefaultEdge> grafo;
	Map<Integer, Country> idMap;
	BordersDAO dao;
	private Map<Country, Country> visita;

	public Model() {
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		idMap = new LinkedHashMap<>();
		dao = new BordersDAO();
		dao.loadAllCountries(idMap);
		visita = new LinkedHashMap<>();
	}

	public Collection<Country> getStati() {
		return idMap.values();
	}

	public void calcolaConfini(int anno) {
		List<Border> borders = dao.getCountryPairs(anno, idMap);

		for (Border b : borders) {
			Graphs.addEdgeWithVertices(grafo, b.getC1(), b.getC2());
		}

	}

	public String elencoStati() {
		String s = "";
		for (Country c : grafo.vertexSet()) {
			s += c.getStateName() + " " + Graphs.neighborListOf(grafo, c).size() + "\n"; // oppure grafo.degreeOf(c)
		}
	
		return s;
	}

	public int connesse() {
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		
		return ci.connectedSets().size();
	}
// Quando ho bisogno di componenti connesse o vertici non serve usare Traversal Listener!!!!
	
	public List<Country> BFI(Country start) {
		List<Country> result = new ArrayList<>();
		visita.put(start, null);

		BreadthFirstIterator<Country, DefaultEdge> it = new BreadthFirstIterator<Country, DefaultEdge>(grafo,start);
		while(it.hasNext()) {
			result.add(it.next());
		}
		
	/*	// DepthFirstIterator<Country, DefaultEdge> it= new DepthFirstIterator<Country,
		// DefaultEdge>(grafo);
		it.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {
			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
			}
			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
			}
			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				Country c1 = grafo.getEdgeSource(e.getEdge());
				Country c2 = grafo.getEdgeTarget(e.getEdge());

				if (!visita.containsKey(c1) && visita.containsKey(c2)) {
					visita.put(c1, c2);
				} else if (!visita.containsKey(c2) && visita.containsKey(c1)) {
					visita.put(c2, c1);
				}
			}
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
			}
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
			}
		});
		while (it.hasNext()) {
			it.next();
		}
		result.add(start);
	
		for (Country e : visita.keySet()) {
			for (Country c : Graphs.neighborListOf(grafo, e)) {
				if(!result.contains(c)) {
				result.add(c);
				}
			}
		}*/
		return result;

	}

	public List<Country> DFI(Country start) {
		List<Country> result = new ArrayList<>();
		visita.put(start, null);

		DepthFirstIterator<Country, DefaultEdge> it = new DepthFirstIterator<Country, DefaultEdge>(grafo, start);
		
		while(it.hasNext()) {
			result.add(it.next());
		}
		return result;

	}


	
	public List<Country> ricorsione(Country start) {
	List<Country> parziale= new ArrayList<>();
		
		this.cerca(parziale, start);
		return parziale;

	}
	
	private void cerca(List<Country> parziale,  Country start) {
		
			parziale.add(start);
		
		List<Country> vicini= Graphs.neighborListOf(grafo, start);
	
		
		for(Country c: vicini) {
			if(!parziale.contains(c)) {
				
				this.cerca(parziale, c);
				
			}
		}
		
		
	}

	public List<Country> iterazione(Country start) {
		
		// Creo due liste: quella dei noti visitati ..
				List<Country> visited = new LinkedList<Country>();

				// .. e quella dei nodi da visitare
				List<Country> toBeVisited = new LinkedList<Country>();

				// Aggiungo alla lista dei vertici visitati il nodo di partenza.
				visited.add(start);

				// Aggiungo ai vertici da visitare tutti i vertici collegati a quello inserito
				toBeVisited.addAll(Graphs.neighborListOf(grafo, start));

				while (!toBeVisited.isEmpty()) {

					// Rimuovi il vertice in testa alla coda
					Country temp = toBeVisited.remove(0);

					// Aggiungi il nodo alla lista di quelli visitati
					visited.add(temp);

					// Ottieni tutti i vicini di un nodo
					List<Country> listaDeiVicini = Graphs.neighborListOf(grafo, temp);

					// Rimuovi da questa lista tutti quelli che hai già visitato..
					listaDeiVicini.removeAll(visited);

					// .. e quelli che sai già che devi visitare.
					listaDeiVicini.removeAll(toBeVisited);

					// Aggiungi i rimanenenti alla coda di quelli che devi visitare.
					toBeVisited.addAll(listaDeiVicini);
				}

				// Ritorna la lista di tutti i nodi raggiungibili
				return visited;
	

	}
}
