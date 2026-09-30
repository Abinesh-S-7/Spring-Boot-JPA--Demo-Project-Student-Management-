package dev.student.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    private String city;
    private String street;

    @Column(name = "zip_code")
    @Pattern(regexp = "^[0-9]{6}$",message = "Enter your 6-digit postal code.")
    private String zipcode;
}