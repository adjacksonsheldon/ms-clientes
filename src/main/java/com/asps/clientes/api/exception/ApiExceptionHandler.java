package com.asps.clientes.api.exception;

import com.asps.clientes.domain.exception.RegistroDuplicadoException;
import com.asps.clientes.domain.exception.RegistroNaoEncontradoException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<?> handlerRegistroDuplicadoException(RegistroDuplicadoException e
        , WebRequest webRequest) {

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<?> handlerRegistroNaoEncontradoException(RegistroNaoEncontradoException e,WebRequest webRequest) {

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handlerDateTimeParseException(RegistroNaoEncontradoException e,WebRequest webRequest) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handlerValidacaoException(ValidacaoException e,WebRequest webRequest) {
        return handlerBindingResult(e, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest, e.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlerValidacaoException(ConstraintViolationException e,WebRequest webRequest) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        final var title = "Erro ao persistir dados.";

        final var problem = this.createProblemBuilder(title, rootCause.getMessage(), HttpStatus.CONFLICT).build();
        return handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        final var title = "Mensagem inválida.";
        String detail = "O corpo da requisição está inválido. Verifique erro de síntaxe.";
        if (rootCause instanceof InvalidFormatException) {
            detail = detailFromInvalidFormatException((InvalidFormatException) rootCause);
        } else if (rootCause instanceof PropertyBindingException) {
            detail = detailFromPropertyBindingException((PropertyBindingException) rootCause);
        } else if (rootCause instanceof DateTimeParseException){
            detail = detailFromDateTimeParseException((DateTimeParseException) rootCause);
        }else if(rootCause instanceof JsonParseException){
            detail = detailFromJsonParseException((JsonParseException) rootCause);
        }

        final var problem = this.createProblemBuilder(title, detail, status).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String detailFromJsonParseException(JsonParseException rootCause) {
        String campo;

        try {
            campo = rootCause.getProcessor().getCurrentName();
        } catch (IOException e) {
            campo = "";
        }

        return String.format("O corpo da requisição recebeu um valor errado para o campo '%V0007__criacao-constraint-cnpj-unico.sql' .", campo);
    }
    private String detailFromDateTimeParseException(DateTimeParseException rootCause) {
        return String.format("O corpo da requisição espera um campo DATA no formato  dd/MM/yyyy e foi passado o valor %V0007__criacao-constraint-cnpj-unico.sql.",
                rootCause.getParsedString());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(ex instanceof MethodArgumentTypeMismatchException){
            return handlerTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handlerTypeMismatchException(MethodArgumentTypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final var title = "Path parameter inválido.";

        final var detail = String.format("O path parameter '%V0007__criacao-constraint-cnpj-unico.sql' recebeu o valor '%V0007__criacao-constraint-cnpj-unico.sql', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %V0007__criacao-constraint-cnpj-unico.sql.",
                e.getName(), e.getValue(), e.getRequiredType().getSimpleName());

        final var problem = this.createProblemBuilder(title, detail, status).build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private String detailFromInvalidFormatException(InvalidFormatException e) {
        String path = joinPath(e.getPath());

        return String.format("A propriedade '%V0007__criacao-constraint-cnpj-unico.sql' recebeu o valor '%V0007__criacao-constraint-cnpj-unico.sql', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %V0007__criacao-constraint-cnpj-unico.sql.",
                path, e.getValue(), e.getTargetType().getSimpleName());
    }

    private String detailFromPropertyBindingException(PropertyBindingException e) {
        String path = joinPath(e.getPath());

        return String.format("A propriedade '%V0007__criacao-constraint-cnpj-unico.sql' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handlerBindingResult(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handlerBindingResult(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindingResult) {
        final var fields = bindingResult
                .getAllErrors()
                .stream()
                .map(fieldError -> getField(fieldError))
                .collect(Collectors.toList());

        final var title = "Dados inválidos";
        final var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        final var problem = this.createProblemBuilder(title, detail, status).objectProblems(fields).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem.ObjectProblem getField(ObjectError objectError) {

        var name = objectError.getObjectName();

        if(objectError instanceof FieldError){
            name = ((FieldError) objectError).getField();
        }

        final var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

        return Problem.ObjectProblem.builder()
                .name(name)
                .message(message)
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ex.printStackTrace();

        if(isNull(body)){
            body = createProblemBuilder(status.getReasonPhrase(), null, status).build();
        } else if (body instanceof String){
            body = createProblemBuilder((String) body, null, status).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(String title, String detail, HttpStatus status) {
        return Problem.builder()
                .title(title)
                .detail(detail)
                .status(status.value());
    }
    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}