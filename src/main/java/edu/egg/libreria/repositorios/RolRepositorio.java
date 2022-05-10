package edu.egg.libreria.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.egg.libreria.entidades.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Integer>{
    boolean existsByNombre(String nombre);

    Optional<Rol> findByNombre(String nombre);
    
}
