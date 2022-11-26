package com.ip.hw8.controller;

import com.ip.hw8.entity.Role;
import com.ip.hw8.entity.User;
import com.ip.hw8.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/main")
    public ModelAndView main(){
        return new ModelAndView("index");
    }

    @GetMapping("/registrationNewUser")
    public ModelAndView createForm() {

        return new ModelAndView("registration");
    }

    @PostMapping("/registrationNewUser")
    public String  createUser(@RequestParam(name = "email") String email,
                                   @RequestParam(name = "password") String password,
                                   @RequestParam(name = "firstName") String firstName,
                                   @RequestParam(name = "lastName") String lastName,
                              Map<String,Object> model) {
        User userAudit = userRepository.findByEmailUser(email);
        if (userAudit != null) {
            model.put("message","User with this name already exist!!!\n" +
                    "Try again.");
            return "registration";
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoles(Collections.singletonList(new Role("ROLE_USER")));
        userRepository.save(user);
        return "redirect:/login";
    }
}

