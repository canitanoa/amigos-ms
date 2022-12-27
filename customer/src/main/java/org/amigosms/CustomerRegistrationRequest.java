package org.amigosms;

public record CustomerRegistrationRequest(
    String firstName,
    String lastName,
    String email){

}
