package com.project.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Account {
    private long id;

    private String firstName;

    private String secondName;
}
