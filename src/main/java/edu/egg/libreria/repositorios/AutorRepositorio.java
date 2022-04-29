package edu.egg.libreria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.libreria.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Integer>{

    
}
