package com.example.todoRocketLab2.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
*   Modifiers
*   public      - acessivel por todas as classes
*   private     - acessivel em outras classes apenas por getters e setters
*   protected   - só se acessa dentro da classe
* */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    /*
    *
    * String (texto)
    * Integer (int) numeros inteiros
    * Double (double) decimais
    * Float (float) decimais
    * char (caracteres)
    * Date (datas)
    * void
    *
    * */
    /*
    *
    * @RequestBody - Anotation para recuperar dados no body da requisição http
    *
    * */
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel userModel){
       if (this.userRepository.findByEmail(userModel.getEmail()) != null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email já cadastrado");
       }
       if (this.userRepository.findByUsername(userModel.getUsername()) != null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuário já cadastrado");
       }
       var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

       userModel.setPassword(passwordHashed);

       var userCreated = this.userRepository.save(userModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
