package br.usp.poli.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.usp.poli.model.Simio;
import br.usp.poli.repository.Simios;


@Service
public class SimioService {
	
	@Autowired
	private Simios simios;
	
	//Create
	public void create(Simio simio) {
		try {
			simios.save(simio);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be created on db");
		}
	}
	
	//Read
	public List<Simio> readAll(){
		return simios.findAll();
	}
	
	public Optional<Simio> readById(Long id) {
		try {
			return simios.findById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid id for simio");
		}
	}
	
	public List<Simio> readTemperatureGreaterThan(int temperature) {
		return simios.findByTemperatureGreaterThan(temperature);
	}
	
	//Update
	public void update(Simio simio) {
		try {
			simios.save(simio);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be updated on db");
		}
	}
	
	//Delete
	public void delete(Long id) {
		try {
			simios.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be deleted on db");
		}
	}
}
