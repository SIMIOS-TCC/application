package br.usp.poli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.usp.poli.entity.RoleEntity;
import br.usp.poli.entity.UserEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	List<RoleEntity> findByUsersIn(UserEntity user);
}
