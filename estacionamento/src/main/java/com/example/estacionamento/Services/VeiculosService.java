package com.example.estacionamento.Services;

import com.example.estacionamento.DTO.VeiculosDTO;
import com.example.estacionamento.Entity.Veiculos;
import com.example.estacionamento.Repository.VeiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculosService {

    @Autowired
    private VeiculosRepository veiculosRepository;

    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    public List<Veiculos> listarVeiculosAtivos() {
        return veiculosRepository.findByHorarioSaidaNull();
    }

    public Veiculos buscarPorId(int id) {
        return veiculosRepository.findById(id).orElse(null);
    }

    public List<Veiculos> buscarPorPlaca(String placa) {
        return veiculosRepository.findByPlaca(placa);
    }

    public VeiculosDTO liberarEntrada(Veiculos veiculo) {
        // Impede entrada duplicada (veículo ativo)
        if (veiculosRepository.existsByPlacaAndHorarioSaidaNull(veiculo.getPlaca())) {
            throw new RuntimeException("Veículo já está registrado como ativo!");
        }

        LocalDateTime agora = LocalDateTime.now(ZONE_ID);
        veiculo.setDataEntrada(agora.toLocalDate());
        veiculo.setHorarioEntrada(agora.toLocalTime());

        Veiculos salvo = veiculosRepository.save(veiculo);
        return convertToDTO(salvo);
    }

    public VeiculosDTO liberarSaida(Veiculos veiculos) {
        Veiculos veiculo = veiculosRepository.findByPlacaAndHorarioSaidaNull(veiculos.getPlaca())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado ou já saiu!"));

        LocalDateTime agora = LocalDateTime.now(ZONE_ID);
        veiculo.setDataSaida(agora.toLocalDate());
        veiculo.setHorarioSaida(agora.toLocalTime());
        veiculo.calcularValor(10);

        Veiculos salvo = veiculosRepository.save(veiculo);
        return convertToDTO(salvo);
    }

    public VeiculosDTO convertToDTO(Veiculos veiculo) {
        return new VeiculosDTO(
                veiculo.getPlaca(),
                veiculo.getDataEntrada(),
                veiculo.getHorarioEntrada(),
                veiculo.getDataSaida(),
                veiculo.getHorarioSaida(),
                veiculo.getValorPago()
        );
    }
}