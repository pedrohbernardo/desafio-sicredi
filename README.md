# Objetivo

Desafio proposto como avaliação técnica para a criação de uma Api REST.

# Desafio Sicredi

Criar uma aplicação para a criação e o manuseio de Pautas, onde se tem a possibilidade de efetuar votos em determinada pauta, durante o periodo referido.
 - Cadastrar uma nova pauta
 - Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
 - Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)
 - Contabilizar os votos e dar o resultado da votação na pauta

# Requisições disponiveis

## Associado

- POST - ("/associado") - para a criação do associado. Necessário disponibilidar um "cpf".

- GET - ("/associado/{id}") - recuperar informações do associado com base em seu "id".

- DELETE - ("/associado/{id}") - deletar informações do associado com base em seu "id".

## Pauta

- POST - ("/pauta") - para a criação de uma Pauta. Necessário informar um "nomePauta" e opcionalmente um "tempoDuracaoPauta" em minutos. (Ex: 1,5,10)

- GET - ("/pauta/{id}") - recuperar informações da Pauta com base em seu "id".

- DELETE - ("/pauta/{id}") - deletar informações da Pauta com base em seu "id".

- GET - ("/pauta/iniciar/{id}") - disponibilizando o "id" da pauta que deseja iniciar, onde assim serão permitidos os associados a votarem.

- GET - ("/pauta/resultado/{id}") - recupera informações sobre os resultados da pauta com o denominado "id".

## Voto

- POST - ("/votar") - Permite votar em uma sessão que está aberta. Necessário informar o "idPauta", "cpfAssociado" e o "voto" (Sendo "Sim" ou "Nao").

## Tecnologias

- Java
- Spring Boot
- Jpa
- PostgreSQL (DB)
