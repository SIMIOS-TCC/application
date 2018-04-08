package br.usp.poli.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.usp.poli.model.Simio;
import br.usp.poli.repository.SimioRepository;


@Service
@Configurable
public class SimioService {
	
	@Autowired
	private SimioRepository simioRepository;
	
	public SimioService() {
		super();
	}

	//Create
	public void create(Simio simio) {
		try {
			simioRepository.save(simio);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be created on db");
		}
	}
	
	//Read
	public List<Simio> readAll(){
		return simioRepository.findAll();
	}
	
	public Simio readById(Long id) {
		try {
			return simioRepository.findOne(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid id for simio");
		}
	}
	
	public List<Simio> readTemperatureGreaterThan(int temperature) {
		return simioRepository.findByTemperatureGreaterThan(temperature);
	}
	
	public List<Simio> readName(String name) {
		return simioRepository.findByNameContaining(name);
	}
	
	//Update
	public void update(Simio simio) {
		try {
			simioRepository.save(simio);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be updated on db");
		}
	}
	
	//Delete
	public void delete(Long id) {
		try {
			simioRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be deleted on db");
		}
	}
}
