package br.usp.poli.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Simio {

	private Long id;
	
	@NotBlank(message="Name cannot be blank.")
	@Size(max=15, message="Name cannot contain more than 15 characters.")
	private String name;
	
	private int temperature;
	
	//Constructors
	public Simio() {
		super();
	}

	public Simio(Long id, String name, int temperature) {
		super();
		this.id = id;
		this.name = name;
		this.temperature = temperature;
	}
	
}
