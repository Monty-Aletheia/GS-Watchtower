package br.com.fiap.watchtower.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RiskPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Double latitude;
    private Double longitude;
    private String desasterType;
    private String markerType;
    private LocalDateTime timestamp;
    private String aiAnalysis;
    private String description;
    private RiskLevel riskLevel = RiskLevel.LOW;

    @Embedded
    private SensorData sensorData;

    public RiskPoint() {}

}