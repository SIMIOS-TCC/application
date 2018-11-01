package br.usp.poli.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimioDistance {

	private Long id;
	
	@NotNull
	private AccessPoint accessPoint;

	@NotNull
	private Simio simio;
	
	@NotNull
	private Double distance;
	
	@NotNull
	private Date timestamp;
}
