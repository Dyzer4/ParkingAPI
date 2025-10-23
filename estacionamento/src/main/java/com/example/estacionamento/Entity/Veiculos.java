package com.example.estacionamento.Entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "veiculos")
public class Veiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String placa;

    @Column(nullable = false)
    private LocalTime horarioEntrada;

    @Column(nullable = false)
    private LocalDate dataEntrada;

    @Column
    private LocalTime horarioSaida;

    @Column
    private LocalDate dataSaida;

    @Column
    private Integer valorPago;

    public Veiculos() {}

    public Veiculos(String placa, LocalTime horarioEntrada, LocalDate dataEntrada, LocalTime horarioSaida, LocalDate dataSaida, Integer valorPago) {
        this.placa = placa;
        this.horarioEntrada = horarioEntrada;
        this.dataEntrada = dataEntrada;
        this.horarioSaida = horarioSaida;
        this.dataSaida = dataSaida;
        this.valorPago = valorPago;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public LocalTime getHorarioEntrada() { return horarioEntrada; }
    public void setHorarioEntrada(LocalTime horarioEntrada) { this.horarioEntrada = horarioEntrada; }
    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }
    public LocalTime getHorarioSaida() { return horarioSaida; }
    public void setHorarioSaida(LocalTime horarioSaida) { this.horarioSaida = horarioSaida; }
    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }
    public Integer getValorPago() { return valorPago; }
    public void setValorPago(Integer valorPago) { this.valorPago = valorPago; }

    public void calcularValor(int valorHora) {
        if (dataEntrada != null && horarioEntrada != null &&
                dataSaida != null && horarioSaida != null) {

            LocalDateTime entrada = LocalDateTime.of(dataEntrada, horarioEntrada);
            LocalDateTime saida = LocalDateTime.of(dataSaida, horarioSaida);

            Duration duracao = Duration.between(entrada, saida);
            long minutos = duracao.toMinutes();
            int horasCobradas = (int) Math.ceil(minutos / 60.0);

            if (horasCobradas == 0) {
                this.valorPago = valorHora;
            } else {
                this.valorPago = horasCobradas * valorHora;
            }
        }
    }
}