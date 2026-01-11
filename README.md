# App de Gestão de Livros

## O que faz?

Aplicação Android para gestão de coleções de livros, permitindo:

- **Adicionar livros** à estante (procurando por título)
- **Acompanhar o progresso** de leitura (0%, 25%, 50%, 75%, 100%)
- **Registar notas** sobre cada livro
- **Marcar favoritos** para destaque
- **Consultar melhores livros** de um determinado ano
- **Filtrar livros** por nome

## Como funciona?

### A arquitetura

A app está estruturada em camadas:

1. **DAO** - Define as operações na base de dados (inserir, atualizar, apagar, listar)
2. **Repository** - Camada intermediária que usa corotinas para não congelar a app
3. **ViewModel** - Gere a lógica e passa os dados para a interface
4. **UI (Compose)** - A parte visual que o utilizador vê

### Como usar

- **Estante**: Apresenta todos os livros guardados. Permite procurar por nome, atualizar progresso, registar notas ou remover livros.
- **Favoritos**: Apresenta apenas os livros marcados como favoritos.
- **Top**: Consulta os melhores livros de um ano específico numa API externa e permite adicioná-los à estante.
- **Novo**: Realiza pesquisa de novos livros numa API externa.

Os livros ficam armazenados localmente na base de dados do dispositivo, sendo necessária ligação à internet apenas para pesquisa de novos livros.

## Tecnologias usadas

- **Android + Kotlin** - Linguagem da app
- **Jetpack Compose** - Interface visual
- **Room** - Base de dados local
- **LiveData** - Observação de mudanças nos dados
- **Corotinas** - Para operações assincronas
- **OkHttp + Gson** - Para fazer chamadas à API
- **Coil** - Para carregar imagens online
