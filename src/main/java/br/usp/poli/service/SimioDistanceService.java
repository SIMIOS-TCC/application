package br.usp.poli.service;

import java.util.List;

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
	
	public SimioDistance readById(Long id) {
		try {
			return simioDistances.findOne(id);
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
			simioDistances.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be deleted on db");
		}
	}
}
