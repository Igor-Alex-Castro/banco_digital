package com.br.bancodigital.handler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.br.bancodigital.exceptions.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 400 Bad Request - Parâmetro ausente
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException ex,
			HttpServletRequest request) {
		return ResponseEntity.badRequest()
				.body(Map.of("timestamp", LocalDateTime.now(), "status", HttpStatus.BAD_REQUEST, "error",
						"Parâmetro obrigatório não informado", "message", ex.getMessage(), "path",
						request.getRequestURI()));

	}

	// 400 Bad Request - Parâmetro inválido / @NotBlank / @NotNull
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex,
			HttpServletRequest request) {
		return ResponseEntity.badRequest()
				.body(Map.of("timestamp", LocalDateTime.now(), "status", HttpStatus.BAD_REQUEST, "error",
						"Parâmetro inválido", "message", ex.getMessage(), "path", request.getRequestURI()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		String errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining());
		System.out.println(ex.getFieldError());

		return ResponseEntity.badRequest()
				.body(Map.of("timestamp", LocalDateTime.now(), "status", HttpStatus.BAD_REQUEST, "error", errors,
						"message", "DTO inválido: " + ex.getMessage(), "path", request.getRequestURI()));
	}

	// 404 Not Found - Recurso não encontrado

	/*
	 * @ExceptionHandler(ResourceNotFoundException.class) public
	 * ResponseEntity<Map<String, Object>>
	 * handleResourceNodFound(ResourceNotFoundException ex, HttpServletRequest
	 * request){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
	 * "timestamp", LocalDateTime.now(), "status", HttpStatus.NOT_FOUND, "error",
	 * "Recurso não encontrado", "message", ex.getMessage(), "path",
	 * request.getRequestURI() )); }
	 */

	// 409 Conflict - Violação de integridade no banco
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(

				"timestamp", LocalDateTime.now(), "status", HttpStatus.CONFLICT, "error", "Violação de integridade",
				"message", ex.getMessage(), "path", request.getRequestURI()));

	}

// 409 Conflict - Violação de regra de negócio

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(

				"timestamp", LocalDateTime.now(), "status", HttpStatus.CONFLICT, "error",
				"Violação de regra de negócio", "message", ex.getMessage(), "path", request.getRequestURI()));

	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(

				"timestamp", LocalDateTime.now(), "status", HttpStatus.UNAUTHORIZED, "error", "Não autenticado",
				"message", ex.getMessage(), "path", request.getRequestURI()));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(

				"timestamp", LocalDateTime.now(), "status", HttpStatus.FORBIDDEN, "error", "Acesso negado", "message",
				ex.getMessage(), "path", request.getRequestURI()));
	}

	// 500 Internal Server Error - Qualquer erro inesperado
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(

				"timestamp", LocalDateTime.now(), "status", HttpStatus.INTERNAL_SERVER_ERROR, "error", "Erro interno",
				"message", ex.getMessage(), "path", request.getRequestURI()));
	}

	/*
	 * Metodo para ser utilizado para construir o json da padronização private
	 * ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String
	 * error, String message) { return ResponseEntity.status(status).body(Map.of(
	 * "timestamp", LocalDateTime.now(), "status", status.value(), "error", error,
	 * "message", message )); }
	 */

}
