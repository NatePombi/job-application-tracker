package com.nate.jobapplicationtracker.mapper;

import com.nate.jobapplicationtracker.dto.UserDto;
import com.nate.jobapplicationtracker.model.JobApplication;
import com.nate.jobapplicationtracker.model.User;

import java.util.List;

public class UserMapper {

    public static User toEntity(UserDto dto){
        if(dto == null){
            return null;
        }

        User user = new User();

        user.setId(dto.getId());
        user.setUsername(user.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setJobApplicationList(dto.getJobApplicationList());

        return user;
    }


    public static UserDto toDto(User user){
        if(user == null){
            return  null;
        }


        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getJobApplicationList()
        );
    }
}
