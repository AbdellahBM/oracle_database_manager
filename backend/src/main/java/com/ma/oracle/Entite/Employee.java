package com.ma.oracle.Entite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
}
