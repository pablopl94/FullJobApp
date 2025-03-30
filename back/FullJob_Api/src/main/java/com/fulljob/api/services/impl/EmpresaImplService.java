package com.fulljob.api.services.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fulljob.api.models.dto.AltaEmpresaRequestDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IEmpresaRepository;
import com.fulljob.api.repository.IUsuarioRepository;
import com.fulljob.api.services.IEmpresaService;
import com.fulljob.api.utils.PasswordGenerator;

@Service
public class EmpresaImplService extends GenericCrudServiceImpl<Empresa, Integer> implements IEmpresaService {

	@Autowired
	private IEmpresaRepository empresaRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;


	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Empresa, Integer> getRepository() {
		return empresaRepository;
	}
	
	/**
	 * METODO DAR DE ALTA EMPRESA ( POR EL ADMON)
	 *
	 * Paso a paso, lo que hacemos es:
	 * 1. Comprobamos si el email ya está registrado. Si ya existe, lanzamos un error.
	 * 2. Generamos una contraseña aleatoria para el nuevo usuario.
	 * 3. Encriptamos esa contraseña para que no se guarde visible en la base de datos.
	 * 4. Creamos un nuevo usuario con el rol "EMPRESA" y lo guardamos.
	 * 5. Creamos la empresa con los datos que nos han mandado (como CIF, dirección, etc.)
	 *    y la vinculamos con el usuario que acabamos de crear.
	 * 6. Guardamos la empresa.
	 * 7. Devolvemos el usuario creado y la contraseña generada (sin encriptar), por si se necesita mostrar al usuario.
	 *
	 * Además, este método tiene la anotación @Transactional. Esto sirve para que, si algo falla
	 * en mitad del proceso, nada se guarde en la base de datos. Así evitamos que se quede a medias.
	 *
	 * @param dto Datos que ha rellenado la empresa para registrarse.
	 * @param usuario Usuario que ha iniciado esta acción (en este caso, no se usa).
	 * @return El usuario creado y la contraseña generada sin codificar.
	 * @throws IllegalArgumentException si el email ya está registrado.
	 */



	//METODO PARA BUSCAR EL USUARIO DE UNA EMPRESA 
	// Podríamos usar una relación inversa desde Usuario, pero preferimos este enfoque
	// para evitar relaciones innecesarias y duplicación de datos.
	@Override
	public Empresa findByUsuario(Usuario usuario) {
	    return empresaRepository.findByUsuario(usuario)
	            .orElseThrow(() -> new IllegalArgumentException("No se encontró ninguna empresa asociada al usuario."));
	}
}
