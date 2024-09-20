package com.nmt.groceryfinder.modules.users.controllers;


import com.nmt.groceryfinder.modules.users.domain.model.dtos.UserDto;
import com.nmt.groceryfinder.modules.users.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDto>> getOneById(@PathVariable UUID id) {
        Optional<UserDto> userDto = this.userService.getOneById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("")
    public  ResponseEntity<UserDto> createOne(@RequestBody UserDto data) {
        UserDto userCreated = this.userService.createOne(data);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateOneById(@PathVariable UUID id, @RequestBody UserDto data) {
        UserDto userUpdated = this.userService.updateOneById(id, data);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable UUID id) {
        userService.deleteOneById(id);
        try {
            this.userService.getOneById(id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("")
    public ResponseEntity<Page<UserDto>> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> roles = this.userService.getAllPaginated(pageable);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
