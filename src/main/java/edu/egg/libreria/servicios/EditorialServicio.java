package edu.egg.libreria.servicios;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crear(Editorial editorialDto) {
        Editorial editorial = new Editorial();

        editorial.setNombre(editorialDto.getNombre());
        
        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void actualizar(Editorial editorialDto) {
        Editorial editorial = editorialRepositorio.findById(editorialDto.getId()).get();

        editorial.setNombre(editorialDto.getNombre());

        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public Editorial obtenerPorId(Integer id) {
        return editorialRepositorio.findById(id).get();        
    }

    @Transactional(readOnly = true)
    public List<Editorial> obtenerTodos() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void eliminarPorId(Integer id) {
        editorialRepositorio.deleteById(id);
    }
    

    
}
