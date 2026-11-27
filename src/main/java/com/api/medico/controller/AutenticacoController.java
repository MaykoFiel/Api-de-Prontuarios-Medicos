package com.api.medico.controller;

import com.api.medico.dto.DadosAuteticacao;
import com.api.medico.dto.DadosTokensJWT;
import com.api.medico.model.Usuario;
import com.api.medico.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacoController {

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    public AutenticacoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DadosTokensJWT> efetuarLogin(@RequestBody @Valid DadosAuteticacao dadosAuteticacao) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAuteticacao.email(), dadosAuteticacao.senha());

        var authentication = manager.authenticate(authenticationToken);


        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokensJWT(tokenJWT));
    }
}