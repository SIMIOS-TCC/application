package br.usp.poli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.usp.poli.entity.AccessPointEntity;

public interface AccessPointRepository extends JpaRepository<AccessPointEntity, Long>{

}
