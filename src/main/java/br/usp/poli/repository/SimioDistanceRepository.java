package br.usp.poli.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.poli.entity.SimioDistanceEntity;


@Repository
public interface SimioDistanceRepository extends JpaRepository<SimioDistanceEntity, Long> {
	List<SimioDistanceEntity> findByTimestampOrderByDistanceAsc(Date timestamp);
	List<SimioDistanceEntity> findAllByOrderByTimestampDesc();
}
