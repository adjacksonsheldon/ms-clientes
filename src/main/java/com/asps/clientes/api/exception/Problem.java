package com.asps.clientes.api.exception;

import com.azure.core.annotation.Get;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class Problem {
    private Integer status;
    private String type;
    private String title;
    private String detail;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private String name;
        private String message;
    }
}
