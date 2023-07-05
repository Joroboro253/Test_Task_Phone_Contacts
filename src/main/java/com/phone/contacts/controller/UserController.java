package com.phone.contacts.controller;

import com.phone.contacts.dao.ContactRepository;
import com.phone.contacts.dao.UserRepository;
import com.phone.contacts.entities.Contact;
import com.phone.contacts.entities.User;
import com.phone.contacts.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String username = principal.getName();
        System.out.println("USER_NAME" + username);

        User user = userRepository.getUserByUserName(username);
        model.addAttribute("user", user);
        System.out.println(user);
    }

    // home

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "User DashBoard");

        String username = principal.getName();
        User user = userRepository.getUserByUserName(username);
        model.addAttribute("person", user.getName());
        model.addAttribute("date", new Date());
        return "normal/user_dashboard";
    }

    // open the add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        return "normal/add_contact_form";
    }

    // Submit contact form

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, Principal principal, @RequestParam("profileImage")MultipartFile file, HttpSession session) {
        System.out.println(file.getOriginalFilename());

        try{
            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);

            if(file.isEmpty())
            {
                System.out.println("File is empty");
                contact.setImage("contact.png");
            }
            else {
                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File uploaded successfully");
            }

            contact.setUser(user);

            user.getContacts().add(contact);

            this.userRepository.save(user);

            System.out.println("DATA" + contact);

            System.out.println("Contact added to database");
            // message success added succesfully
            session.setAttribute("message", new Message("Your contact is added !!! Add more", "success"));

        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage());
            e.printStackTrace();

            //message error : user not added
            session.setAttribute("message", new Message("Some Thing went wrong !!! Try again", "danger"));
        }

        return "normal/add_contact_form";
    }

    //Show contacts handler
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model m, Principal principal){
        m.addAttribute("title", "Show UserContacts");

        // send the list of contact from database
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);

        Pageable pageable = PageRequest.of(page, 5);
        Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(), pageable);

        m.addAttribute("contacts", contacts);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPage", contacts.getTotalPages());

        return "normal/show_contacts";
    }

    // handler for the email click to show user profile
    @GetMapping("/{cid}/contact")
    public String showContactDetails(@PathVariable("cid") Integer cid, Model model, Principal principal) {
        Optional<Contact> contactOptional = this.contactRepository.findById(cid);
        Contact contact = contactOptional.get();

        String username = principal.getName();
        User user = this.userRepository.getUserByUserName(username);

        if(user.getId() == contact.getUser().getId()) {
            model.addAttribute("contact", contact);
            model.addAttribute("title", contact.getName());
        }

        return "normal/contact_detail";
    }

    @GetMapping("/delete/{cid}")
    public String deleteContact(@PathVariable("cid") Integer cid, Model m, HttpSession session, Principal principal) {
        System.out.println("CID : " + cid);
        Contact contact = this.contactRepository.findById(cid).get();

        // Removing the contact  data from data base use the
        User user = this.userRepository.getUserByUserName(principal.getName());
        user.getContacts().remove(contact);
        this.userRepository.save(user);

        session.setAttribute("message", new Message("Contact deleted Successfully", "success"));
        return "redirect:/user/show-contacts/0";
    }

    //   open update form folder
    @PostMapping("/update-contact/{cid}")
    public String updateForm(@PathVariable("cid") Integer cid, Model m) {
        m.addAttribute("title", "Update Contact");
        Contact contact = this.contactRepository.findById(cid).get();

        m.addAttribute("contact", contact);
        return "/normal/update_form";
    }

    // the update form submission

    @PostMapping("/process-update")
    public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Model m, HttpSession session, Principal principal)
    {
        try {
            Contact oldContactDetail = this.contactRepository.findById(contact.getCid()).get();
            // if user add new image
            if(!file.isEmpty())
            {
                // deleting the old photo from folder : get the path
                File deletefile = new ClassPathResource("static/img").getFile();
                File file1 = new File(deletefile, oldContactDetail.getImage());
                file1.delete();
                // update the new Photo in file
                File saveFile = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                contact.setImage(file.getOriginalFilename()); // saving the name in database
            } // file same as old
            else {
                contact.setImage(oldContactDetail.getImage());
            }
            //saving the current user
            User user = this.userRepository.getUserByUserName(principal.getName());
            contact.setUser(user);
            this.contactRepository.save(contact);
            session.setAttribute("message", new Message("Your contact updated ", "success"));
        } catch (Exception e) {
            System.out.println("CONTACT" + contact.getName());

        }
        return "redirect:/user/" + contact.getCid() + "/contact";
    }

    @GetMapping("/profile")
    public String yourProfile(Model m) {
        m.addAttribute("title", "Profile Page");

        return "normal/profile";
    }
}
