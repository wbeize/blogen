package com.gen.blogen.controller;

import com.gen.blogen.repository.PostRepository;
import com.gen.blogen.model.Post;
import com.gen.blogen.repository.ThemeRepository;
import com.gen.blogen.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts () {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> GetById(@PathVariable long id) {
        return postRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Post>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(postRepository.findByTitleContainingIgnoreCase(title));
    }

    @GetMapping("/text/{text}")
    public ResponseEntity<List<Post>> getByText(@PathVariable String text) {
        return ResponseEntity.ok(postRepository.findAllByTextContains(text));
    }

    @PostMapping
    public ResponseEntity<Post> createPost (@Valid @RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(post));
    }

    @PutMapping
    public ResponseEntity<Post> updatePost (@Valid @RequestBody Post post) {
        return postRepository.findById(post.getId())
                .map(resp -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(postRepository.save(post)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "postagem não encontrada!");

        postRepository.deleteById(id);
    }
}
