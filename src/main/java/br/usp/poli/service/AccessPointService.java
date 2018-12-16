package br.usp.poli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.usp.poli.entity.AccessPointEntity;
import br.usp.poli.model.AccessPoint;
import br.usp.poli.repository.AccessPointRepository;
import br.usp.poli.utils.Point;


@Service
@Configurable
public class AccessPointService implements BaseService<AccessPoint>{
	
	@Autowired
	private AccessPointRepository accessPointRepository;
	
	public AccessPointService() {
		super();
	}

	//Create
	public Long save(AccessPoint accessPoint) {
		try {
			AccessPointEntity accessPointEntity = modelToEntity(accessPoint);
			accessPointRepository.save(accessPointEntity);
			return accessPointEntity.getId();
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid access point - cannot be created on db");
		}
	}
	
	//Read
	public List<AccessPoint> readAll(){
		
		List<AccessPoint> aps = new ArrayList<AccessPoint>();
		
		accessPointRepository.findAll().forEach(accessPointEntity -> {
			aps.add(entityToModel(accessPointEntity));
		});
		
		return aps;
	}
	
	public AccessPoint readById(Long id) {
		AccessPointEntity accessPointEntity = accessPointRepository.findOne(id);
		if(accessPointEntity != null) return entityToModel(accessPointEntity);
		return null;
	}
	
	//Update
	public void update(AccessPoint accessPoint) {
		try {
			accessPointRepository.save(modelToEntity(accessPoint));
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid access point - cannot be updated on db");
		}
	}
	
	//Delete
	public void delete(Long id) {
		try {
			accessPointRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid access point - cannot be deleted on db");
		}
	}
	
	//Model - Entity
	public AccessPoint entityToModel(AccessPointEntity accessPointEntity) {
		
		Point position = new Point(accessPointEntity.getX(), accessPointEntity.getY());
		AccessPoint accessPoint = AccessPoint.builder()
				.id(accessPointEntity.getId())
				.position(position)
				.x(accessPointEntity.getX())
				.y(accessPointEntity.getY())
				.build();
		
		return accessPoint;
	}
	
	public AccessPointEntity modelToEntity(AccessPoint accessPoint) {
		
		AccessPointEntity accessPointEntity = AccessPointEntity.builder()
				.id(accessPoint.getId())
				.x(accessPoint.getX())
				.y(accessPoint.getY())
				.build();
		
		return accessPointEntity;
	}
}
