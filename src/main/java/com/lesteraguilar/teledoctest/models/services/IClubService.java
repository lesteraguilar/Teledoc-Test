package com.lesteraguilar.teledoctest.models.services;

import com.lesteraguilar.teledoctest.models.entity.Club;

import java.util.List;

public interface IClubService {

    public List<Club> findAll();

    public Club findById(Long id);

    public Club save(Club club);

    public void delete(Long id);


}

