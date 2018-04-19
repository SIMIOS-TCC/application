package br.usp.poli.model;

import java.util.List;

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
 
	private String name;
	
	private String email;
 
	private String login;
 
	private String password;
 
	private boolean active;
 
	private List<Role> roles;
   
 
}