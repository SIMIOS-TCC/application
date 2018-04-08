package br.usp.poli.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class SimioDistance {

	@Id
	@Column(name="simio_distance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "simio_id")
	private Simio simio;
	
	private Long simio2_id;
	
	private Double distance;
	
	@NotNull(message="Timestamp was not found.")
	@Temporal(TemporalType.DATE)
	private Date timestamp;

	
	//Constructors
	public SimioDistance() {
		super();
	}

	public SimioDistance(Simio simio, Long simio2_id, Double distance,
			@NotNull(message = "Timestamp was not found.") Date timestamp) {
		super();
		this.simio = simio;
		this.simio2_id = simio2_id;
		this.distance = distance;
		this.timestamp = timestamp;
	}

	//Getter and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Simio getSimio() {
		return simio;
	}

	public void setSimio(Simio simio) {
		this.simio = simio;
	}

	public Long getSimio2_id() {
		return simio2_id;
	}

	public void setSimio2_id(Long simio2_id) {
		this.simio2_id = simio2_id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
