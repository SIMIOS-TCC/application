package com.dmhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.usp.poli.model.Animal;

@Repository
public interface Animals extends JpaRepository<Animal, Long> {

}
