package com.webstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class SignUpRequestDTO {

    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;

    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastName;

    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Size(min = 8, max = 255, message = "Длина пароля должна содержать от 8 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
