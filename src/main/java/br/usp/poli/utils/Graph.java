package br.usp.poli.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import br.usp.poli.model.Simio;
import lombok.Data;

@Data
@Component
public class Graph {
	
	private List<Simio> simios;
	private List<Point> positions;
	
	public Graph() {
		super();
	}
	
}