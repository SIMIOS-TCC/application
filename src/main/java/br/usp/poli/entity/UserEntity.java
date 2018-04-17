package br.usp.poli.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

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
@Table(name="user")
public class UserEntity {
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_user")
	private Long id;
 
	@NotEmpty(message = "*Please provide your name")
	private String name;
	
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
 
	@NotEmpty(message = "*Please provide a login ID")
	private String login;
 
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	@Transient
	private String password;
 
	private boolean active;
 
	@JoinTable(name = "tb_user_role", 
		    	joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id_user")}, 
			inverseJoinColumns = {@JoinColumn(name = "id_role", referencedColumnName = "id_role")}
		        )
	@ManyToMany(cascade ={ CascadeType.PERSIST, CascadeType.MERGE})	
	private List<RoleEntity> roles;
 
}