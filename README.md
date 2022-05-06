#Assembleia API

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação.

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

- Cadastrar uma nova pauta
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
- Receber votes dos associados em pautas (os votes são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)
- Contabilizar os votes e dar o resultado da votação na pauta

  Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A solução deve ser construída em java, usando Spring-boot, mas os frameworks e bibliotecas são de livre escolha (desde que não infrinja direitos de uso).

É importante que as pautas e os votes sejam persistidos e que não sejam perdidos com o restart da aplicação.


##Informações
* Documentação da API - https://lmg-assembleia.herokuapp.com/swagger-ui.html

##Para rodar localmente
* Ao rodar o projeto localmente verificar a porta do postgresql, username e password no arquivo application.properties
* Para rodar os testes de Integração rodar o comando:

`./mvnw verify`

* Necessário instalar o banco postgres, caso tenha o docker instalado na máquina pode rodar o docker compose que se encontra na raiz do projeto, ele criará uma instância do postgres:
* `docker-compose up -d` - Instala o postgres
* `docker ps` - Verifica se está rodando
* Endereço da API: http://localhost:8080/

##Tecnologias
* Java 11
* Spring boot
* Spring data
* Swagger (Documentação da API)
* Postgres