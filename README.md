==================Instruções para a execução do projeto==================

Cerifique-se de que tem o Maven, Java, e Docker instalado

Aconselhamos rodar o banco postgresql no Docker

docker run --name internetBanking -e POSTGRES_PASSWORD=12345 -p 5432:5432 -d postgres

-------------------------------------------------------------------------------------

Execute os microserviços com o comando ./mvnw spring-boot:run na seguinte sequência:

1: eurekaservice
2: gateway
3: emailmicroservice
4: segurancaApp

Após isso, execute o frontend com os seguintes comandos:

npm install
npm run dev