package com.spring.blogen.repository;

import com.spring.blogen.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
    public List<Post> findAllByTextoContains(@Param("texto") String texto);
}
