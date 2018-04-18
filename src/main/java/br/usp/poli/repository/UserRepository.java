package br.usp.poli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.usp.poli.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	UserEntity findByLogin(String login);
}
