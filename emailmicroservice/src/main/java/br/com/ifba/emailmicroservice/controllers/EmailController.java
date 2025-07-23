package br.com.ifba.emailmicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.emailmicroservice.DTOs.EmailDTO;
import br.com.ifba.emailmicroservice.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping("/send")
    public ResponseEntity<EmailDTO> sendEmail(@RequestBody EmailDTO data) {

        return new ResponseEntity<EmailDTO>(service.sendEmail(data), HttpStatus.CREATED);
    }
}
