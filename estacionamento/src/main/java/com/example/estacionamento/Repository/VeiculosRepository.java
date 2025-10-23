package com.example.estacionamento.Repository;

import com.example.estacionamento.Entity.Veiculos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VeiculosRepository extends JpaRepository<Veiculos, Integer> {

    List<Veiculos> findByPlaca(String placa);

    // Lista veículos ativos (saída ainda não registrada)
    @Query("SELECT v FROM Veiculos v WHERE v.horarioSaida IS NULL")
    List<Veiculos> findByHorarioSaidaNull();

    // Busca veículo ativo por placa
    @Query("SELECT v FROM Veiculos v WHERE v.horarioSaida IS NULL AND v.placa = :placa")
    Optional<Veiculos> findByPlacaAndHorarioSaidaNull(@Param("placa") String placa);

    // Checa se veículo ativo existe
    boolean existsByPlacaAndHorarioSaidaNull(String placa);
}