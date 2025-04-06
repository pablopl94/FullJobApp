package com.fulljob.api.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

	@Transient
	private List<SimpleGrantedAuthority> authorities;

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return Collections.singletonList(new SimpleGrantedAuthority(rol));
	}

	public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
	    this.authorities = authorities;
	}


	@Override
	public String getUsername() {
	    return email;
	}

	@Override
	public String getPassword() {
	    return password;
	}

	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return enabled != null && enabled == 1;
	}

	  
}
