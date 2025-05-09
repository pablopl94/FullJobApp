package com.fulljob.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;

import jakarta.transaction.Transactional;

@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, Integer> {

	List<Solicitud> findByUsuarioEmail(String email);

	List<Solicitud> findByVacante_idVacante(int idVacante);

	Optional<Solicitud> findByVacanteAndUsuario(Vacante vacante, Usuario usuario);

	List<Solicitud> findByVacante_Empresa_IdEmpresa(int idEmpresa);
	
	boolean existsByVacanteIdVacanteAndUsuarioEmail(Integer idVacante, String email);
	
	List<Solicitud> findTop5ByVacante_Empresa_IdEmpresaOrderByFechaDesc (int idEmpresa);

	List<Solicitud> findTop5ByUsuarioEmailOrderByFechaDesc(String email);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM Solicitud s WHERE s.vacante.idVacante = :idVacante")
    void deleteByIdVacante(@Param("idVacante") Integer idVacante);
}
