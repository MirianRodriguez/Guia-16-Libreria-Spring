package edu.egg.libreria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.libreria.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer>{
    
}
