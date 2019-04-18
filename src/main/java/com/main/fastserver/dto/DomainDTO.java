package com.main.fastserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
