package br.usp.poli.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.usp.poli.model.SimioDistance;
import br.usp.poli.repository.SimioDistances;


@Service
public class SimioDistanceService {
	
	@Autowired
	private SimioDistances simioDistances;
	
	//Create
	public void create(SimioDistance simioDistance) {
		try {
			simioDistances.save(simioDistance);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be created on db");
		}
	}
	
	//Read
	public List<SimioDistance> readAll(){
		return simioDistances.findAll();
	}
	
	public Optional<SimioDistance> readById(Long id) {
		try {
			return simioDistances.findById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid id for simio distance");
		}
	}
	
	//Update
	public void update(SimioDistance simioDistance) {
		try {
			simioDistances.save(simioDistance);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be updated on db");
		}
	}
	
	//Delete
	public void delete(Long id) {
		try {
			simioDistances.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be deleted on db");
		}
	}
}
