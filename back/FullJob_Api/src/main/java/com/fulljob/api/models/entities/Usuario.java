package com.fulljob.api.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, length = 45)
	private String email;
	
	@Column(nullable = false, length = 45)
	private String nombre;
	
	@Column(nullable = false, length = 100)
	private String apellidos;
	
	@Column(nullable = false, length = 100)
	private String password;

	//Aqui le asignamos por defecto que este activo el usuario
	//para cuando se da de alta un usuario
    @Builder.Default
    @Column(nullable = false)
	private Integer enabled = 1;

	@Column(name = "fecha_registro")
	private LocalDate fechaRegistro;
	
	@Column(nullable = false, length = 15)
	private String rol;

	// METODOS CLASE USERDETAILS DE SPRINGSECURIRY 

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(rol.split(",")).stream()
	        .map(role -> (GrantedAuthority) () -> role.trim())
	        .toList();
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
	     return password;
	}
	  
}
