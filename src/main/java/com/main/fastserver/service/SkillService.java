package com.main.fastserver.service;

import com.main.fastserver.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * service for skill
 */
@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    public void deleteSkill(Long skill) {
        skillRepository.delete(skill);
    }
}
