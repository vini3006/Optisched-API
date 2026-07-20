package com.vinibarros.optisched.config;

public class InstitutionContext {

    private static final ThreadLocal<Long> currentInstitution = new ThreadLocal<>();

    public static void setCurrentInstitution(Long institutionId) {
        currentInstitution.set(institutionId);
    }

    public static Long getCurrentInstitution() {
        return currentInstitution.get();
    }

    public static void clear() {
        currentInstitution.remove();
    }
}
