package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BidListRepository extends JpaRepository<BidList, Integer> {
	
	@Query(value = "SELECT * FROM user WHERE id = ?1", nativeQuery=true)
    Optional<BidList> findById(Integer id);

}
