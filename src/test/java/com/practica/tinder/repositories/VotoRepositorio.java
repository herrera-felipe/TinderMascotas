package com.practica.tinder.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practica.tinder.entitys.Voto;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String> {

	@Query("SELECT c FROM Voto c WHERE c.mascotaOriginaVoto.id = :id ORDER BY c.fecha DESC")
	public List<Voto> buscarVotosPropios(@Param("id") String id);
	
	@Query("SELECT c FROM Voto c WHERE c.mascotaRecibeVoto.id = :id ORDER BY c.fecha DESC")
	public List<Voto> buscarVotosRecibidos(@Param("id") String id);
}
