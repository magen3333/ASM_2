package net.javaguides.springboot.springsecurity.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class PasswordEncoder {

     public static void main(String[] args){

         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         String rewpassword = "alex2020";
         String encodedPassword = encoder.encode(rewpassword);

         System.out.println(encodedPassword);
     }

}
