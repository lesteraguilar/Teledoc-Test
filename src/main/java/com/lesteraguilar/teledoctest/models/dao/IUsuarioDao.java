package com.lesteraguilar.teledoctest.models.dao;

import com.lesteraguilar.teledoctest.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {


    Usuario findByUsername(String username);

    Usuario findByUsernameAndPassword(String username, String password);

}
