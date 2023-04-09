package com.digitlibraryproject.security;

import com.digitlibraryproject.domain.request.RegistrationUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;


@RestController
@RequestMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecurityController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpResponse> registrationUser(@RequestBody RegistrationUser registrationUser) {
        return new ResponseEntity<>(securityService.registration(registrationUser) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }


    @PutMapping("/user/{id}/admin")
    public ResponseEntity<HttpResponse> updateUserToAdmin(@PathVariable int id) {
        return new ResponseEntity<>(securityService.updateUserToAdmin(id) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PutMapping("/admin/{id}/user")
    public ResponseEntity<HttpResponse> updateAdminToUser(@PathVariable int id) {
        return new ResponseEntity<>(securityService.updateAdminToUser(id) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PutMapping("/user/{id}/subscriber")
    public ResponseEntity<HttpResponse> updateUserToSubscriber(@PathVariable int id) {
        return new ResponseEntity<>(securityService.updateUserToSubscriber(id) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }
}
