package br.usp.poli.model;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.usp.poli.utils.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccessPoint {

	private Long id;
	
	@NotNull(message="Longitude Position cannot be null")
	@Max(message="Longitude cannot be higher than 180 degrees", value = 180)
	@Min(message="Longitude cannot be lower than -180 degrees", value = -180)
	private Double x;
	
	@NotNull(message="Latitude Position cannot be null")
	@Max(message="Latitude cannot be higher than 90 degrees", value = 90)
	@Min(message="Latitude cannot be lower than -90 degrees", value = -90)
	private Double y;
	
	private Point position;
	
	public List<SimioDistance> distances; 
	
	public String getCode() {
		return id.toString();
	}
	
}
