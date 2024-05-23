package com.psyclinicSolutions.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationException {
    private Instant timestamp;
    private Integer status;
    private List<Map<String, String>> errors = new ArrayList<>();
    private String path;
}
