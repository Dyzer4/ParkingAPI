package com.example.estacionamento.DTO;

public class VeiculoRespostaDTO {
    private String mensagem;
    private VeiculosDTO veiculo;

    public VeiculoRespostaDTO(String mensagem, VeiculosDTO veiculo) {
        this.mensagem = mensagem;
        this.veiculo = veiculo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public VeiculosDTO getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculosDTO veiculo) {
        this.veiculo = veiculo;
    }
}