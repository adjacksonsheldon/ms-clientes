package com.asps.clientes.api.exception;

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
    private List<ObjectProblem> objectProblems;

    @Getter
    @Builder
    public static class ObjectProblem {
        private String name;
        private String message;
    }
}
