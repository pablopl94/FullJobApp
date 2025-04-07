package com.fulljob.api.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.repository.IUsuarioRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clientes")
public class ClienteRestController {

    @Autowired
    private IUsuarioRepository usuarioRepository;



}
