package br.usp.poli.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SimioDistance {

	@Id
	@Column(name="simio_distance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long simioId1;
	
	@NotNull
	private Long simioId2;
	
	private Double distance;

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

	//Hash and Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimioDistance other = (SimioDistance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
