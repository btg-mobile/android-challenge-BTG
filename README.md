
# Code Challenge :: The Movie Database API

Client da API do The Movie DB. Utilizou-se Clean Architecture, dividindo as responsabilidades do App em camadas (View, Repository, DAO) e MVVM (Model, View, ViewModel) recomendado pelo Google, com o framework Lifecycle. Pensando nos conceitos do SOLID, um framework para injeção de dependencias foi utilizado, chamado Koin.

O app possui as seguintes funcionalidades

- Paginação com rolagem infinita
- Swipe up para atualizar a lista
- IME customizado para buscar tanto pelo teclado quanto pelo botao de busca
- Ao clicar em um item da pesquisa, é direcionado a uma tela de detalhes
- Após a busca o teclado é fechado automaticamente
- Quando é feita uma busca com o campo vazio, ele traz a lista de filmes populares

## Instalação

Para gerar o arquivo .apk do projeto, no terminal navegue até a pasta do projeto e digite o comando abaixo
```
./gradlew assembleDebug
```
Após o termino da operação, digite o comando para instalação no celular
```
adb install /app/build/outputs/apk/app-debug.apk
```
e abra o app "Teste @Rafael"

### Pré requisitos

É necessário que se tenha instalado o ADB caso queria realizar a instalação via comando, do contrário é necessário ir até o local de criação do arquivo .apk e copia-lo ao dispositivo utilizado para teste. Todos os comando utilizados devem estar presente na raiz do projeto para funcionarem.

## Testes

Todos os testes foram feitos utilizando a sintaxe de nomenclatura do [Gherkin](https://cucumber.io/docs/gherkin/reference/) e o padrão [Robot](https://jakewharton.com/testing-robots/). Os testes aqui escrito, são uma demonstração de como eu atuo para garantir a especificação e o funcionamento do app. Através da sintaxe do gherkin é possível testar os cenários mais minuciosos de um sistema, garantindo quaisquer alterações futuras poderão ser feitas com a tranquilidade de não afetarem outras áreas do sistema.

### Testes unitários
A execução dos testes unitários (locais) feitos pode ser realizada pelo comando abaixo
```
./gradlew testDebugUnitTest --info
```

### Testes Instrumentados
Com um aparelho conectado ao computador (em modo debug), digite o comando abaixo e o teste será executado no celular
```
./gradlew connectedAndroidTest
```

## Frameworks utilizados

### View
* [ConstraintLayout2](https://developer.android.com/training/constraint-layout)
* [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)
* [CardView](https://developer.android.com/guide/topics/ui/layout/cardview)
* [Paging](https://developer.android.com/topic/libraries/architecture/paging)

### Architecture
* [Koin](https://start.insert-koin.io)
* [Lifecycle & ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Navigation](https://developer.android.com/guide/navigation)

### Network request e Concurrency
* [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Glide](https://bumptech.github.io/glide/)
* [Retrofit](https://square.github.io/retrofit/)

### Testes
* [Espresso](https://developer.android.com/training/testing/espresso)
* [Roboletric](http://robolectric.org/)
* [Mockito](https://site.mockito.org/)
* [FragmentScenario](https://developer.android.com/training/basics/fragments/testing)
* [Mockk](https://github.com/mockk/mockk)

