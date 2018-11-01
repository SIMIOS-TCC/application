package br.usp.poli.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.usp.poli.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simio {

	private Long id;
	
	@NotBlank(message="Name cannot be blank.")
	@Size(max=15, message="Name cannot contain more than 15 characters.")
	private String name;
	
	@NotNull(message="Select a gender.")
	private Gender gender;
	
	private Integer birthYear;
	
	private Integer age;
	
	private Position position;
	
}
