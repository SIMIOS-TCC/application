package br.usp.poli.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

	private Double x;
	
	private Double y;
	
	private Date timestamp;
}
