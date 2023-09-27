package com.alaska.securitylearn.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationErrorMessage {
    private HttpStatus status;
    List<String> messages = new ArrayList<>();
}
