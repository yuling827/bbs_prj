package com.zx.bbsprj.repository;

import com.zx.bbsprj.entity.Critique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CritiqueRepository extends JpaRepository<Critique,Integer>,JpaSpecificationExecutor<Critique> {
}
