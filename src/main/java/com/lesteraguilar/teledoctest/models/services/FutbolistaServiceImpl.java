package com.lesteraguilar.teledoctest.models.services;

import com.lesteraguilar.teledoctest.models.dao.IFutbolistaDao;
import com.lesteraguilar.teledoctest.models.entity.Futbolista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FutbolistaServiceImpl implements IFutbolistaService {

    @Autowired
    private IFutbolistaDao futbolistaDao;

    @Override
    public List<Futbolista> findAll() {
        return (List<Futbolista>) futbolistaDao.findAll();
    }

    @Override
    public Futbolista findById(Long id) {
        return futbolistaDao.findById(id).orElse(null);
    }

    @Override
    public Futbolista save(Futbolista futbolista) {
        return futbolistaDao.save(futbolista);
    }

    @Override
    public void delete(Long id) {
        futbolistaDao.deleteById(id);
    }
}
