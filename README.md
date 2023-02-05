
<h1 align="center">
  Projeto final: Desafio Solar Brasil (Android app)
</h1>

<h1 align="center">
    <img alt="DSB Logo" src="https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/09/Logo_DSB.png?fit=218%2C100&ssl=1" width="300px" />
</h1>


## 📜 Sobre o projeto
* O aplicativo foi feito em **Android com Kotlin** e roda em dispositivos com **Android 4.1 ou superior**;

* Ele pega a posição (latitude e longitude) do celular a cada segundo e envia para um servidor com Socket.io, atualizando as informações em tempo real em um site construído para a competição;

* Possui um sistema de autenticação que os organizadores do evento terão acesso e farão as configurações para cada barcos;

* O aplicativo permite solicitar socorro caso o barco encontro alguma situação anormal durante a competição;

* O mesmo permite verificar a velocidade atual do barco que é atualizada em uma pequena fração de segundos;

* No aplicativo é possível visualizar todos os competidores;

* A visualização dos barcos durante a competição foi implementada numa versão desktop em outro projeto, você pode confeir o resultado clicando [aqui](https://dsbrastreio.com.br/);

* Aplicativo desenvolvido para a competição do Desafio Solar Brasil (DSB) e 
para a disciplina de desenvolvimento mobile da Ufes (Universidade Federal do Espírito Santo).
<hr>

## 🏁 Como executar

O aplicativo ainda não está disponível na playstore ☹️, mas você pode executar usando o **emulador do Android Studio**:

1. Baixe o Android Studio através deste [link](https://developer.android.com/studio)
2. Faça o clone deste projeto
3. Ao abrir o Android Studio, seleciona a opção "Project from Version Control" e cole o link copiado do github
4. No menu superior, ao canto direito, selecione a opção "Device Manager" e crie um dispositivo, com android igual ou superior a 4.1, para emular a aplicação
5. Após esse processo, basta clicar em "Run" e aguardar a aplicação ser executada.

## :rocket: Entendendo melhor as funcionalidades

Para esse projeto, nós temos 2 atores principais que chamarei de **piloto** e **organizador**:
| Ator |  Significado | Permissões |
|---|---|---|
| Piloto  | Pessoa que dirige o barco | Pode solicitar ajuda, visualizar a velocidade do seu barco e ver a lista de times da competição |
| Organizador  | Organizador do DSB que possui acesso ao sistema  | Configura os aparelhos celulares para cada equipe, informando a chave de acesso e selecionando a qual time o dispositivo se refere|
<hr>

### Tela SOS
Ao abrir o aplicativo, o competidor que está pilotando o barco se depara com esse grande botão escrito "SOS" que ao clicá-lo é disparado um sinal para o servidor, através do protocolo WebSocket, onde é sinalizado que este barco está passando por alguma situação anormal. Para sinalizar que o botão foi clicado e, consequentemente, o piloto solicitou ajuda, na versão mobile as cores do botão alternam do fundo branco e texto vermelho para fundo vermelho e texto branco a cada segundo. O mesmo sinal poderá ser captado pelo organizador na versão web, onde o barco e o nome do time receberão um fundo vermelho.
<div align="center">
<img alt="DSB Logo" src=github/sos.png width="200px" />
</div>

### Tela Velo (Velocidade)
Abaixo do menu (estrutura de fundo roxo com o nome da aplicação e um botão ellipsis) temos uma estrutura chamada **Tab** que possui 2 opções: **SOS** (aberta por padrão ao iniciar o aplicativo) e **Velo**. Ao em Velo, desliza-se até o novo fragmento onde é exibida a velocidade do barco atual. Tal velocidade é baseada na diferença de posição (latitude e logitude) numa fração de segundos, via WebSocket.
<div align="center">
<img alt="DSB Logo" src=github/velocity.png width="200px" />
</div>

### Menu inicial
Perceba que a tela é composta por um menu de fundo roxo com o nome da aplicação a esquerda e o ellipsis botão que possui duas outras telas: **Settings** e **Teams**.
<div align="center">
<img alt="DSB Logo" src=github/menu-options.png width="200px" />
</div>

### Tela Login
Ao clicar no botão ellipsis e selecionar settings,a pessoa é redirecionada para a tela de login. Assim, apenas o organizador irá acessar com sua chave secreta a tela settings.
<div align="center">
<img alt="DSB Logo" src=github/login.png width="200px" />
</div>

### Tela Settings
Quando um organizador faz o login, ele é redirecionado para está tela onde ele pode selecionar qual o time que representa o aparelho atual. Além disso, ao apertar no switch, ele liga a busca da localização (ou seja, dá vida ao aplicativo) permitindo ver a velocidade e localização do barco selecionado.

Somente após essa configuração, cada piloto irá receber um dispositivo do seu time. Não podendo alterar as informações da tela settings.
<div align="center">
<img alt="DSB Logo" src=github/settings.png width="200px" />
</idv>

### Tela TEAMS
Tela com a visualização de cada time que está participando da competição.
<div align="center">
<img alt="DSB Logo" src=github/teams.png width="200px" />
</div>

## 👨🏾‍💻 Particulariedades da aplicação

## 📽️ Vídeo demonstrativo
* <a href="https://youtu.be/p1qgmu9adfg"> App rastreamento do Desafio Solar Brasil (DSB)</a>

## 🧠 Autores

| [<img src="https://avatars.githubusercontent.com/u/21970707?v=4" width=115><br><sub>Emerson Laranja</sub>](https://github.com/EmersonLaranja) | [<img src="https://avatars.githubusercontent.com/u/54721131?v=4" width=115><br><sub>André Cunha</sub>](https://github.com/andreocunha) |
| :--------------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------:|
