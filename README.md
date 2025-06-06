# CoreWave - Monitoramento de Eventos Extremos

## Participantes

- João Alves 559369
- Juan Pablo 560445
- Matheus Mariotto 560276

## Visão Geral do Projeto

O projeto **CoreWave** é uma aplicação Java Quarkus desenvolvida para **monitorar eventos extremos**. Ele oferece uma API robusta para gerenciar usuários e registrar, consultar e atualizar informações sobre eventos como enchentes, incêndios e deslizamentos. O objetivo principal é fornecer uma ferramenta para acompanhar e gerenciar dados relacionados a desastres naturais e situações de risco.

A aplicação é configurada para fácil implantação no **Render**.

## Funcionalidades Principais

* **Gestão de Usuários:** Cadastro, listagem, consulta e exclusão de usuários do sistema.
* **Gestão de Eventos:** Registro, listagem paginada, consulta, atualização e exclusão de eventos extremos.
* **Validação de Usuário:** Verificação de usuários existentes.

## Tecnologias Utilizadas

* **Java**
* **Quarkus Framework:** Para desenvolvimento de aplicações nativas e de alta performance.
* **API RESTful:** Para comunicação entre cliente e servidor.
* **Render:** Plataforma de deploy.

## Estrutura da API

A API do CoreWave está disponível em: [https://corewaveapi.onrender.com](https://corewaveapi.onrender.com)

Abaixo estão os endpoints disponíveis e seus respectivos detalhes:

### **Rotas de Usuários (`/users`)**

#### 1. Listar Usuários

* **GET `/users?page=X&size=X`**
    * **Descrição:** Retorna uma lista paginada de usuários no sistema. 
    * **Parâmetros de Query:**
        * `page`: A página buscada (padrão: 1). 
        * `size`: O tamanho da lista (padrão: 15). 
    * **Retorna:** JSON. 
    * **Exemplo de Resposta:**
        ```json
        {
          "page": 1,
          "pageSize": 15,
          "totalItens": 3,
          "data": [
            {
              "id": 1,
              "name": "Carlos Meteorologista",
              "deleted": false,
              "email": "carlos.meteo@defesacivil.gov.br"
            },
            {
              "id": 2,
              "name": "Ana Bombeiros",
              "deleted": false,
              "email": "ana.bombeiros@cbm.sp.gov.br"
            },
            {
              "id": 3,
              "name": "Pedro Geólogo",
              "deleted": false,
              "email": "pedro.geologo@instituto.gov.br"
            }
          ]
        }
        ```

#### 2. Consultar Usuário por ID

* **GET `/users/{id}`**
    * **Descrição:** Retorna o usuário específico. 
    * **Parâmetros de Path:**
        * `id`: ID do usuário buscado. 
    * **Retorna:** JSON. 
    * **Exemplo de Resposta:**
        ```json
        {
          "id": 1,
          "name": "Carlos Meteorologista",
          "deleted": false,
          "email": "carlos.meteo@defesacivil.gov.br",
          "password": "senha123"
        }
        ```

#### 3. Adicionar Usuário

* **POST `/users`**
    * **Descrição:** Adiciona um novo usuário no sistema. 
    * **Consome:** JSON. 
    * **Exemplo de Requisição (Body):**
        ```json
        {
          "name": "Carlos",
          "email": "email@gmail.com",
          "password": "123a"
        }
        ```

#### 4. Atualizar Usuário

* **PUT `/users/{id}`**
    * **Descrição:** Atualiza um usuário. [cite_start]Só precisa enviar o novo campo para atualizar. 
    * **Consome:** JSON. 
    * **Exemplo de Requisição (Body):**
        ```json
        {
          "email": "novoEmail@gmail.com"
        }
        ```

#### 5. Deletar Usuário

* **DELETE `/users/{id}`**
    * **Descrição:** Remove o usuário específico. 
    * **Parâmetros de Path:**
        * `id`: ID do usuário buscado. 

#### 6. Validar Usuário

* **GET `/users/validate-user`**
    * **Descrição:** Verifica se o usuário está cadastrado. 
    * **Consome:** JSON. 
    * **Exemplo de Requisição (Body):**
        ```json
        {
          "email": "email@gmail.com",
          "password": "123a"
        }
        ```

### **Rotas de Eventos (`/events`)**

#### 1. Listar Eventos

* **GET `/events?page=X&size=X`**
    * **Descrição:** Retorna uma lista paginada de eventos no sistema. 
    * **Parâmetros de Query:**
        * `page`: A página buscada (padrão: 1). 
        * `size`: O tamanho da lista (padrão: 15). 
    * **Retorna:** JSON. 
    * **Exemplo de Resposta:**
        ```json
        {
          "page": 1,
          "pageSize": 15,
          "totalItens": 3,
          "data": [
            {
              "id": 1,
              "name": "Enchente Rio Tietã",
              "deleted": false,
              "eventType": "enchente",
              "place": "Marginal Tietã - Zona Leste São Paulo/SP",
              "description": "Transbordamento do Rio Tietã após chuvas intensas atingindo milhares de pessoas",
              "eventRisk": "alta"
            },
            {
              "id": 2,
              "name": "Incêndio Florestal Serra",
              "deleted": false,
              "eventType": "incendio",
              "place": "Parque Nacional Serra da Cantareira São Paulo/SP",
              "description": "Grande incendio florestal em Área de preservação com risco de alastramento",
              "eventRisk": "alta"
            },
            {
              "id": 3,
              "name": "Deslizamento Morro Favela",
              "deleted": false,
              "eventType": "deslizamento",
              "place": "Morro do Alemão Rio de Janeiro/RJ",
              "description": "Deslizamento de terra após período chuvoso com risco para moradores",
              "eventRisk": "media"
            }
          ]
        }
        ```

#### 2. Consultar Evento por ID

* **GET `/events/{id}`**
    * **Descrição:** Retorna o evento específico. 
    * **Parâmetros de Path:**
        * `id`: ID do evento buscado. 
    * **Retorna:** JSON. 
    * **Exemplo de Resposta:**
        ```json
        {
          "id": 1,
          "name": "Enchente Rio Tietã",
          "deleted": false,
          "eventType": "enchente",
          "place": "Marginal Tietã - Zona Leste São Paulo/SP",
          "description": "Transbordamento do Rio Tietã após chuvas intensas atingindo milhares de pessoas",
          "eventRisk": "alta"
        }
        ```

#### 3. Adicionar Evento

* **POST `/events`**
    * **Descrição:** Adiciona um novo evento no sistema. 
    * **Consome:** JSON. 
    * **Exemplo de Requisição (Body):**
        ```json
        {
          "name": "Carlos",
          "eventType": "enchente",
          "eventRisk": "alta",
          "place": "SP",
          "description": "Muita chuva na região. Bueiros entupidos."
        }
        ```

#### 4. Atualizar Evento

* **PUT `/events/{id}`**
    * **Descrição:** Atualiza um evento. [cite_start]Só precisa enviar o novo campo para atualizar. 
    * **Consome:** JSON. 
    * **Exemplo de Requisição (Body):**
        ```json
        {
          "eventType": "incendio"
        }
        ```

#### 5. Deletar Evento

* **DELETE `/events/{id}`**
    * **Descrição:** Remove o evento específico. 
    * **Parâmetros de Path:**
        * `id`: ID do evento buscado. 
