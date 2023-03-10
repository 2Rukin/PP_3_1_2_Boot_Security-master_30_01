package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class UserController {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String getUserInfo(Model model, Principal principal) {

        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        return "user";
    }


    @GetMapping("/admin")
    public String printUsersList(Model model) {
        model.addAttribute("users", userService.getUsersList());

        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {

        model.addAttribute("user",  new User());
        model.addAttribute("roles",roleService.getRoleList());
        return "new";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles",roleService.getRoleList());
        return "edit";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
//        user.getRolesSet();
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {

        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/edit/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
