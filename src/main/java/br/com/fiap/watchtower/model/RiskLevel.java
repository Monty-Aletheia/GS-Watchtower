package br.com.fiap.watchtower.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RiskLevel {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL;

    @JsonCreator
    public static RiskLevel fromString(String value) {
        if (value == null || value.isBlank()) {
            return null; // ou RiskLevel.LOW, se quiser um padrão
        }
        return RiskLevel.valueOf(value.toUpperCase());
    }
} 