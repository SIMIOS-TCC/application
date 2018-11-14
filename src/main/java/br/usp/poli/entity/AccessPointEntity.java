package br.usp.poli.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Builder
@Entity
@Table(name="access_point")
public class AccessPointEntity {

	@Id
	@Column(name="id_ap")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Double x;
	
	@NotNull
	private Double y;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="accessPointEntity", cascade=CascadeType.ALL )
	public Set<SimioDistanceEntity> distances; 
	
	public String getCode() {
		return id.toString();
	}
}
