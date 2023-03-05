package com.gen.blogen.repository;

import com.gen.blogen.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> findAllByDescriptionContainingIgnoreCase(@Param("description") String description);
}
