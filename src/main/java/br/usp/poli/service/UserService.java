package br.usp.poli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.usp.poli.entity.PermissionEntity;
import br.usp.poli.entity.RoleEntity;
import br.usp.poli.entity.UserEntity;
import br.usp.poli.model.Role;
import br.usp.poli.model.SecurityUser;
import br.usp.poli.model.UserModel;
import br.usp.poli.repository.PermissionRepository;
import br.usp.poli.repository.RoleRepository;
import br.usp.poli.repository.UserRepository;
 
@Component
public class UserService implements UserDetailsService{
 
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
 
	@Autowired
	private RoleService roleService; 
 
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws BadCredentialsException,DisabledException {
 
		UserEntity userEntity = readByLogin(login);
		
		return new SecurityUser(entityToModel(userEntity),
				readUserAuthorities(userEntity));
	}
	
	public boolean toggleActive(Long id){
		UserEntity userEntity = userRepository.findOne(id);
		userEntity.setActive(!userEntity.isActive());
		userRepository.save(userEntity);
		return userEntity.isActive();
	}
 
	//Create
	public void create(UserModel user){
		if(user.getId() != null) update(user);
		else
			userRepository.save(modelToEntity(null, user));
	}
	
	//Read
	private UserEntity readByLogin(String login) {
		UserEntity userEntity = userRepository.findByLogin(login);
 
		if(userEntity == null)
			throw new BadCredentialsException("User is not registered!");
 
		if(!userEntity.isActive())
			throw new DisabledException("User is not active on system!");
 
		return userEntity;
	}
	
	public List<GrantedAuthority> readUserAuthorities(UserEntity userEntity) {
		 
		List<RoleEntity> roles = roleRepository.findByUsersIn(userEntity);
 
		return this.readRolesAuthorities(roles);
	}
 
	private List<GrantedAuthority> readRolesAuthorities(List<RoleEntity> roles) {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		for (RoleEntity role: roles) {
			List<PermissionEntity> permissions = permissionRepository.findByRolesIn(role);
			
			for (PermissionEntity permission: permissions) {
				auths.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}
		return auths;
	}
	
	public List<UserModel> readAll(){
 
		List<UserModel> users = new ArrayList<UserModel>();
 
		List<UserEntity> usersEntity = this.userRepository.findAll();
 
		usersEntity.forEach(userEntity ->{
			users.add(entityToModel(userEntity));
		});
 
		return users;
	}
 
	public UserModel readById(Long id){
		UserEntity userEntity = this.userRepository.findOne(id);
		
		if(userEntity == null)
			throw new BadCredentialsException("User is not registered!");
 
		if(!userEntity.isActive())
			throw new DisabledException("User is not active on system!");
		
		return entityToModel(userEntity);
	}
	
	//Update
	public void update(UserModel user){
		UserEntity userEntity = userRepository.findOne(user.getId());
		this.userRepository.saveAndFlush(modelToEntity(userEntity, user));
	}
	
	//Delete
	public void delete(Long id){
		this.userRepository.delete(id);
	}
 
	//Model - Entity
	public UserEntity modelToEntity(UserEntity userEntity, UserModel user) {
		
		List<RoleEntity> rolesEntity = new ArrayList<RoleEntity>();
		user.getRoles().forEach(role -> {
			rolesEntity.add(roleService.modelToEntity(role));
		});
		
		if(userEntity == null) userEntity = new UserEntity();
		
		userEntity.setName(user.getName());
		userEntity.setActive(true);
		userEntity.setLogin(user.getLogin());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userEntity.setRoles(rolesEntity);
		
		return userEntity;
	}
 
	public UserModel entityToModel(UserEntity userEntity) {
		
		List<Role> roles = new ArrayList<Role>();
		for (RoleEntity roleEntity : roleRepository.findByUsersIn(userEntity)){
			roles.add(roleService.entityToModel(roleEntity));
		}
		
		UserModel user = UserModel.builder()
				.id(userEntity.getId())
				.password(userEntity.getPassword())
				.name(userEntity.getName())
				.email(userEntity.getEmail())
				.login(userEntity.getLogin())
				.active(userEntity.isActive())
				.roles(roles)
				.build();
		
		return user;
	}
}