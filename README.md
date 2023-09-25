# Wishlist Service

Aplicação implementada em Kotlin e Spring Boot, organizada pelos seguintes módulos Gradle:

- [core/domain](core/domain)
  - Classes do domínio, independente de bibliotecas externas.
- [core/use-case](core/use-case)
  - Implementa manipulações do domínio e independe de bibliotecas externas.
- [adapter/input/spring-controller](adapter/input/spring-controller)
  - Implementa o mapeamento de APIs do serviço através do Spring Web, utiliza os casos de usos e o domínio.
- [adapter/output/mongo-repository](adapter/output/mongo-repository)
  - Implementa o repositório para persistência com MongoDB, utiliza o domínio.
- [app/spring-app](app/spring-app) 
  - Ponto de entrada para execução da aplicação, mapeia a injeção dos casos de uso e importa os demais módulos para o
  funcionamento da aplicação.


## Instruções de execução

- Baixar este repositório
- Executar `docker-compose up --detach` para subir o MongoDB utilizado pela aplicação
- Executar `./gradlew :spring-app:bootRun`
![run](/imgs/run.gif)
- Swagger disponível em http://localhost:8080/swagger-ui/index.html  
![screenshot](/imgs/swagger.png)


## Referências
- [Enunciado](imgs/enunciado.pdf)
- [The Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Criando uma aplicação modular muito além do Clean Architecture](https://medium.com/luizalabs/criando-uma-aplica%C3%A7%C3%A3o-modular-muito-al%C3%A9m-do-clean-architecture-5dde3687c5d6)
- [Descomplicando a Clean Architecture](https://medium.com/luizalabs/descomplicando-a-clean-architecture-cf4dfc4a1ac6)
- [klean-arch-sample](https://github.com/gbzarelli/klean-arch-sample/tree/master)
- [multi-module-spring-boot](https://github.com/emmapatterson/multi-module-spring-boot/tree/master)
