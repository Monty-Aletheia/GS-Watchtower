package br.com.fiap.watchtower.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Embedded;
import java.time.LocalDateTime;

@Entity
public class RiskPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double latitude;
    private Double longitude;
    private String desasterType;
    private String markerType;
    private String markerName;
    private String markerImage;
    private RiskLevel riskLevel;
    private LocalDateTime timestamp;
    private String aiAnalysis;
    private String description;

    @Embedded
    private SensorData sensorData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDesasterType() {
        return desasterType;
    }

    public void setDesasterType(String desasterType) {
        this.desasterType = desasterType;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getMarkerImage() {
        return markerImage;
    }

    public void setMarkerImage(String markerImage) {
        this.markerImage = markerImage;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    public SensorData getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData sensorData) {
        this.sensorData = sensorData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RiskPoint{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", desasterType='" + desasterType + '\'' +
                ", markerType='" + markerType + '\'' +
                ", markerName='" + markerName + '\'' +
                ", markerImage='" + markerImage + '\'' +
                ", riskLevel=" + riskLevel +
                ", timestamp=" + timestamp +
                ", aiAnalysis='" + aiAnalysis + '\'' +
                ", sensorData=" + sensorData +
                '}';
    }
}