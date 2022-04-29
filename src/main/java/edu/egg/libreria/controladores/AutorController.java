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

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.servicios.AutorServicio;

@Controller
@RequestMapping("/autores")
public class AutorController {
    
    @Autowired
    private AutorServicio autorServicio;

    @GetMapping
    public ModelAndView obtenerAutores(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("autor/index.html");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("autores", autorServicio.obtenerTodos());

        return mav;
    }

    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario() {
        ModelAndView mav = new ModelAndView("autor/formulario.html");
        mav.addObject("autor", new Autor());
        mav.addObject("action", "crear");
        return mav;        
    }

    @PostMapping("/crear")
    public RedirectView crear(Autor autorDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/autores");
        autorServicio.crear(autorDto);
        atributos.addFlashAttribute("exito", "El autor se ha almacenado");
        return redireccion;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("autor/formulario");
        mav.addObject("autor", autorServicio.obtenerPorId(id));
        mav.addObject("action", "actualizar");
        return mav;
    }

    @PostMapping("/actualizar")
    public RedirectView actualizar(Autor autorDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/autores");
        autorServicio.actualizar(autorDto);
        atributos.addFlashAttribute("exito", "Se ha modificado el autor");
        return redireccion;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id) {
        RedirectView redireccion = new RedirectView("/autores");
        autorServicio.eliminarPorId(id);
        return redireccion;
    }
}
