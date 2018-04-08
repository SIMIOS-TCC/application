package br.usp.poli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.poli.model.Simio;

@Repository
public interface SimioRepository extends JpaRepository<Simio, Long> {
	List<Simio> findByTemperatureGreaterThan(int temperature);
}
