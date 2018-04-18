package br.usp.poli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.usp.poli.entity.PermissionEntity;
import br.usp.poli.entity.RoleEntity;
import br.usp.poli.entity.UserEntity;
import br.usp.poli.model.Role;
import br.usp.poli.model.User;
import br.usp.poli.repository.PermissionRepository;
import br.usp.poli.repository.UserRepository;
 
@Component
public class UserService {
 
	@Autowired
	private UserRepository userRepository;
 
	@Autowired
	private RoleService roleService; 
 
	@Autowired
	private PermissionRepository permissionRepository;
 
	//Create
	public void create(User user){
		this.userRepository.save(modelToEntity(user));
	}
	
	//Read
	public User readUserByLogin(String login) {
		UserEntity userEntity = userRepository.findByLogin(login);
 
		if(userEntity == null)
			throw new BadCredentialsException("User is not registered!");
 
		if(!userEntity.isActive())
			throw new DisabledException("User is not active on system!");
 
		return entityToModel(userEntity);
	}
 
	public List<PermissionEntity> readRolesPermissions(List<RoleEntity> roles) {
		
		List<PermissionEntity> permissions = new ArrayList<PermissionEntity>();
		
		for (RoleEntity role: roles) {
			permissions.addAll(permissionRepository.findByRolesIn(role));
		}
		
		return permissions;
	}
	
	public List<User> readAll(){
 
		List<User> users = new ArrayList<User>();
 
		List<UserEntity> usersEntity = this.userRepository.findAll();
 
		usersEntity.forEach(userEntity ->{
			users.add(entityToModel(userEntity));
		});
 
		return users;
	}
 
	public User readById(Long id){
		UserEntity userEntity = this.userRepository.findOne(id);
		
		if(userEntity == null)
			throw new BadCredentialsException("User is not registered!");
 
		if(!userEntity.isActive())
			throw new DisabledException("User is not active on system!");
		
		return entityToModel(userEntity);
	}
	
	//Update
	public void update(User user){
		this.userRepository.saveAndFlush(modelToEntity(user));
	}
	
	//Delete
	public void delete(Long id){
		this.userRepository.delete(id);
	}
 
	//Model - Entity
	public UserEntity modelToEntity(User user) {
		
		List<RoleEntity> rolesEntity = new ArrayList<RoleEntity>();
		for (Role role : user.getRoles()){
			rolesEntity.add(roleService.modelToEntity(role));
		}
		
		UserEntity userEntity =  UserEntity.builder()
				.active(true)
				.login(user.getLogin())
				.email(user.getEmail())
				.password(new BCryptPasswordEncoder().encode(user.getPassword()))
				.roles(rolesEntity)
				.build();
		
		return userEntity;
	}
 
	public User entityToModel(UserEntity userEntity) {
		
		User user = User.builder()
				.id(userEntity.getId())
				.name(userEntity.getName())
				.email(userEntity.getEmail())
				.login(userEntity.getLogin())
				.active(userEntity.isActive())
				.build();
		
		return user;
	}
}