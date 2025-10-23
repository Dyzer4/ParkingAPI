package com.example.estacionamento.Controller;

import com.example.estacionamento.DTO.VeiculosDTO;
import com.example.estacionamento.DTO.VeiculoRespostaDTO;
import com.example.estacionamento.Entity.Veiculos;
import com.example.estacionamento.Services.VeiculosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculosService veiculosService;

    @GetMapping
    public ResponseEntity<List<VeiculosDTO>> listarVeiculos() {
        List<Veiculos> veiculos = veiculosService.listarVeiculosAtivos();
        List<VeiculosDTO> veiculosDTO = veiculos.stream()
                .map(veiculosService::convertToDTO)
                .toList();
        return ResponseEntity.ok(veiculosDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VeiculosDTO> pesquisarVeiculo(@PathVariable int id) {
        Veiculos veiculo = veiculosService.buscarPorId(id);
        if (veiculo != null) {
            return ResponseEntity.ok(veiculosService.convertToDTO(veiculo));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<VeiculosDTO>> pesquisarVeiculoPorPlaca(@PathVariable String placa) {
        List<Veiculos> veiculos = veiculosService.buscarPorPlaca(placa);
        if (veiculos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<VeiculosDTO> dtos = veiculos.stream()
                .map(veiculosService::convertToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/entrada")
    public ResponseEntity<VeiculoRespostaDTO> liberarEntradaPorPlaca(@RequestBody Veiculos veiculos) {
        VeiculosDTO dto = veiculosService.liberarEntrada(veiculos);
        VeiculoRespostaDTO resposta = new VeiculoRespostaDTO("Entrada liberada com sucesso", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/saida")
    public ResponseEntity<VeiculoRespostaDTO> liberarSaidaPorPlaca(@RequestBody Veiculos veiculos) {
        VeiculosDTO dto = veiculosService.liberarSaida(veiculos);
        VeiculoRespostaDTO resposta = new VeiculoRespostaDTO("Saída liberada com sucesso", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
}