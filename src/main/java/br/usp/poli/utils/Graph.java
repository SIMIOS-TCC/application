package br.usp.poli.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import br.usp.poli.model.Simio;

@Component
public class Graph {
	
	private List<Simio> simios;
	private List<Point> positions;
	
	public Graph() {
		super();
	}
	public List<Simio> getSimios() {
		return simios;
	}
	public void setSimios(List<Simio> simios) {
		this.simios = simios;
	}
	public List<Point> getPositions() {
		return positions;
	}
	public void setPositions(List<Point> positions) {
		this.positions = positions;
	}
	
}