package br.usp.poli.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccessPoint {

	private Long id;
	
	private Long position;
	
	public List<SimioDistance> distances; 
	
	public String getCode() {
		return id.toString();
	}
	
	//Constructors
	public AccessPoint() {
		super();
	}

	public AccessPoint(Long id, Long position) {
		super();
		this.id = id;
		this.position = position;
	}
	
	public AccessPoint(Long id, Long position, List<SimioDistance> distances) {
		super();
		this.id = id;
		this.position = position;
		this.distances = distances;
	}
	
}
