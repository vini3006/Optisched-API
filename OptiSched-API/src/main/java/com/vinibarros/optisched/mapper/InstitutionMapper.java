package com.vinibarros.optisched.mapper;

import com.vinibarros.optisched.dto.request.InstitutionRequest;
import com.vinibarros.optisched.dto.response.InstitutionResponse;
import com.vinibarros.optisched.entity.Institution;
import org.springframework.stereotype.Component;

@Component
public class InstitutionMapper {

    public Institution toEntity(InstitutionRequest request) {
        Institution institution = new Institution();

        institution.setName(request.name());
        institution.setCnpj(request.cnpj());

        return institution;
    }

    public InstitutionResponse toResponse(Institution institution){
        return new InstitutionResponse(
                institution.getId(),
                institution.getName(),
                institution.getCnpj(),
                institution.getSubscriptionStatus(),
                institution.getExpiresAt()
        );
    }
}
