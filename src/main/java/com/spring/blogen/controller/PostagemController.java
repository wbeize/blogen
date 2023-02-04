package com.spring.blogen.controller;

import com.spring.blogen.model.Postagem;
import com.spring.blogen.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;

    @GetMapping
    public ResponseEntity<List<Postagem>> getAll() {
        return ResponseEntity.ok(postagemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> GetById(@PathVariable long id) {
        return postagemRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
    }

    @GetMapping("/texto/{texto}")
    public ResponseEntity<List<Postagem>> getByTexto(@PathVariable String texto) {
        return ResponseEntity.ok(postagemRepository.findAllByTextoContains(texto));
    }

    @PostMapping
    public ResponseEntity<Postagem> post (@RequestBody Postagem postagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
    }

    @PutMapping
    public ResponseEntity<Postagem> put (@RequestBody Postagem postagem) {
        return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        postagemRepository.deleteById(id);
    }
}
