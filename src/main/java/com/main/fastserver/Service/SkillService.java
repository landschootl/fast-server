package com.main.fastserver.Service;

import com.main.fastserver.Repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    public void delete(String subDomain, String skill) {
        skillRepository.delete(subDomain, skill);
    }
}
