package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Battery;

@Repository
public interface BatteryRepository  extends  CrudRepository<Battery, Integer> {

	List<Battery> findAllByPostCode(Integer postCode);

}
