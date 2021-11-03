package com.adl.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adl.main.model.FaqsModel;

@Repository
public interface FaqsRepository extends JpaRepository <FaqsModel , Integer> {

}
