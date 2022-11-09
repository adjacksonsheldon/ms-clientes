package com.asps.clientes.api.helper;

import com.asps.clientes.api.exception.ValidacaoException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Map;

@Component
@AllArgsConstructor
public class MapToObjectHelper<T> {

    private final SmartValidator validator;

    public T merge(Map<String, Object> mapSource, T target, HttpServletRequest request, Class<T> toValueType, Object... validationHints) throws MethodArgumentNotValidException {

        final T objectSource = objectSourceFromMap(mapSource, request, toValueType);

        mapSource.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(toValueType, key);
            field.setAccessible(true);

            Object valorAtualizado = ReflectionUtils.getField(field, objectSource);

            ReflectionUtils.setField(field, target, valorAtualizado);
        });

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, toValueType.getSimpleName().toLowerCase());
        validator.validate(target, bindingResult, validationHints);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }

        return target;
    }

    private T objectSourceFromMap(Map<String, Object> mapSource, HttpServletRequest request, Class<T> toValueType) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            return objectMapper.convertValue(mapSource, toValueType);

        }catch (IllegalArgumentException e){
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }
}
