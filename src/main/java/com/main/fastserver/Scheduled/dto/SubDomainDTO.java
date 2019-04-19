package com.main.fastserver.Scheduled.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubDomainDTO {

    private String title;
    private List<SkillDTO> skills;

}
