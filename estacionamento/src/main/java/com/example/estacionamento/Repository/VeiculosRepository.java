package com.example.estacionamento.Repository;

import com.example.estacionamento.Entity.Veiculos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VeiculosRepository extends JpaRepository<Veiculos, Integer> {

    List<Veiculos> findByPlaca(String placa);

    @Query("SELECT v FROM Veiculos v WHERE v.dataSaida IS NULL")
    List<Veiculos> findByDataSaidaNull();

    @Query("SELECT v FROM Veiculos v WHERE v.dataSaida IS NULL AND v.placa = :placa")
    Optional<Veiculos> findByPlacaActive(@Param("placa") String placa);
}
