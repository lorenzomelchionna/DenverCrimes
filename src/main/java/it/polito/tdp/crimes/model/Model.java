package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private SimpleWeightedGraph<String,DefaultWeightedEdge> Grafo;
	
	private EventsDao dao;
	
	private List<String> percorsoMigliore;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public List<String> getCategorie() {
		return dao.getCategorie();
	}
	
	public void creaGrafo(String categoria, int mese) {
		
		this.Grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(Grafo, dao.getVertici(categoria, mese));
		
		for(Adiacenza a : dao.getAdiacenze(categoria, mese)) {
			if(this.Grafo.getEdge(a.getB1(),a.getB2()) == null) {
				Graphs.addEdgeWithVertices(Grafo, a.getB1(), a.getB2(), a.getPeso());
			}
		}
		
		System.out.println("#VERTICI: "+this.Grafo.vertexSet().size()+"\n");
		System.out.println("#ARCHI: "+this.Grafo.edgeSet().size()+"\n");
		
	}
	
	public List<Adiacenza> getArchi() {
		
		double pesoMedio = 0.0;
		
		for(DefaultWeightedEdge e : this.Grafo.edgeSet()) {
			pesoMedio += this.Grafo.getEdgeWeight(e);
		}
		
		pesoMedio = pesoMedio/this.Grafo.edgeSet().size();
		
		List<Adiacenza> result = new LinkedList<>();
		
		for(DefaultWeightedEdge e : this.Grafo.edgeSet()) {
			if(this.Grafo.getEdgeWeight(e) > pesoMedio)
				result.add(new Adiacenza(this.Grafo.getEdgeSource(e), this.Grafo.getEdgeTarget(e), this.Grafo.getEdgeWeight(e)));
		}
		
		return result;
		
	}
	
	public List<String> trovaPercorso(String sorgente, String destinazione){
		
		this.percorsoMigliore = new ArrayList<>();
		List<String> parziale = new ArrayList<>();
		
		parziale.add(sorgente);
		
		cerca(destinazione, parziale, 0);
		
		return this.percorsoMigliore;
		
	}

	private void cerca(String destinazione, List<String> parziale, int L) {
		
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			if(parziale.size() > this.percorsoMigliore.size()) {
				this.percorsoMigliore = new LinkedList<>(parziale);
			}
			return;
		}
		
		for(String vicino : Graphs.neighborListOf(Grafo, parziale.get(parziale.size()-1))) {
			
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(destinazione, parziale, L+1);
				parziale.remove(parziale.size()-1);
			}
			
		}
		
	}
	
}
