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
	
	public SimioDistance readBySimioPair(Long simioId1, Long simioId2){
		if(simioId2 < simioId1) {
			simioId1 = simioId2;
			simioId2 = simioId1;
		}	
		List<SimioDistance> simioDistances = simioDistanceRepository.findBySimioId1AndSimioId2(simioId1, simioId2);
		try {
			if (simioDistances.size() > 1) {
				//TODO: log ("More than one match found for simio distance");
			}
			return simioDistances.get(0);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("No match found for simio distance");
		}
	}
	
	public List<SimioDistance> readBySimioId(Long simioId) {
		return simioDistanceRepository.findBySimioId1OrSimioId2(simioId, simioId);
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
