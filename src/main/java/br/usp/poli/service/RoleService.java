package br.usp.poli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.usp.poli.entity.RoleEntity;
import br.usp.poli.model.Role;
import br.usp.poli.repository.PermissionRepository;
import br.usp.poli.repository.RoleRepository;
 
@Service
@Transactional
public class RoleService {
 
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
 
	@Transactional(readOnly = true)
	public List<Role> readAll(){
 
		List<Role> roles =  new ArrayList<Role>();
 
		List<RoleEntity> rolesEntity = this.roleRepository.findAll();
 
		rolesEntity.forEach(role ->{ 
		   roles.add(new Role(role.getId(), role.getName(), role.getDescription())); 
	    });
 
		return roles;
	}
	
	public RoleEntity modelToEntity(Role role) {
		RoleEntity roleEntity = RoleEntity.builder()
				.id(role.getId())
				.name(role.getName())
				.description(role.getDescription())
				.build();
		
		roleEntity.setPermissions(permissionRepository.findByRolesIn(roleEntity));
		
		return roleEntity;
	}
	
	public Role entityToModel(RoleEntity roleEntity) {
		Role role = Role.builder()
				.id(roleEntity.getId())
				.name(roleEntity.getName())
				.description(roleEntity.getDescription())
				.permissions(permissionRepository.findByRolesIn(roleEntity))
				.build();
		
		return role;
	}
 
}