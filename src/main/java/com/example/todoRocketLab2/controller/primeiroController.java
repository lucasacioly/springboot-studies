package com.example.todoRocketLab2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
public class primeiroController {

    /*
    *
    *   GET     - Buscar uma informação
    *   POST    - criar um dado/info
    *   PUT     - Alterar um dado/info
    *   DELETE  - apagar um dado/info
    *   PATCH   - alterar uma parte de um dado/info
    *
    * */

    @GetMapping("/hello")
    public String hello(){
        return "helloWorld";
    }
}
