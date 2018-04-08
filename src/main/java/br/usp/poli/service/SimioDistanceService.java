package br.usp.poli.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.usp.poli.model.SimioDistance;
import br.usp.poli.repository.SimioDistanceRepository;


@Service
public class SimioDistanceService {
	
	@Autowired
	private SimioDistanceRepository simioDistanceRepository;
	
	//Create
	public void create(SimioDistance simioDistance) {
		try {
			simioDistanceRepository.save(simioDistance);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be created on db");
		}
	}
	
	//Read
	public List<SimioDistance> readAll(){
		return simioDistanceRepository.findAll();
	}
	
	public SimioDistance readById(Long id) {
		try {
			return simioDistanceRepository.findOne(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid id for simio distance");
		}
	}
	
	//Update
	public void update(SimioDistance simioDistance) {
		try {
			simioDistanceRepository.save(simioDistance);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be updated on db");
		}
	}
	
	//Delete
	public void delete(Long id) {
		try {
			simioDistanceRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be deleted on db");
		}
	}
}
