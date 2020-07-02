package com.lesteraguilar.teledoctest.models.services;

import com.lesteraguilar.teledoctest.models.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    public List<Usuario> findAll();

    public Usuario findByUsername(String username);

    public Usuario findByUsernameAndPassword(String username, String password);

    public Usuario findById(Long id);

    public Usuario save(Usuario usuario);

    public void delete(Long id);
}
