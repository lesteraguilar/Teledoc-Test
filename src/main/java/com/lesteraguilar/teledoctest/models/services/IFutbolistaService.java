package com.lesteraguilar.teledoctest.models.services;

import com.lesteraguilar.teledoctest.models.entity.Futbolista;

import java.util.List;

public interface IFutbolistaService {

    public List<Futbolista> findAll();

    public Futbolista findById(Long id);

    public Futbolista save(Futbolista futbolista);

    public void delete(Long id);

}
