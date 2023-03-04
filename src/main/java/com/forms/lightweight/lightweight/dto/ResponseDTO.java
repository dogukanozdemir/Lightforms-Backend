package com.forms.lightweight.lightweight.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {

    private T data;

    private String message;
}
