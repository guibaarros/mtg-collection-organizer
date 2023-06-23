package com.mtgcollectionorganizer.api.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private String code;
    private String message;
    private String localizedMessage;
}
