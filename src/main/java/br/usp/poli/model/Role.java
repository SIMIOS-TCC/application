package br.usp.poli.model;

import java.util.List;

import br.usp.poli.entity.PermissionEntity;
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
public class Role {
 
	private Long id;
	private String name;
	private String description;
	private boolean checked;
 
	private List<UserModel> users;
 
	private List<PermissionEntity> permissions;

	public Role(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	} 
	
}