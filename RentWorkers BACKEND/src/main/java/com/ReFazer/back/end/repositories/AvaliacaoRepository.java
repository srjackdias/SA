package com.ReFazer.back.end.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReFazer.back.end.entities.AvaliacaoEntity;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long>{
    void deleteById(Long id);
}
