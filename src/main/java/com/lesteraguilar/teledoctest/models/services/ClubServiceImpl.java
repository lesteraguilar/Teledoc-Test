package com.lesteraguilar.teledoctest.models.services;

import com.lesteraguilar.teledoctest.models.dao.IClubDao;
import com.lesteraguilar.teledoctest.models.entity.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements IClubService{

    @Autowired
    private IClubDao clubDao;

    @Override
    public List<Club> findAll() {
        return (List<Club>) clubDao.findAll();
    }

    @Override
    public Club findById(Long id) {
        return clubDao.findById(id).orElse(null);
    }

    @Override
    public Club save(Club club) {
        return clubDao.save(club);
    }

    @Override
    public void delete(Long id) {
        clubDao.deleteById(id);
    }
}
