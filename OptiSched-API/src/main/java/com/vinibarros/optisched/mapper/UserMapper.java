package com.vinibarros.optisched.mapper;

import com.vinibarros.optisched.dto.request.UserRequest;
import com.vinibarros.optisched.dto.response.UserResponse;
import com.vinibarros.optisched.entity.Institution;
import com.vinibarros.optisched.entity.User;
import com.vinibarros.optisched.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request, UserRole role, Institution institution){
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(role);
        user.setInstitution(institution);

        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
