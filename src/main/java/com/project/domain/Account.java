package com.project.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
