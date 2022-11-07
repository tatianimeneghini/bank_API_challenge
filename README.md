# Bank API
Este projeto faz parte do *desafio Backend* da Digital Republic.

## Descrição
API tem por objetivo o gerenciamento de contas bancárias, com funcionalidade básicas de criação de conta bancária, consulta de contas e contra, depósito bancário e transferência entre contas.  

## Arquitetura
``` 
.
├── ...
├── src    
│   ├── main
│   │   ├── controller              # Responsável pela orquestração dos dados
│   │   ├── exception               # Responsável por tratar erros específicos
│   │   ├── model                   # Camada de modelos
│   │   ├── repository              # Responsável por realizar operações na base de dados
│   │   ├── security                # Responsável pelas configurações de segurança
│   │   ├── service                 # Responsável pela lógica da aplicação e responsável por se comunicar com outras camadas
│   │   ├── utils                   # Camada de validações específicas
│   │   └── DemoApplication.kt      # Aplicação
│   ├── test
│   │   └──controller
└── ...
```   

## Rodar projeto
- Rodar `DemoApplication.kt`;  
- Conectar banco de dados no MongoDB.  

## Endpoints
### Criar conta 
Path: POST `localhost:8080/account`  
Body:
```{
"name": "",                  //String
document": ""                // String (11 dígitos)
}
```  
Basic Auth: verificar username e password em `SecurityConfiguration`  

### Listar todas contas 
Path: GET `localhost:8080/account`
Basic Auth: verificar username e password em `SecurityConfiguration`  

### Listar conta com CPF 
Path: GET `localhost:8080/account/{document}`
Basic Auth: verificar username e password em `SecurityConfiguration`  

### Depositar 
Path: PATCH `localhost:8080/account/deposit`  
Body:
```{
"account_id":  ,            // Long
"amount":                   // BigDecimal
}
```  
Basic Auth: verificar username e password em `SecurityConfiguration`  

### Transferir 
Path: PATCH `localhost:8080/account/transfer`  
Body:
```{
"account_id_from":  ,            // Long
"account_id_to": ,               // Long
"amount":                        // BigDecimal
}
```  
Basic Auth: verificar username e password em `SecurityConfiguration`  

## Regras de negócio
1. O CPF só pode ser associado a uma conta;
2. Para criar uma conta, é necessário conter o nome completo da pessoa e o CPF;
3. Uma conta bancária é criada com valor nulo e não é permitido saldo negativo;
4. Depósito nunca devem ultrapassar o valor de R$ 2000;
5. Transferências são gratuitas e ilimitadas.  
