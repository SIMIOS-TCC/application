package br.usp.poli;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.usp.poli.entity.PermissionEntity;
import br.usp.poli.entity.RoleEntity;
import br.usp.poli.entity.UserEntity;
import br.usp.poli.repository.PermissionRepository;
import br.usp.poli.repository.RoleRepository;
import br.usp.poli.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimiosApplication.class)
@WebAppConfiguration
public class UserTests {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PermissionRepository permissionRepository;
	
	@Before
	public void loadContext() {
		if(permissionRepoIsEmpty()) insertPermissions();
		if(roleRepoIsEmpty()) insertRoles();
		if(userRepoIsEmpty()) insertUsers();
	}
	
	@After
	public void cleanContext() {
		
	}
	
	@Test
	public void createUser() {
		
	}
	
	private void insertPermissions() {
		permissionRepository.save(PermissionEntity.builder()
				.name("ADMIN")
				.build());
		
		permissionRepository.save(PermissionEntity.builder()
				.name("AUTHENTICATED")
				.build());
	}
	
	private void insertRoles() {
		List<PermissionEntity> permissions = permissionRepository.findAll();
		
		roleRepository.save(RoleEntity.builder()
			.name("Administrator")
			.description("Complete access to all system features")
			.permissions(permissions)
			.build());
		
		roleRepository.save(RoleEntity.builder()
			.name("Common User")
			.description("Access to simio features")
			.permissions(Arrays.asList(permissions.get(1)))
			.build());
	}
	
	private void insertUsers() {
		
		RoleEntity role = roleRepository.findAll().get(0);
		
		UserEntity user = UserEntity.builder()
				.name("Name")
				.email("email@email.com")
				.login("username")
				.password("$2a$10$aGJP0j77LL3RxYveVRvEceiT9rE3P7aWdlK2KgwodfE0zwmZbAt6a")
				.active(true)
				.roles(Arrays.asList(role))
				.build();
		
		userRepository.save(user);

	}
	
	private boolean userRepoIsEmpty() {
		return (userRepository.findAll().isEmpty());
	}
	
	private boolean roleRepoIsEmpty() {
		return (roleRepository.findAll().isEmpty());
	}
	
	private boolean permissionRepoIsEmpty() {
		return (permissionRepository.findAll().isEmpty());
	}
}
