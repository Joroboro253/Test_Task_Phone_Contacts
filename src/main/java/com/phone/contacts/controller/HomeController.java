package com.phone.contacts.controller;

import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.User;
import com.phone.contacts.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String home(Model m){
        m.addAttribute("title", "Home -Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model m){
        m.addAttribute("title", "About -Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model m) {
        m.addAttribute("title", "Register Here -Smart Contact Manager");
        m.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1, @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement, Model model, HttpSession session) {
        try {
            if(!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("U have not agreed the terms and conditions");
            }
            if(result1.hasErrors())
            {
                System.out.println("ERROR " + result1.toString());
                model.addAttribute("user", user);
                return "signup";
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            System.out.println("Agreement " + agreement);
            System.out.println("USER " + user);

            // saving data to database
            User result = this.userRepository.save(user);
            model.addAttribute("message", new Message("SuccessFully Registered !!!", "alert-success"));

            return "signup";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong !!! " + e.getMessage(), "alert-danger "));
            return "signup";
        }
    }

    @GetMapping("/signin")
    public String customlogin(Model m){
        m.addAttribute("title", "The Login Page");
        return "login";
    }

}
