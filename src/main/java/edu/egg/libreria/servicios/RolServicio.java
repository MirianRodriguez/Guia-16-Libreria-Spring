package edu.egg.libreria.servicios;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.libreria.entidades.Rol;
import edu.egg.libreria.repositorios.RolRepositorio;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;
    
    @Transactional
    public void crear(Rol rolDto) {
        if (rolRepositorio.existsByNombre(rolDto.getNombre()))
            throw new IllegalArgumentException("Ya existe un rol con ese nombre"); 

        Rol rol = new Rol();

        rol.setNombre(rolDto.getNombre());
        
        rolRepositorio.save(rol);
    }

    @Transactional
    public void actualizar(Rol rolDto) {
        Rol rol = rolRepositorio.findById(rolDto.getId()).get();

        rol.setNombre(rolDto.getNombre());

        rolRepositorio.save(rol);
    }

    @Transactional(readOnly = true)
    public Rol obtenerPorId(Integer id) {
        return rolRepositorio.findById(id).get();        
    }

    @Transactional(readOnly = true)
    public List<Rol> obtenerTodos() {
        return rolRepositorio.findAll();
    }

}
