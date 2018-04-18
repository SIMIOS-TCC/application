package br.usp.poli.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
@Table(name="role")
public class RoleEntity {
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_role")
	private Long id;
 
	private String name;
 
	private String description;
 
	@ManyToMany(mappedBy = "roles")
	private List<UserEntity> users;
 
	@ManyToMany
	@JoinTable(
	name="tb_permission_role",
	joinColumns=@JoinColumn(name="id_role", referencedColumnName="id_role"),
	inverseJoinColumns=@JoinColumn(name="id_permission", referencedColumnName="id_permission")
	)
	private List<PermissionEntity> permissions; 
}