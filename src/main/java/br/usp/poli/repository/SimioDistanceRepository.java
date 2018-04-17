package br.usp.poli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.poli.entity.SimioDistanceEntity;


@Repository
public interface SimioDistanceRepository extends JpaRepository<SimioDistanceEntity, Long> {
	List<SimioDistanceEntity> findBySimioId1OrSimioId2(Long simioId1, Long simioId2);
	List<SimioDistanceEntity> findBySimioId1AndSimioId2(Long simioId1, Long simioId2);
}
