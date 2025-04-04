package com.fulljob.api.restcontroller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.CategoriaDto;
import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.services.ICategoriaService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categorias")
public class CategoriaRestController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ICategoriaService iCategoria;

	@GetMapping
	public ResponseEntity<List<CategoriaDto>> findAll() {

		List<Categoria> categorias = iCategoria.findAll();

		List<CategoriaDto> response = categorias.stream()
				.map(categoria -> modelMapper.map(categoria, CategoriaDto.class)).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto> findCategory(@PathVariable Integer id) {

		Categoria categoria = iCategoria.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no encontrada."));

		CategoriaDto response = modelMapper.map(categoria, CategoriaDto.class);

		return ResponseEntity.ok(response);

	}

	@PostMapping  //Aqui tienes un error pixa 
	public ResponseEntity<CategoriaDto> createCategory(@Valid Categoria categoria) {

		Categoria nuevaCategoria = Categoria.builder()
                						    .nombre(categoria.getNombre())
                						    .descripcion(categoria.getDescripcion())
                						    .build();

		Categoria categoriBbdd = iCategoria.insertOne(nuevaCategoria);

		CategoriaDto response = modelMapper.map(categoria, CategoriaDto.class);

		return ResponseEntity.ok(response);

	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDto> UpdateCategory(@PathVariable Integer id,
			@RequestBody @Valid Categoria categoria) {

		Categoria categoriaBbbdd = iCategoria.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no encontrada."));

		categoriaBbbdd.setNombre(categoria.getNombre());
		categoriaBbbdd.setDescripcion(categoria.getDescripcion());

		Categoria categoriaActualizada = iCategoria.updateOne(categoriaBbbdd);

		CategoriaDto response = modelMapper.map(categoria, CategoriaDto.class);

		return ResponseEntity.ok(response);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		iCategoria.deleteOne(id);

		return ResponseEntity.ok("Categoría eliminada correctamente");

	}

}
