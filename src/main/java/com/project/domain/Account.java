package com.project.domain;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class Account {
    private String firstName;

    private String lastName;
}
