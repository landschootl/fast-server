package com.davidson.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for skill
 */
@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    /**
     * delete relation ship between subdomain and skill
     * @param skill to be deleted relation ship
     */
    public void deleteSkill(Long skill) {
        skillRepository.deleteSkill(skill);
    }
}
