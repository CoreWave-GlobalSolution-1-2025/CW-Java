# CoreWave - Monitoramento de Eventos Extremos

## Visão Geral do Projeto

O projeto **CoreWave** é uma aplicação Java Quarkus desenvolvida para **monitorar eventos extremos**. Ele oferece uma API robusta para gerenciar usuários e registrar, consultar e atualizar informações sobre eventos como enchentes, incêndios e deslizamentos. O objetivo principal é fornecer uma ferramenta para acompanhar e gerenciar dados relacionados a desastres naturais e situações de risco.

A aplicação é configurada para fácil implantação no Railway.

## Funcionalidades Principais

* **Gestão de Usuários:** Cadastro, listagem, consulta e exclusão de usuários do sistema.
* **Gestão de Eventos:** Registro, listagem paginada, consulta, atualização e exclusão de eventos extremos.
* **Validação de Usuário:** Verificação de usuários existentes.

## Tecnologias Utilizadas

* **Java**
* **Quarkus Framework:** Para desenvolvimento de aplicações nativas e de alta performance.
* **API RESTful:** Para comunicação entre cliente e servidor.
* **Railway:** Plataforma de deploy.

## Estrutura da API

A API do CoreWave está disponível em: [https://corewaveapi.onrender.com](https://corewaveapi.onrender.com)

Abaixo estão os endpoints disponíveis e seus respectivos detalhes:

### **Rotas de Usuários (`/users`)**

#### 1. Listar Usuários

* **GET `/users?page=X&size=X`**
    * [cite_start]**Descrição:** Retorna uma lista paginada de usuários no sistema.
    * [cite_start]**Parâmetros de Query:**
        * [cite_start]`page`: A página buscada (padrão: 1).
        * [cite_start]`size`: O tamanho da lista (padrão: 15).
    * [cite_start]**Retorna:** JSON.
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
    * [cite_start]**Descrição:** Retorna um usuário específico.
    * **Parâmetros de Path:**
        * [cite_start]`id`: ID do usuário buscado.
    * [cite_start]**Retorna:** JSON.
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
    * [cite_start]**Descrição:** Adiciona um novo usuário no sistema.
    * [cite_start]**Consome:** JSON.
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
    * [cite_start]**Consome:** JSON.
    * **Exemplo de Requisição (Body):**
        ```json
        {
          "email": "novoEmail@gmail.com"
        }
        ```

#### 5. Deletar Usuário

* **DELETE `/users/{id}`**
    * [cite_start]**Descrição:** Remove o usuário específico.
    * **Parâmetros de Path:**
        * [cite_start]`id`: ID do usuário buscado.

#### 6. Validar Usuário

* **GET `/users/validate-user`**
    * [cite_start]**Descrição:** Verifica se o usuário está cadastrado.
    * [cite_start]**Consome:** JSON.
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
    * [cite_start]**Descrição:** Retorna uma lista paginada de eventos no sistema.
    * **Parâmetros de Query:**
        * [cite_start]`page`: A página buscada (padrão: 1).
        * [cite_start]`size`: O tamanho da lista (padrão: 15).
    * [cite_start]**Retorna:** JSON.
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
    * [cite_start]**Descrição:** Retorna o evento específico.
    * **Parâmetros de Path:**
        * [cite_start]`id`: ID do evento buscado.
    * [cite_start]**Retorna:** JSON.
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
    * [cite_start]**Descrição:** Adiciona um novo evento no sistema.
    * [cite_start]**Consome:** JSON.
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
    * [cite_start]**Consome:** JSON.
    * **Exemplo de Requisição (Body):**
        ```json
        {
          "eventType": "incendio"
        }
        ```

#### 5. Deletar Evento

* **DELETE `/events/{id}`**
    * [cite_start]**Descrição:** Remove o evento específico.
    * **Parâmetros de Path:**
        * [cite_start]`id`: ID do evento buscado.

## Como Configurar e Rodar o Projeto Localmente

**(Você precisará preencher esta seção com os passos específicos do seu projeto. Exemplo abaixo.)**

### Pré-requisitos

* Java Development Kit (JDK) 11 ou superior
* Maven ou Gradle
* Git

### Passos:

1.  **Clonar o repositório:**
    ```bash
    git clone [https://github.com/CoreWave-GlobalSolution-1-2025/CW-Java.git](https://github.com/CoreWave-GlobalSolution-1-2025/CW-Java.git)
    cd CW-Java
    ```
2.  **Configurar o banco de dados (se aplicável):**
    * *(Ex: instruções para configurar um banco de dados local, se houver.)*
3.  **Rodar a aplicação:**
    * **Com Maven:**
        ```bash
        ./mvnw quarkus:dev
        ```
    * **Com Gradle:**
        ```bash
        ./gradlew quarkusDev
        ```
    A aplicação estará disponível em `http://localhost:8080` (ou a porta configurada).

## Como Fazer o Deploy para o Railway

**(Você precisará preencher esta seção com os passos específicos para o deploy. Exemplo abaixo.)**

1.  **Crie uma conta no Railway** (se ainda não tiver).
2.  **Conecte seu repositório GitHub** ao Railway.
3.  **Crie um novo projeto** no Railway e selecione o repositório `CoreWave-GlobalSolution-1-2025/CW-Java`.
4.  **Configure as variáveis de ambiente** necessárias (ex: variáveis de conexão com banco de dados, se aplicável).
5.  O Railway irá automaticamente detectar a aplicação Quarkus e iniciar o processo de build e deploy.

## Contribuição

*(Opcional: Se este for um projeto colaborativo, adicione instruções sobre como outros podem contribuir.)*

## Licença

*(Opcional: Adicione a licença do seu projeto, por exemplo, MIT.)*

---