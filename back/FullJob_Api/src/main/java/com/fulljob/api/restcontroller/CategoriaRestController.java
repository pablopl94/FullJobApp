package com.fulljob.api.restcontroller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.CategoriaRequestDTO;
import com.fulljob.api.models.dto.CategoriaResponseDTO;
import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.services.ICategoriaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categorias")
public class CategoriaRestController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ICategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaResponseDTO>> findAllCategories() {

		List<Categoria> categorias = categoriaService.findAll();

		List<CategoriaResponseDTO> response = categorias.stream()
				.map(categoria -> modelMapper.map(categoria, CategoriaResponseDTO.class)).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{nombre}")
	public ResponseEntity<List<CategoriaResponseDTO>> findByCategoryName(@PathVariable String nombre) {

		List<Categoria> categorias = categoriaService.findByName(nombre);

		if (categorias.isEmpty()) {
			throw new RuntimeException("No se han encontrado categorías con el nombre " + nombre);
		}

		List<CategoriaResponseDTO> response = categorias.stream()
				.map(categoria -> modelMapper.map(categoria, CategoriaResponseDTO.class)).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> findByCategoryId(@PathVariable Integer id) {

		Categoria categoria = categoriaService.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no encontrada"));

		CategoriaResponseDTO response = modelMapper.map(categoria, CategoriaResponseDTO.class);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> createCategory(
			@RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO) {

		Categoria nuevaCategoria = Categoria.builder().nombre(categoriaRequestDTO.getNombre())
				.descripcion(categoriaRequestDTO.getDescripcion()).build();

		Categoria categoriaCreada = categoriaService.insertOne(nuevaCategoria);

		CategoriaResponseDTO response = modelMapper.map(categoriaCreada, CategoriaResponseDTO.class);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Integer id,
			@RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO) {

		Categoria categoria = categoriaService.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no encontrada"));

		categoria.setNombre(categoriaRequestDTO.getNombre());
		categoria.setDescripcion(categoriaRequestDTO.getDescripcion());

		Categoria categoriaActualizada = categoriaService.updateOne(categoria);

		CategoriaResponseDTO response = modelMapper.map(categoriaActualizada, CategoriaResponseDTO.class);

		return ResponseEntity.ok(response);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteCategoria(@PathVariable Integer id) {

		categoriaService.deleteOne(id);

		return ResponseEntity.ok(Map.of("message", "Categoría eliminada correctamente"));
	}

}
