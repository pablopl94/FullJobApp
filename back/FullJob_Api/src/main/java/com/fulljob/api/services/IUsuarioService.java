package com.fulljob.api.services;

import com.fulljob.api.models.dto.RegistroRequestDto;
import com.fulljob.api.models.entities.Usuario;



public interface IUsuarioService extends IGenericCrud<Usuario, String> {

    String auth(String email, String password);

    Usuario altaCandidato(RegistroRequestDto dto);

}