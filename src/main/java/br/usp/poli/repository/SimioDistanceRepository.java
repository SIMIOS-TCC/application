package br.usp.poli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.poli.model.SimioDistance;


@Repository
public interface SimioDistanceRepository extends JpaRepository<SimioDistance, Long> {
	List<SimioDistance> findBySimioId1OrSimioId2(Long simioId1, Long simioId2);
	List<SimioDistance> findBySimioId1AndSimioId2(Long simioId1, Long simioId2);
}
