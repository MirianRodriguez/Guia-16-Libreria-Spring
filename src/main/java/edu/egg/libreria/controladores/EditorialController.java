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

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.servicios.EditorialServicio;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping
    public ModelAndView obtenerEditoriales(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("editorial/index.html");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            if(inputFlashMap.containsKey("exito")){
                mav.addObject("exito", inputFlashMap.get("exito"));
            }
            if(inputFlashMap.containsKey("error")){
                mav.addObject("error", inputFlashMap.get("error"));
            }
        }
        
        mav.addObject("editoriales", editorialServicio.obtenerTodos());

        return mav;
    }

    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("editorial/formulario.html");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null){
            mav.addObject("editorial", inputFlashMap.get("editorial"));
            mav.addObject("error", inputFlashMap.get("error"));
        }else{
            mav.addObject("editorial", new Editorial());
        }

        mav.addObject("action", "crear");
        return mav;        
    }

    @PostMapping("/crear")
    public RedirectView crear(Editorial editorialDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/editoriales");
        try {         
            editorialServicio.crear(editorialDto);
            atributos.addFlashAttribute("exito", "La editorial se ha almacenado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("editorial", editorialDto);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/editoriales/formulario");
        }
        return redireccion;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("editorial/formulario");
        mav.addObject("editorial", editorialServicio.obtenerPorId(id));
        mav.addObject("action", "actualizar");
        return mav;
    }

    @PostMapping("/actualizar")
    public RedirectView actualizar(Editorial editorialDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/editoriales");
        editorialServicio.actualizar(editorialDto);
        atributos.addFlashAttribute("exito", "Se ha modificado la editorial");
        return redireccion;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/editoriales");
        try {        
            editorialServicio.eliminarPorId(id);
            atributos.addFlashAttribute("exito", "Se ha eliminado la editorial");
        } catch (Exception e) {
            atributos.addFlashAttribute("error", e.getMessage());
        }
        return redireccion;
    }
}
