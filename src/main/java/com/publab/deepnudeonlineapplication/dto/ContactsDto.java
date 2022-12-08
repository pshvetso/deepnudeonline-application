package com.publab.deepnudeonlineapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class ContactsDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String text;
}
