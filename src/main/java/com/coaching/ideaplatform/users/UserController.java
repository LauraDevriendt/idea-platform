package com.coaching.ideaplatform.users;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = service.showOnlyUsersWithPublicIdeas().stream()
                .map(UserDTO::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(UserDTO.toChildDto(service.getUser(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user) {
        return new ResponseEntity<>(UserDTO.toChildDto(service.addUser(user.toEntity())), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto, @PathVariable Long id) {
        User user = userDto.toEntity();
        user.setId(id);
        return new ResponseEntity<>(UserDTO.toChildDto(service.updateUser(user)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>("User with id " + id + " is deleted", HttpStatus.OK);
    }
}
