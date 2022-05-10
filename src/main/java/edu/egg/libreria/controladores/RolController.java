package edu.egg.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.libreria.entidades.Rol;
import edu.egg.libreria.servicios.RolServicio;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolServicio rolServicio;

    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario() {
        ModelAndView mav = new ModelAndView("rol/role-form");
        mav.addObject("rol", new Rol());
        return mav;
    }

    @PostMapping("/crear")
    public RedirectView crear(Rol rolDto) {
        RedirectView redirect = new RedirectView("/");
        rolServicio.crear(rolDto);
        return redirect;
    }
}
