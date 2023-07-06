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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;



import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserRepository userRepository;
    private ContactRepository contactRepository;

    @Autowired
    public UserController(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            System.out.println("USER_NAME" + username);

            User user = userRepository.getUserByUserName(username);
            model.addAttribute("user", user);
            System.out.println(user);
        }
    }

    // home

    @GetMapping("/index")
    public ResponseEntity<String> dashboard(Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.getUserByUserName(username);
            return ResponseEntity.ok("User Dashboard - " + user.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    // open the add form handler
    @PostMapping("/add-contact")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact, Principal principal) {
        String name = principal.getName();
        User user = userRepository.getUserByUserName(name);

        contact.setUser(user);
        user.getContacts().add(contact);
        userRepository.save(user);

        return new ResponseEntity<>(contact, HttpStatus.CREATED);
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
    public ResponseEntity<?> showContacts(@PathVariable("page") Integer page, Principal principal){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        Pageable pageable = PageRequest.of(page, 5); // you can change the number of rows per page
        Page<Contact> contacts = contactRepository.findContactByUser(user.getId(), pageable);

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }


    // handler for the email click to show user profile
    @GetMapping("/{cid}/contact")
    public ResponseEntity<?> showContactDetails(@PathVariable("cid") Integer cid, Principal principal) {
        Optional<Contact> contactOptional = contactRepository.findById(cid);
        if (contactOptional.isPresent()){
            Contact contact = contactOptional.get();
            String username = principal.getName();
            User user = userRepository.getUserByUserName(username);
            if(user.getId() == contact.getUser().getId()) {
                return ResponseEntity.ok(contact);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Access denied", "danger"));
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/delete/{cid}")
    public ResponseEntity<?> deleteContact(@PathVariable("cid") Integer cid, HttpSession session, Principal principal) {
        Optional<Contact> contactOptional = contactRepository.findById(cid);
        if(contactOptional.isPresent()){
            Contact contact = contactOptional.get();
            User user = userRepository.getUserByUserName(principal.getName());
            if(user.getId() == contact.getUser().getId()) {
                user.getContacts().remove(contact);
                userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //   open update form folder
    @PutMapping("/update-contact/{cid}")
    public ResponseEntity<?> updateForm(@PathVariable("cid") Integer cid, @RequestBody Contact updatedContact, Principal principal) {
        Optional<Contact> contactOptional = contactRepository.findById(cid);
        if(contactOptional.isPresent()) {
            Contact existingContact = contactOptional.get();
            User user = userRepository.getUserByUserName(principal.getName());
            if(user.getId() == existingContact.getUser().getId()) {
                existingContact.setName(updatedContact.getName());
                existingContact.setEmails(updatedContact.getEmails());
                existingContact.setPhones(updatedContact.getPhones());
                contactRepository.save(existingContact);
                return new ResponseEntity<>(existingContact, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<User> userProfile(Principal principal) {
        String username = principal.getName();
        User user = userRepository.getUserByUserName(username);
        return ResponseEntity.ok(user);
    }
}
