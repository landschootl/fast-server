package com.main.fastserver.Service;

import com.main.fastserver.Repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for skill
 */
@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    public void deleteSkill(Long skill) {
        skillRepository.delete(skill);
    }
}
