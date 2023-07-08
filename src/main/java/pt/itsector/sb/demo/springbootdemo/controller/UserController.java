package pt.itsector.sb.demo.springbootdemo.controller;

import io.micrometer.observation.annotation.Observed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.itsector.sb.demo.springbootdemo.dto.UserDtoReq;
import pt.itsector.sb.demo.springbootdemo.dto.UserDtoRes;
import pt.itsector.sb.demo.springbootdemo.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Observed(name = "add.users")
    @PostMapping("/add")
    ResponseEntity<UserDtoRes> addUser(@RequestBody UserDtoReq userDto){
        UserDtoRes userDtoRes = userService.addUser(userDto);
        return ResponseEntity.ok(userDtoRes);
    }

    @Observed(name = "update.user")
    @PutMapping("/{id}")
    ResponseEntity<String> updateUser(@RequestBody UserDtoReq userDto, @PathVariable long id){
        try {
            userService.updateUser(userDto,id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("User not found");
        }
        return ResponseEntity.ok("User updated successfully.");
    }

    @Observed(name = "get.users")
    @GetMapping
    ResponseEntity<List<UserDtoRes>> getAllUsers(){
        List<UserDtoRes> userDtoResList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoResList);
    }
}

