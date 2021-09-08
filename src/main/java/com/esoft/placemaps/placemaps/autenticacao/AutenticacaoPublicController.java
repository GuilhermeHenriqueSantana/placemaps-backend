package com.esoft.placemaps.placemaps.autenticacao;

import com.esoft.placemaps.placemaps.autenticacao.dto.LoginDTO;
import com.esoft.placemaps.placemaps.autenticacao.dto.RespostaLoginDTO;
import com.esoft.placemaps.placemaps.autenticacao.dto.TrocaDeSenhaDTO;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/autenticacao")
public class AutenticacaoPublicController {

    private final AutenticacaoService autenticacaoService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AutenticacaoPublicController(AutenticacaoService autenticacaoService,
                                        AuthenticationManager authenticationManager) {
        this.autenticacaoService = autenticacaoService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<RespostaLoginDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = this.authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginDTO.getEmail(), loginDTO.getSenha()
                            )
                    );

            RespostaLoginDTO respostaLoginDTO = this.autenticacaoService.realizarLogin(loginDTO.getEmail(), loginDTO.getSenha());
            return ResponseEntity.ok(respostaLoginDTO);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(path = "/trocar-senha")
    public ResponseEntity<RespostaLoginDTO> trocarSenha(@RequestBody TrocaDeSenhaDTO trocaDeSenhaDTO) {
        try {
            Authentication authentication =this.authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    trocaDeSenhaDTO.getEmail(), trocaDeSenhaDTO.getSenha()
                            )
                    );

            return ResponseEntity.ok(this.autenticacaoService.trocarSenha(trocaDeSenhaDTO));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<RespostaLoginDTO> cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(this.autenticacaoService.cadastrarUsuario(usuario));
    }

}
