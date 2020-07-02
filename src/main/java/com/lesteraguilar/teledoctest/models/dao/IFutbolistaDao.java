package com.lesteraguilar.teledoctest.models.dao;

import com.lesteraguilar.teledoctest.models.entity.Futbolista;
import org.springframework.data.repository.CrudRepository;

public interface IFutbolistaDao extends CrudRepository<Futbolista, Long> {
}
