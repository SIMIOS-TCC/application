package br.usp.poli.model;

import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
public class SimioDistance {

	private Long id;
	
	@NotNull
	private Long simioId1;
	
	@NotNull
	private Long simioId2;
	
	private Double distance;
	
	//Constructors
	public SimioDistance(Long id, Long simioId1, Long simioId2, Double distance) {
		super();
		this.id = id;
		this.simioId1 = simioId1;
		this.simioId2 = simioId2;
		this.distance = distance;
	}

	public SimioDistance() {
		super();
	}

	//Getter and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSimioId1(Long simioId1) {
		this.simioId1 = simioId1;
	}

	public void setSimioId2(Long simioId2) {
		this.simioId2 = simioId2;
	}

	public void setSimioIds(Long simioId1, Long simioId2) {
		if(simioId2 > simioId1) {
			this.simioId1 = simioId1;
			this.simioId2 = simioId2;
		}
		else {
			this.simioId1 = simioId2;
			this.simioId2 = simioId1;
		}		
	}

	public Long getSimioId1() {
		return simioId1;
	}
	
	public Long getSimioId2() {
		return simioId2;
	}
	
	public Long getPairedSimioId(Long mainSimioId) {
		if(mainSimioId == simioId1) return simioId2;
		if(mainSimioId == simioId2) return simioId1;
		return 0L;
		//TODO: throw new Exception("Invalid simio Id - not paired");
	}
	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
