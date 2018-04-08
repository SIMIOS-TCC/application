package br.usp.poli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.poli.model.SimioDistance;


@Repository
public interface SimioDistanceRepository extends JpaRepository<SimioDistance, Long> {

}
