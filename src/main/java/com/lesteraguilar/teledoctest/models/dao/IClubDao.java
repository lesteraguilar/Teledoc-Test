package com.lesteraguilar.teledoctest.models.dao;

import com.lesteraguilar.teledoctest.models.entity.Club;
import org.springframework.data.repository.CrudRepository;

public interface IClubDao extends CrudRepository<Club, Long> {
}
