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

import com.fulljob.api.models.dto.CategoriaDTO;
import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.services.ICategoria;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categorias")
public class CategoriaRestController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ICategoria iCategoria;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<Categoria> categorias = iCategoria.findAll();

		List<CategoriaDTO> response = categorias.stream()
				.map(categoria -> modelMapper.map(categoria, CategoriaDTO.class)).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> findCategory(@PathVariable Integer id) {

		Categoria categoria = iCategoria.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no encontrada."));

		CategoriaDTO response = modelMapper.map(categoria, CategoriaDTO.class);

		return ResponseEntity.ok(response);

	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> createCategory(@Valid Categoria categoria) {

		Categoria nuevaCategoria = Categoria.builder().nombre(categoria.getNombre())
				.descripcion(categoria.getDescripcion()).build();

		Categoria categoriBbdd = iCategoria.insertOne(nuevaCategoria);

		CategoriaDTO response = modelMapper.map(categoria, CategoriaDTO.class);

		return ResponseEntity.ok(response);

	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> UpdateCategory(@PathVariable Integer id,
			@RequestBody @Valid Categoria categoria) {

		Categoria categoriaBbbdd = iCategoria.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no encontrada."));

		categoriaBbbdd.setNombre(categoria.getNombre());
		categoriaBbbdd.setDescripcion(categoria.getDescripcion());

		Categoria categoriaActualizada = iCategoria.updateOne(categoriaBbbdd);

		CategoriaDTO response = modelMapper.map(categoria, CategoriaDTO.class);

		return ResponseEntity.ok(response);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		iCategoria.deleteOne(id);

		return ResponseEntity.ok("Categoría eliminada correctamente");

	}

}
