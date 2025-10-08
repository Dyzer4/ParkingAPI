# Documentação ParkingAPI

API para gerenciamento de um estacionamento, com cadastro de usuários, login, liberação de entrada e saída de veículos.

# Rotas

## GET

### ”/api/veiculos/id/{id}”

Objetivo: Buscar o registro de veículo pelo ID independente se ja foi liberado a saída ou não.

Retorno esperado:

```json
{
	"placa": "AAA7777",
	"dataEntrada": "2025-08-27",
	"horarioEntrada": "15:10:45",
	"dataSaida": "2025-08-27",
	"horarioSaida": "16:25:33",
	"valorPago": 20
}
```

### “/api/veiculos/placa/{placa}”

Objetivo: Buscar todos os registros relacionados a placa do veículo pesquisado.


Retorno esperado:

```json
[
	{
		"placa": "ABC1234",
		"dataEntrada": "2025-08-27",
		"horarioEntrada": "21:27:47",
		"dataSaida": "2025-08-27",
		"horarioSaida": "21:59:05",
		"valorPago": 10
	},
	{
		"placa": "ABC1234",
		"dataEntrada": "2025-08-30",
		"horarioEntrada": "09:13:54",
		"dataSaida": "2025-08-30",
		"horarioSaida": "09:35:09",
		"valorPago": 10
	}
]
```

### “/api/veiculos”

Objetivo: Buscar todos os veículos ativos do estacionamento.

Retorno esperado:

```json
[
	{
		"placa": "DDD9999",
		"dataEntrada": "2025-08-30",
		"horarioEntrada": "13:33:17"
	},
	{
		"placa": "CCC8888",
		"dataEntrada": "2025-08-30",
		"horarioEntrada": "20:04:21"
	}
]
```

### “/auth/me”

Objetivo: Buscar dados do usuario logado.

informação do Header necessaria: 

```
	Authorization: Bearer <seu_token>
```

Retorno esperado:

```json
[
	{
		"nome": "aaaaaa aa aaaaaa",
		"email": "aaaaaaaaa@gmail.com",
	}
]
```

---

## POST

### “/auth/register”

Objetivo: Realizar o cadastro de usuários.

Corpo da requisição esperado (O que deverá ser enviado pelo front-end):

```json
{
	"nome": "a",
	"email": "a@gmail.com",
	"senha": "XXXXXXXX"
}
```

Retorno esperado:

```
Usuário registrado com sucesso
```

### “/auth/login”

Objetivo: Realizar o Login dos usuários.

Corpo da requisição esperado (O que deverá ser enviado pelo front-end):

```json
{
	"email": "a@gmail.com",
	"senha": "XXXXXXXX"
}
```

Retorno esperado:

```json
{
	"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsImlhdCI6MTc1NjU5NTM5OSwiZXhwIjoxNzU2NTk4OTk5fQ.dV6MYOVTvHwcC2byblb7oh6OhZ3WqC3QF9DThWRq5g0"
}
```

### “/api/veiculos/entrada”

Objetivo: Liberar a entrada do veículo no estacionamento.

Corpo da requisição esperado (O que deverá ser enviado pelo front-end):

```json
{
	"placa": "CCC8888"
}
```

Retorno esperado: 

```json
{
	"mensagem": "Entrada liberada com sucesso",
	"veiculo": {
		"placa": "CCC8888",
		"dataEntrada": "2025-08-30",
		"horarioEntrada": "20:04:21.2525841"
	}
}
```

---

## PUT

### “/api/veiculos/saida”

Objetivo: Liberar a saída e fazer o calculo do valor a pagar do veículo no estacionamento.


Corpo da requisição esperado (O que deverá ser enviado pelo front-end):

```json
{
	"placa": "CCC8888"
}
```

Retorno esperado: 

```json
{
	"mensagem": "Saída liberada com sucesso",
	"veiculo": {
		"placa": "CCC8888",
		"dataEntrada": "2025-08-30",
		"horarioEntrada": "09:13:54",
		"dataSaida": "2025-08-30",
		"horarioSaida": "09:35:08.5911774",
		"valorPago": 10
	}
}
```

---

# Como iniciar o SpringBoot pelo VSCode

- Instale o JDK 20
- Abra o VSCode
- instale as extenções "Extension pack for Java" e "Spring Boot Extension Pack"
- reinicie o VSCode na pasta raiz do projeto Spring Boot
- irá aparecer na barra lateral esquerda um icone hexagonal, clique nele
- espere carregar tudo, ao final irá aparecer um item na lista com o nome do arquivo execultavel do projeto
 -clique no play único
