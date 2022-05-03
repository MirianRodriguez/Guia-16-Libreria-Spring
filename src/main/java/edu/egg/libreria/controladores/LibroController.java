package edu.egg.libreria.controladores;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.servicios.AutorServicio;
import edu.egg.libreria.servicios.EditorialServicio;
import edu.egg.libreria.servicios.LibroServicio;

@Controller
@RequestMapping("/libros")
public class LibroController {
    
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping
    public ModelAndView obtenerLibros(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("libro/index.html");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("libros", libroServicio.obtenerTodos());

        return mav;
    }

    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario() {
        ModelAndView mav = new ModelAndView("libro/formulario.html");
        mav.addObject("libro", new Libro());
        mav.addObject("autores", autorServicio.obtenerTodos());
        mav.addObject("editoriales", editorialServicio.obtenerTodos());
        mav.addObject("action", "crear");
        return mav;        
    }

    @PostMapping("/crear")
    public RedirectView crear(Libro libroDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/libros");
        libroServicio.crear(libroDto);
        atributos.addFlashAttribute("exito", "El libro se ha almacenado");
        return redireccion;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("libro/formulario");
        mav.addObject("libro", libroServicio.obtenerPorId(id));
        mav.addObject("autores", autorServicio.obtenerTodos());
        mav.addObject("editoriales", editorialServicio.obtenerTodos());
        mav.addObject("action", "actualizar");
        return mav;
    }

    @PostMapping("/actualizar")
    public RedirectView actualizar(Libro libroDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/libros");
        libroServicio.actualizar(libroDto);
        atributos.addFlashAttribute("exito", "Se ha modificado el libro");
        return redireccion;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/libros");
        libroServicio.eliminarPorId(id);
        atributos.addFlashAttribute("exito", "Se ha eliminado el libro");
        return redireccion;
    }
}
