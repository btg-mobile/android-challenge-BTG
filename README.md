# Desafio BTG Android:

<p align="center">
  <img src="video/app-main-flow.mp4" align="center" width=150>
</p>
<a name="flow" />

1. [Introdução](#introduction) 
2. [Requisitos](#requirements) 
3. [Organização](#organization)
4. [Recursos](#resources)
5. [Considerações](#considerations)
5. [Instalação](#setup)  
6. [Licença](#license)
<a name="introduction" />

## Introdução

Desafio proposto pelo Banco de Investimentos BTG Pactual para a vaga de desenvolvedor Android. Você deve fazer um aplicativo livre, que lista os filmes populares do site TheMovieDB. No fluxo do aplicativo, o usuário tem duas abas superiores, que lista os filmes populares e os favoritos. Cada um, com uma barra de pesquisa.
<a name="requirements" />

## Requisitos:

1. Criar uma tela principal com duas abas: Populares e Favoritos.
2. Na aba de Populares, carregar os dados vindos da API do TheMOvieDB em uma lista. Os itens a serem mostrados são: poster do filme, nome e data de lançamento.
3. Na aba de Favoritos, também carregar uma lista dos filmes que forem sendo favoritados. Os itens a serem mostrados são: poster do filme, nome e data de lançamento.
4. Cada item do filme, possui uma tela de detalhe. Onde devem ser mostrados as informações: poster do filme, título, avaliação, gênero (por extenso), sinopse e um botão para favoritar o filme.
4. Ao favoritar um filme, o mesmo deve aparecer na lista de filmes favoritos. Ao desfavoritar, o mesmo deve desaparecer.
5. Na barra de pesquisa dos populares, deve ser permitido realizar buscas por nome. Já na de favoritos, as buscas podem ser realizadas por nome e por data.
6. Fazer os tratamentos de erros e dos fluxos de exceção necessários.
7. O fluxo das telas está no topo <a name="flow"> e arquivo app-main-flow.mp4.
<a name="organization" />

## Organização

As classes estão divididas entre packages próprios para melhor visualização e futura manutenção.
<a name="resources" />

## Recursos

 1. **Java**, como linguagem de programação;
 2. **Retrofit**, para requisições HTTP;
 3. **SerializedName**, para contornar as inconsistências de um dos JSONs;
 4. **Picasso**, para o carregamento das imagens
 5. **Junit e Mockito**, para testes unitários.
 6. **Expresso**, para testes de tela.
<a name="considerations" />

## Considerações

A estrutura principal das classes foi mantida. Para comportar o status de favorito, foi criado um arquivo de SharedPreferences, que guarda esta informação a requisita em momentos apropriados para o correto carregamento das listas.
<a name="setup" />

## Instalação

Para rodar esse projeto utilize uma das seguintes formas:

Instale o APK disponível na raíz do projeto (app-desafio-btg)

ou

Clone o repositório na sua máquina.
Faça o build da aplicação utilizando Android Studio ou via terminal com ```./gradlew assembleDebug```
<a name="license" />

## Licença
<aside class="notice">
  
Copyright 2019 Ricardo Sousa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

</aside>