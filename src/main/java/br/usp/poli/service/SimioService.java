package br.usp.poli.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.usp.poli.entity.SimioEntity;
import br.usp.poli.exception.UnsuitedBirthYearException;
import br.usp.poli.model.Simio;
import br.usp.poli.repository.SimioRepository;


@Service
@Configurable
public class SimioService implements BaseService<Simio>{
	
	@Autowired
	private SimioRepository simioRepository;
	
	public SimioService() {
		super();
	}

	//Create
	public Long save(Simio simio) {
		try {
			SimioEntity simioEntity = modelToEntity(simio);
			simioRepository.save(simioEntity);
			return simioEntity.getId();
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be created on db");
		} catch (UnsuitedBirthYearException e) {
			throw e;
		}
	}
	
	//Read
	public List<Simio> readAll(){
		
		List<Simio> simios = new ArrayList<Simio>();
		
		simioRepository.findAll().forEach(simioEntity -> {
			simios.add(entityToModel(simioEntity));
		});
		
		return simios;
	}
	
	public Simio readById(Long id) {
		SimioEntity simioEntity = simioRepository.findOne(id);
		if(simioEntity != null) return entityToModel(simioEntity);
		return null;
	}
	
	public List<Simio> readByName(String name) {
		
		List<Simio> simios = new ArrayList<Simio>();
		
		simioRepository.findByNameContaining(name).forEach(simioEntity -> {
			simios.add(entityToModel(simioEntity));
		});
		
		return simios;
	}
	
	//Delete
	public void delete(Long id) {
		try {
			simioRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio - cannot be deleted on db");
		}
	}
	
	//Model - Entity
	public Simio entityToModel(SimioEntity simioEntity) {
		
		Simio simio = Simio.builder()
				.id(simioEntity.getId())
				.name(simioEntity.getName())
				.gender(simioEntity.getGender())
				.build();
		
		if(simioEntity.getBirthYear() != null) {
			Integer currentYear = (Integer)(Calendar.getInstance().get(Calendar.YEAR));
			Integer age = currentYear - simioEntity.getBirthYear();
			
			simio.setBirthYear(simioEntity.getBirthYear());
			simio.setAge(age);
		}

		return simio;
	}
	
	public SimioEntity modelToEntity(Simio simio) {
		SimioEntity simioEntity = SimioEntity.builder()
				.id(simio.getId())
				.name(simio.getName())
				.gender(simio.getGender())
				.build();
		
		if(simio.getBirthYear() != null) {
			Integer currentYear = (Integer)(Calendar.getInstance().get(Calendar.YEAR));
			if(simio.getBirthYear() > currentYear) {
				throw new UnsuitedBirthYearException("Existing simio cannot be born in the future!");
			}
			
			simioEntity.setBirthYear(simio.getBirthYear());
		}
		
		return simioEntity;
	}
}
