package com.spring.blogen.controller;

import com.spring.blogen.model.Post;
import com.spring.blogen.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> GetById(@PathVariable long id) {
        return postRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Post>> getByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(postRepository.findAllByTituloContainingIgnoreCase(titulo));
    }

    @GetMapping("/texto/{texto}")
    public ResponseEntity<List<Post>> getByTexto(@PathVariable String texto) {
        return ResponseEntity.ok(postRepository.findAllByTextoContains(texto));
    }

    @PostMapping
    public ResponseEntity<Post> post (@RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(post));
    }

    @PutMapping
    public ResponseEntity<Post> put (@RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.OK).body(postRepository.save(post));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        postRepository.deleteById(id);
    }
}
