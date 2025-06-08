package br.com.fiap.watchtower.model;

public class SensorData {
    private double temperatura;
    private double umidade;
    private double pressao;
    private double vento;
    private double chuva;
    private double nivelAgua;
    private double gases;
    private double luminosidade;
    private String evento;

    public SensorData(double temperatura,
                      double umidade,
                      double pressao,
                      double vento,
                      double chuva,
                      double nivelAgua,
                      double gases,
                      double luminosidade) {
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.pressao = pressao;
        this.vento = vento;
        this.chuva = chuva;
        this.nivelAgua = nivelAgua;
        this.gases = gases;
        this.luminosidade = luminosidade;
    }

    // Getters e Setters
    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public double getPressao() {
        return pressao;
    }

    public void setPressao(double pressao) {
        this.pressao = pressao;
    }

    public double getVento() {
        return vento;
    }

    public void setVento(double vento) {
        this.vento = vento;
    }

    public double getChuva() {
        return chuva;
    }

    public void setChuva(double chuva) {
        this.chuva = chuva;
    }

    public double getNivelAgua() {
        return nivelAgua;
    }

    public void setNivelAgua(double nivelAgua) {
        this.nivelAgua = nivelAgua;
    }

    public double getGases() {
        return gases;
    }

    public void setGases(double gases) {
        this.gases = gases;
    }

    public double getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(double luminosidade) {
        this.luminosidade = luminosidade;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "temperatura=" + temperatura +
                ", umidade=" + umidade +
                ", pressao=" + pressao +
                ", vento=" + vento +
                ", chuva=" + chuva +
                ", nivelAgua=" + nivelAgua +
                ", gases=" + gases +
                ", luminosidade=" + luminosidade +
                ", evento='" + evento + '\'' +
                '}';
    }
} 