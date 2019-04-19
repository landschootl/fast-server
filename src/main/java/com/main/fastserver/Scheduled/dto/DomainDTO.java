package com.main.fastserver.Scheduled.dto;

import com.main.fastserver.Entity.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DomainDTO {

    private String title;
    private String icon;
    private List<SubDomainDTO> subDomains;

}
