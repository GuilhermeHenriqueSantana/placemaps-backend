package com.esoft.placemaps.placemaps.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UsuarioEscopo {
  
  private static UsuarioRepository usuarioRepository;

  @Autowired
  public UsuarioEscopo(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public static Usuario usuarioAtual() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal.equals("anonymousUser")) {
      return null;
    }
    return usuarioRepository.findByEmail(((User) principal).getUsername()).get();
  }

}
