package com.psyclinicSolutions.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationException {
    private Instant timestamp;
    private Integer status;
    private Map<String, String> errors = new HashMap<>();
    private String path;
}
