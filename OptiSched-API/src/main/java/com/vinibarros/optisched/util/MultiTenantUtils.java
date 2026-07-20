package com.vinibarros.optisched.util;

public class MultiTenantUtils {

    private MultiTenantUtils(){}

    public static Long resolveInstitutionId(String action, Long... candidateIds) {
        for (Long id : candidateIds) {
            if (id != null) {
                return id;
            }
        }
        throw new IllegalArgumentException("Institution Id is required to " + action + ".");
    }
}
