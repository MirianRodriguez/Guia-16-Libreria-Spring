package edu.egg.libreria.servicios;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.libreria.entidades.Usuario;
import edu.egg.libreria.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService{

    private final UsuarioRepositorio usuarioRepositorio;
    private final BCryptPasswordEncoder codificador;

    

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, BCryptPasswordEncoder codificador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.codificador = codificador;
    }

    @Transactional
    public void create(Usuario usuarioDto) {
        if (usuarioRepositorio.existsByEmail(usuarioDto.getEmail()))
            throw new IllegalArgumentException("Ya existe un usuario con este email");

        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDto.getEmail());
        usuario.setContrasenia(codificador.encode(usuarioDto.getContrasenia()));

        usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No hay un usuario asociado con el email ingresado."));

        return new User(usuario.getEmail(), usuario.getContrasenia(), emptyList());
    }
    
}
