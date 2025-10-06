package com.example.estacionamento.Services;

import com.example.estacionamento.DTO.VeiculosDTO;
import com.example.estacionamento.Entity.Veiculos;
import com.example.estacionamento.Repository.VeiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculosService {
    @Autowired
    private VeiculosRepository veiculosRepository;

    public List<Veiculos> listarVeiculosAtivos() {
        // Confirme que o método se chama findByDataEntradaNull ou similar.
        return veiculosRepository.findByDataSaidaNull();
    }

    public Veiculos buscarPorId(int id) {
        return veiculosRepository.findById(id).orElse(null);
    }

    public List<Veiculos> buscarPorPlaca(String placa) {
        return veiculosRepository.findByPlaca(placa);
    }

    public VeiculosDTO liberarEntrada(Veiculos veiculo) {
        Optional<Veiculos> veiculoExistente = veiculosRepository.findByPlacaActive(veiculo.getPlaca());

        if (veiculoExistente.isEmpty()) {
            veiculo.setDataEntrada(LocalDate.now());
            veiculo.setHorarioEntrada(LocalTime.now());

            Veiculos salvo = veiculosRepository.save(veiculo);
            return convertToDTO(salvo);
        } else {
            throw new RuntimeException("Veículo já está registrado como ativo!");
        }
    }

    public VeiculosDTO liberarSaida(Veiculos veiculos) {
        Veiculos veiculo = veiculosRepository.findByPlacaActive(veiculos.getPlaca())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));

        veiculo.setDataSaida(LocalDate.now());
        veiculo.setHorarioSaida(LocalTime.now());
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
