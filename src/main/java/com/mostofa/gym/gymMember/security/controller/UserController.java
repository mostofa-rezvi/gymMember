package com.mostofa.gym.gymMember.security.controller;

import com.mostofa.gym.gymMember.security.entity.User;
import com.mostofa.gym.gymMember.security.service.UserService;
import com.mostofa.gym.gymMember.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public ApiResponse saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/updateUser")
    public ApiResponse updateUser(@RequestBody User user) {
        return userService.update(user);
    }


    @DeleteMapping("/deleteById/{id}")
    public ApiResponse deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
