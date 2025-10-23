package com.example.estacionamento.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.LocalTime;

public class VeiculosDTO {
    private String placa;
    private LocalDate dataEntrada;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horarioEntrada;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate dataSaida;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horarioSaida;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer valorPago;

    public VeiculosDTO() {}

    public VeiculosDTO(String placa, LocalDate dataEntrada, LocalTime horarioEntrada, LocalDate dataSaida, LocalTime horarioSaida, Integer valorPago) {
        this.placa = placa;
        this.dataEntrada = dataEntrada;
        this.horarioEntrada = horarioEntrada;
        this.dataSaida = dataSaida;
        this.horarioSaida = horarioSaida;
        this.valorPago = valorPago;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public LocalTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public Integer getValorPago() {
        return valorPago;
    }

    public void setValorPago(Integer valorPago) {
        this.valorPago = valorPago;
    }
}