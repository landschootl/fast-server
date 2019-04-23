package com.main.fastserver.Scheduled.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DomainDTO {

    private String title;
    private String icon;
    private List<SubDomainDTO> subDomains;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainDTO domainDTO = (DomainDTO) o;
        return title.equals(domainDTO.title);
    }

}
