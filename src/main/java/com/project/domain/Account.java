package com.project.domain;
import lombok.*;

/**
 * Entity
 */
@Getter
@Builder
@EqualsAndHashCode
public class Account {
    private String firstName;

    private String lastName;
}
