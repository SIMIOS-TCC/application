package br.usp.poli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.usp.poli.entity.PermissionEntity;
import br.usp.poli.entity.RoleEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>{
	List<PermissionEntity> findByRolesIn(RoleEntity role);
}
