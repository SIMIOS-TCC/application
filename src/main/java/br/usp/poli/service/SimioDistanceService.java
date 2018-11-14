package br.usp.poli.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.usp.poli.entity.AccessPointEntity;
import br.usp.poli.entity.SimioDistanceEntity;
import br.usp.poli.entity.SimioEntity;
import br.usp.poli.model.AccessPoint;
import br.usp.poli.model.Simio;
import br.usp.poli.model.SimioDistance;
import br.usp.poli.repository.SimioDistanceRepository;


@Service
public class SimioDistanceService implements BaseService<SimioDistance>{
	
	@Autowired
	private SimioDistanceRepository simioDistanceRepository;
	
	@Autowired
	private SimioService simioService;
	
	@Autowired
	private AccessPointService accessPointService;
	
	//Create
	public Long save(SimioDistance simioDistance) {
		try {
			SimioDistanceEntity simioDistanceEntity = modelToEntity(simioDistance);
			simioDistanceRepository.save(simioDistanceEntity);
			return simioDistanceEntity.getId();
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid simio distance - cannot be created on db");
		}
	}
	
	//Read
	public List<SimioDistance> readAll(){
		
		List<SimioDistance> simioDistances = new ArrayList<SimioDistance>();
		
		simioDistanceRepository.findAll().forEach(simioDistanceEntity -> {
			simioDistances.add(entityToModel(simioDistanceEntity));
		});
		
		return simioDistances;
	}
	
	public SimioDistance readById(Long id) {
		SimioDistanceEntity simioDistanceEntity = simioDistanceRepository.findOne(id);
		if(simioDistanceEntity != null) return entityToModel(simioDistanceEntity);
		return null;
	}
	
//	public SimioDistance readBySimioPair(Long simioId1, Long simioId2){
//		if(simioId2 < simioId1) {
//			Long aux = simioId1;
//			simioId1 = simioId2;
//			simioId2 = aux;
//		}	
//		List<SimioDistanceEntity> simioDistances = simioDistanceRepository.findBySimioId1AndSimioId2(simioId1, simioId2);
//		if (simioDistances.size() > 1) {
//			//TODO: log ("More than one match found for simio distance");
//		}
//		SimioDistanceEntity simioDistanceEntity = simioDistances.get(0);
//		if(simioDistanceEntity != null) return entityToModel(simioDistanceEntity);
//		return null;
//	}
//	
	public List<SimioDistance> readByTimestamp(Date timestamp) {
		
		List<SimioDistance> simioDistances = new ArrayList<SimioDistance>();
				
		simioDistanceRepository.findByTimestampOrderByDistanceAsc(timestamp).forEach(simioDistanceEntity -> {
			simioDistances.add(entityToModel(simioDistanceEntity));
		});
		
		return simioDistances;
	}
	
	//Update
	public void update(SimioDistance simioDistance) {
		try {
			simioDistanceRepository.save(modelToEntity(simioDistance));
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
	
	//Model - Entity
		public SimioDistance entityToModel(SimioDistanceEntity simioDistanceEntity) {
			
			AccessPoint accessPoint = accessPointService.entityToModel(simioDistanceEntity.getAccessPointEntity());
			Simio simio = simioService.entityToModel(simioDistanceEntity.getSimioEntity());
			
			SimioDistance simioDistance = SimioDistance.builder()
					.id(simioDistanceEntity.getId())
					.accessPoint(accessPoint)
					.simio(simio)
					.distance(simioDistanceEntity.getDistance())
					.timestamp(simioDistanceEntity.getTimestamp())
					.build();

			return simioDistance;
		}
		
		public SimioDistanceEntity modelToEntity(SimioDistance simioDistance) {
			
			AccessPointEntity accessPointEntity = accessPointService.modelToEntity(simioDistance.getAccessPoint());
			SimioEntity simioEntity = simioService.modelToEntity(simioDistance.getSimio());
			
			SimioDistanceEntity simioDistanceEntity = SimioDistanceEntity.builder()
					.id(simioDistance.getId())
					.accessPointEntity(accessPointEntity)
					.simioEntity(simioEntity)
					.distance(simioDistance.getDistance())
					.timestamp(simioDistance.getTimestamp())
					.build();
			
			return simioDistanceEntity;
		}
}
