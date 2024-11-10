package com.webstore.dto.response;

import com.webstore.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String firstName;
    private String lastName;

    public UserResponseDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
