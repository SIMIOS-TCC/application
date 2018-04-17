package br.usp.poli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.poli.entity.SimioEntity;

@Repository
public interface SimioRepository extends JpaRepository<SimioEntity, Long> {
	List<SimioEntity> findByTemperatureGreaterThan(int temperature);
	List<SimioEntity> findByNameContaining(String name);
	List<SimioEntity> findByNameOrTemperature(String name, int temperature);
}
