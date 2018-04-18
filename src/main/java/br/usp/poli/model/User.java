package br.usp.poli.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

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
public class User {
 
	private Long id;
 
	private String name;
	
	private String email;
 
	private String login;
 
	private String password;
 
	private boolean active;
 
    @JoinColumn(name = "id_role")
	@ManyToMany(cascade ={ CascadeType.PERSIST, CascadeType.MERGE})	
	private List<Role> roles;
   
 
}