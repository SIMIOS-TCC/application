package br.usp.poli.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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
public class UserModel {
 
	private Long id;
 
	@NotEmpty(message = "Please provide your name")
	private String name;
	
	@Email(message = "Please provide a valid Email")
	@NotEmpty(message = "Please provide an email")
	private String email;
 
	@NotEmpty(message = "Please provide a login ID")
	private String login;
 
	@Length(min = 5, message = "Your password must have at least 5 characters")
	@NotEmpty(message = "Please provide your password")
	private String password;
 
	private boolean active = true;
 
	@NotNull(message = "Please provide user roles")
	private List<Role> roles;
   
 
}