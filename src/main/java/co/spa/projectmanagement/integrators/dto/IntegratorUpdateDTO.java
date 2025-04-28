package co.spa.projectmanagement.integrators.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class IntegratorUpdateDTO {

    @Size(min = 2, max = 100)
    private String name;

    @Email
    private String email;

    @NotNull
    private String phone;

    private String address;

}