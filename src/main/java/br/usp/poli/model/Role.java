package br.usp.poli.model;

import java.util.List;

import br.usp.poli.entity.PermissionEntity;
import br.usp.poli.entity.UserEntity;
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
 
	private List<UserEntity> users;
 
	private List<PermissionEntity> permissions; 
}