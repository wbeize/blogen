package com.gen.blogen.controller;

import com.gen.blogen.repository.ThemeRepository;
import com.gen.blogen.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/themes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ThemeController {

    @Autowired
    private ThemeRepository themeRepository;

    @GetMapping
    public ResponseEntity<List<Theme>> getAll() {
        return ResponseEntity.ok(themeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theme> getById(@PathVariable Long id){
        return themeRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Theme>> getByName(@PathVariable String name){
        return ResponseEntity.ok(themeRepository.findAllByDescriptionContainingIgnoreCase(name));
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List> getByDescription(@PathVariable String description) {
        return ResponseEntity.ok(themeRepository.findAllByDescriptionContainingIgnoreCase(description));
    }

    @PostMapping
    public ResponseEntity<Theme> postTheme (@RequestBody Theme theme){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(themeRepository.save(theme));
    }

    @PutMapping
    public ResponseEntity<Theme> putTheme (@RequestBody Theme theme){
        if (theme.getId() != null)
            return themeRepository.findById(theme.getId())
                    .map(resp -> ResponseEntity.ok().body(themeRepository.save(theme)))
                    .orElse(ResponseEntity.notFound().build());

        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTheme(@PathVariable Long id) {
        Optional<Theme> theme = themeRepository.findById(id);

        if (theme.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        themeRepository.deleteById(id);

//        return themeRepository.findById(id)
//                .map(resp -> {
//                    themeRepository.deleteById(id);
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//                })
//
//                .orElse(ResponseEntity.notFound().build());

    }
}
