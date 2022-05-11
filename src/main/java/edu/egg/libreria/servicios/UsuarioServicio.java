package edu.egg.libreria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.egg.libreria.entidades.Usuario;
import edu.egg.libreria.repositorios.RolRepositorio;
import edu.egg.libreria.repositorios.UsuarioRepositorio;

import static java.util.Collections.singletonList;

import javax.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private BCryptPasswordEncoder codificador;  
    @Autowired
    private RolRepositorio rolRepositorio;


    @Transactional
    public void create(Usuario usuarioDto) {
        if (usuarioRepositorio.existsByEmail(usuarioDto.getEmail()))
            throw new IllegalArgumentException("Ya existe un usuario con este email");

        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDto.getEmail());
        usuario.setContrasenia(codificador.encode(usuarioDto.getContrasenia()));

        if (usuarioDto.getRol() == null){
            if(usuarioRepositorio.count()==0){
                usuario.setRol(rolRepositorio.findByNombre("ADMIN").orElseThrow(() -> new IllegalArgumentException("Error al crear admin")));
            }else{
                usuario.setRol(rolRepositorio.findByNombre("USER").orElseThrow(() -> new IllegalArgumentException("Error al crear usuario")));
            }
        }

        usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No hay un usuario asociado con el email ingresado."));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("id", usuario.getId());
        session.setAttribute("email", usuario.getEmail());
        session.setAttribute("rol", usuario.getRol().getNombre());

        return new User(usuario.getEmail(), usuario.getContrasenia(), singletonList(authority));
    }
    
}
