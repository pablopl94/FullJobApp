package com.fulljob.api.restcontroller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fulljob.api.models.dto.CategoriaRequestDto;
import com.fulljob.api.models.dto.CategoriaResponseDto;
import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.services.ICategoriaService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categorias")
@EnableMethodSecurity(prePostEnabled = true)
public class CategoriaRestController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ICategoriaService categoriaService;

	//ENDOPINT PARA VER TODAS LAS CATEGORIAS
	//GET    /categorias ........................... [permitAll]
	@GetMapping
	public ResponseEntity<List<CategoriaResponseDto>> findAllCategories() {

		List<Categoria> categorias = categoriaService.findAll();

		List<CategoriaResponseDto> response = categorias.stream()
				.map(categoria -> modelMapper.map(categoria, CategoriaResponseDto.class)).toList();

		return ResponseEntity.ok(response);
	}
	
	
	
	//ENDPOINT PARA CREAR CATEGORIAS
    //POST   /categorias ........................... [ROLE_ADMON]
	@PostMapping
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<CategoriaResponseDto> createCategory(@RequestBody @Valid CategoriaRequestDto categoriaDto) {

		Categoria nuevaCategoria = modelMapper.map(categoriaDto,Categoria.class);

		Categoria categoriaCreada = categoriaService.insertOne(nuevaCategoria);

		CategoriaResponseDto response = modelMapper.map(categoriaCreada, CategoriaResponseDto.class);

		return ResponseEntity.ok(response);
	}

	
	//ENDPOINT PARA MODIFICAR CATEGORIAS
	//PUT    /categorias/{id} ...................... [ROLE_ADMON]
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<CategoriaResponseDto> update(@PathVariable Integer id, @RequestBody @Valid CategoriaRequestDto categoriaDto) {

		Categoria categoria = categoriaService.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no encontrada"));

		categoria.setNombre(categoriaDto.getNombre());
		categoria.setDescripcion(categoriaDto.getDescripcion());

		Categoria categoriaActualizada = categoriaService.updateOne(categoria);

		CategoriaResponseDto response = modelMapper.map(categoriaActualizada, CategoriaResponseDto.class);

		return ResponseEntity.ok(response);

	}

	//ENDPOINT PARA ELIMINAR CATEGORIA
	////PUT    /categorias/{id} ...................... [ROLE_ADMON]
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<String> deleteCategoria(@PathVariable Integer id) {

		categoriaService.eliminarCategoria(id);

		return ResponseEntity.ok( "Categoría eliminada correctamente");
	}

}
