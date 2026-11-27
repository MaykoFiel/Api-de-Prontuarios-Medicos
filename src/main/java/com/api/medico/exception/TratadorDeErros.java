package com.api.medico.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DadosErroPadrao> tratarErro404(EntityNotFoundException ex) {

      var mnsagem =  (ex.getMessage() != null && !ex.getMessage().isBlank())
        ? ex.getMessage() : "O registro solicitado não foi encontrado.";

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadosErroPadrao(404, "Recurso não encontrado", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400Validacao(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new)
                .toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler({ValidacaoException.class, IllegalArgumentException.class})
    public ResponseEntity<DadosErroPadrao> tratarErroRegraDeNegocio(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(new DadosErroPadrao(400, "Regra de negócio violada", ex.getMessage()));
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DadosErroPadrao> tratarErroDuplicidade(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new DadosErroPadrao(409, "Conflito de dados", "CPF, CRM ou e-mail já cadastrado no sistema."));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DadosErroPadrao> tratarErroLogin() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new DadosErroPadrao(401, "Não autorizado", "E-mail ou senha inválidos."));
    }

    public record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    public record DadosErroPadrao(LocalDateTime timestamp, int status, String erro, String mensagem) {
        public DadosErroPadrao(int status, String erro, String mensagem) {
            this(LocalDateTime.now(), status, erro, mensagem);
        }
    }
}