package br.com.ifba.emailmicroservice.DTOs;

public record EmailDTO(String mailFrom, String mailTo, String mailSubject, String mailText) {

}
