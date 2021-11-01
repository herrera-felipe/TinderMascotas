package com.practica.tinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practica.tinder.entitys.Voto;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String> {

}
