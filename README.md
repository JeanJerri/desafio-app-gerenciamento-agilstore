# Aplicação de Gerenciamento de Produtos para a Loja AgilStore

Este é um projeto Java desenvolvido com Maven e Java 21, utilizando o banco de dados Oracle. A aplicação é executada diretamente no terminal pela execução da classe `AgilstoreApplication`.

## Tecnologias Utilizadas

- Java 21: Linguagem de programação principal.
- Maven: Gerenciador de dependências.
- Banco de Dados Oracle: Para armazenamento de dados.

## Funcionalidades

- Cadastrar, listar, atualizar, excluir e buscar produtos.
- Gerenciamento de produtos.

## Configuração do Banco de Dados

- Certifique-se de que o banco de dados Oracle está em execução.
- Crie a tabela necessária para a aplicação. Use o script SQL abaixo:

   ```
   CREATE TABLE PRODUTO
   (
       CODIGO_PRODUTO NUMBER(9) NOT NULL
       , NOME VARCHAR(256) NOT NULL
       , CATEGORIA VARCHAR2(50) NOT NULL
       , QUANTIDADE NUMBER(9) NOT NULL
       , PRECO NUMBER(9) NOT NULL
       , CONSTRAINT PRODUTO_PK PRIMARY KEY
       (
       CODIGO_PRODUTO
           )
       ENABLE
   );

   CREATE SEQUENCE SEQUENCE_PRODUTO INCREMENT BY 1 START WITH 1 MAXVALUE 999999999 MINVALUE 1 NOCACHE;
   ```
- Atualize o arquivo application.properties com as credenciais e URL de conexão do banco de dados Oracle.
  Exemplo de configuração no application.properties:
   ```
   spring.application.name=agilstore

   spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XE
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
   spring.jpa.hibernate.ddl-auto=update
   ```

## Como Configurar e Executar a Aplicação Localmente

### Pré-requisitos
- **Java Development Kit (JDK):** Certifique-se de ter o JDK 21 ou superior instalado.
- **Maven:** Certifique-se de ter o Apache Maven instalado.
- **Banco de dados Oracle:** Instale o banco de dados Oracle na sua máquina ou conecte a um banco de dados Oracle remoto.
- **IntelliJ IDEA:** Recomendado pois foi a IDE utilizada no desenvolvimento.

### Passos
- Abra o terminal e execute o seguinte comando para clonar o repositório:
  `git clone https://github.com/JeanJerri/desafio-app-gerenciamento-agilstore.git`
- Abra o IntelliJ IDEA.
- Selecione `File -> Open` e navegue até o diretório onde você clonou o repositório.
- Selecione a pasta gerenciamento_agilstore e clique em OK.
- No IntelliJ IDEA, vá até a janela Maven (geralmente localizada na barra lateral direita).
- Clique no botão Execute Maven Goal, digite `mvn clean install` e clique em Enter para garantir que todas as dependências sejam baixadas e configuradas corretamente.
- Navegue até o arquivo `\src\main\java\com\pucrs\agilstore\AgilstoreApplication.java`.
- Clique com o botão direito no arquivo e selecione `Run 'AgilstoreApplication'`.
- A janela do terminal deve abrir com o o menu de opções da aplicação.

Para preencher a informação de categorias, utilize alguma das categoris cadastradas pelo arquivo `Categorias.java` na pasta `enums`:
```
SMARTPHONE,
NOTEBOOK,
TABLET,
CABO,
FONE,
CARREGADOR
```

## Contato

Para dúvidas ou sugestões, entre em contato pelo e-mail: jeanjerry2015@gmail.com.

