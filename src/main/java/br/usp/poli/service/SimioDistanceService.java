package br.usp.poli.service;

import static br.usp.poli.utils.ConstantsFile.SAMPLING_TAX;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

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
import br.usp.poli.repository.SimioRepository;


@Service
public class SimioDistanceService implements BaseService<SimioDistance>{
	
	@Autowired
	private SimioDistanceRepository simioDistanceRepository;
	
	@Autowired
	private SimioService simioService;
	
	@Autowired 
	private SimioRepository simioRepository;
	
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
	
	public List<SimioDistance> readByTimestamp(Date timestamp) {
		
		List<SimioDistance> simioDistances = new ArrayList<SimioDistance>();
				
		simioDistanceRepository.findByTimestampOrderByDistanceAsc(timestamp).forEach(simioDistanceEntity -> {
			simioDistances.add(entityToModel(simioDistanceEntity));
		});
		
		return simioDistances;
	}
	
	public List<SimioDistance> readByNewestTimestamp() {
		
		List<SimioDistance> simioDistances = new ArrayList<SimioDistance>();
		
		List<SimioDistanceEntity> allDistances = simioDistanceRepository.findAllByOrderByTimestampDesc();
		if(allDistances.isEmpty()) {
			return simioDistances;
		}
		
		Date newestTimestamp = allDistances.get(0).getTimestamp();
		
		List<SimioDistanceEntity> filteredDistances = new ArrayList<SimioDistanceEntity>();
		allDistances.forEach(d -> {
			if(d.getTimestamp().getTime() > (newestTimestamp.getTime()-SAMPLING_TAX)) {
				filteredDistances.add(d);
			}
		});
		
		filteredDistances.forEach(simioDistanceEntity -> {
			simioDistances.add(entityToModel(simioDistanceEntity));
		});
			
		return simioDistances;
	}
	
	public List<SimioDistance> readDistancesForMap() {
		
		List<SimioDistance> simioDistances = new ArrayList<SimioDistance>();
		
		List<SimioDistanceEntity> allDistances = simioDistanceRepository.findAllByOrderByTimestampDesc();
		if(allDistances.isEmpty()) {
			return simioDistances;
		}
		
		List<SimioDistanceEntity> finalDistances = new ArrayList<SimioDistanceEntity>();
		
		List<SimioEntity> allSimios = simioRepository.findAll();
		
		//para cada simio, achar o trio de Simio Distances dentro do tempo discreto sem repetir AP
		for(SimioEntity simio : allSimios) {
			List<SimioDistanceEntity> distances = allDistances.stream()
					.filter(d -> d.getSimioEntity().getId() == simio.getId())
					.collect(Collectors.toList());
			
			long currentTimestamp = distances.get(0).getTimestamp().getTime();
			Map<Long,SimioDistanceEntity> discreteDistances = new HashMap<Long,SimioDistanceEntity>();
			List<SimioDistanceEntity> storedDistances = new ArrayList<SimioDistanceEntity>();
			
			int i = 0;
			while(i < distances.size() && discreteDistances.keySet().size() < 3) {
				SimioDistanceEntity d = distances.get(i++);
				
				if(d.getTimestamp().getTime() < currentTimestamp-SAMPLING_TAX) {
					currentTimestamp -= SAMPLING_TAX;
					discreteDistances = new HashMap<Long,SimioDistanceEntity>();
					storedDistances = new ArrayList<SimioDistanceEntity>();
				} else {
					if(!discreteDistances.containsKey(d.getAccessPointEntity().getId())) {
						storedDistances.add(d);
						discreteDistances.put(d.getAccessPointEntity().getId(), d);
					}
				}
			}
			
			if(discreteDistances.keySet().size() >= 3) {
				finalDistances.addAll(storedDistances);
				continue;
			}
		}
		
		finalDistances.forEach(simioDistanceEntity -> {
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
