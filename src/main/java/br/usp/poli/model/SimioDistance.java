package br.usp.poli.model;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SimioDistance {

	private Long id;
	
	@NotNull
	private AccessPoint accessPoint;

	@NotNull
	private Simio simio;
	
	private Double distance;
	
	//Constructors
	public SimioDistance(Long id, AccessPoint accessPoint, Simio simio, Double distance) {
		super();
		this.id = id;
		this.accessPoint = accessPoint;
		this.simio = simio;
		this.distance = distance;
	}
	
	public SimioDistance(Long id, Double distance) {
		super();
		this.id = id;
		this.distance = distance;
	}

	public SimioDistance() {
		super();
	}
}
