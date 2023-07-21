package pt.itsector.sb.demo.springbootdemo.controller;

import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.itsector.sb.demo.springbootdemo.dto.UserDtoReq;
import pt.itsector.sb.demo.springbootdemo.dto.UserDtoRes;
import pt.itsector.sb.demo.springbootdemo.filter.RequestConstants;
import pt.itsector.sb.demo.springbootdemo.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private HttpServletRequest request;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Observed(name = "add.users")
    @PostMapping("/add")
    ResponseEntity<UserDtoRes> addUser(@RequestBody UserDtoReq userDto){
        this.setAPIId(103);
        UserDtoRes userDtoRes = userService.addUser(userDto);
        return ResponseEntity.ok(userDtoRes);
    }

    @Observed(name = "update.user")
    @PutMapping("/{id}")
    ResponseEntity<String> updateUser(@RequestBody UserDtoReq userDto, @PathVariable long id){
        this.setAPIId(104);
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
        this.setAPIId(101);
        List<UserDtoRes> userDtoResList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoResList);
    }

    private void setAPIId(final int apiId) {
        this.request.setAttribute(RequestConstants.API_ID,apiId);
    }
}

