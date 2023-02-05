
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
Ao abrir o aplicativo, o competidor que está pilotando o barco se depara com esse grande botão escrito "SOS" que ao clicá-lo é disparado um sinal para o servidor, através do protocolo WebSocket, onde é sinalizado que este barco está passando por alguma situação anormal. Para sinalizar que o botão foi clicado e, consequentemente, o piloto solicitou ajuda, na versão mobile as cores do botão alternam do fundo branco e texto vermelho para fundo vermelho e texto branco a cada segundo. O mesmo sinal poderá ser captado pelo organizador na versão web, onde o barco e o nome do time receberão um fundo vermelho. Ao clicar novamente, o botão para de "piscar" e nenhum sinal é emitido.
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
</div>

### Tela TEAMS
Tela com a visualização de cada time que está participando da competição. Tela construída usando o conceito de RecycleView para cada time. Assim criamos um modelo que é usada várias vezes, mudando apenas seu conteúdo.
<div align="center">
<img alt="DSB Logo" src=github/teams.png width="200px" />
</div>

## 👨🏾‍💻 Particulariedades da aplicação

### Tela SOS
Usamos o método do `Handler()` chamado `postDelayed` para definir que a cada 1000 ms, desde que o botão foi apertado, iremos alterar a cor do botão:
```kotlin

binding.buttonSos.setOnClickListener {
  if (!isRunning) {
      isRunning = true
      val colors = intArrayOf(Color.RED, Color.WHITE)
      var index = 0
      val handler = Handler()
      val runnable = object : Runnable {
          override fun run() {
              buttonSos.setBackgroundColor(colors[index % colors.size])
              buttonSos.setTextColor(colors[(index + 1) % colors.size])
              view.setBackgroundColor(colors[(index + 1) % colors.size])
              index++
              if (isRunning) {
                  handler.postDelayed(this, 1000)
              }

          }
      }
      handler.post(runnable)
  } else {
      isRunning = false
  }
}
```

Este código abaixo cria uma conexão socket com o servidor "http://server-solares-solaris.herokuapp.com" e inicia as atualizações de localização utilizando o contexto atual.

Se o status de rastreamento (AppData.statusTracking) estiver ativo, o código formata os dados GPS como uma string e essa string é emitida como um evento "newinfo" pelo socket para o servidor se o botão estiver ativo pedindo ajuda (verificamos isso com a variável **isRunning** usada na funcionalidade de cima).

```kotlin
val socket = IO.socket("http://server-solares-solaris.herokuapp.com")
socket.connect()

// Instantiates the LocationViewModel
locationModel.startLocationUpdates(requireContext())

// Observes the MutableLiveData lastGPSValues and updates the UI
locationModel.lastGPSValues.observe(viewLifecycleOwner, Observer {
    if(AppData.statusTracking){
        val resultGPS = "[0%s,%.6f,%.6f,%.2f,%s];01/01/99 00:41:02"
            .format(Locale.US,
                AppData.numberBoat,
                it?.latitude ?: 0.0,
                it?.longitude ?: 0.0,
                it?.speed ?: 0.0,
                if (isRunning) "1" else "0")
        println(resultGPS)
        socket.emit("newinfo", resultGPS)
    }
})
```

### Tela Velo (Velocidade)
Nesta tela nós escutamos as mudanças de cada informações emitidas (latitude, longitude e velocidade) num intervalo definido em LocationViewModel. Assim conseguimos ter a velocidade mudando em tela, fazemos isso através do Observer abaixo:

```kotlin
locationModel.lastGPSValues.observe(viewLifecycleOwner, Observer {
    binding.speedText.text = "%.2f nós".format(Locale.US, it?.speed)
})
```

### Menu inicial
Aqui não teve muito segredo! Criamos um elemento de menu (main_menu.xml), definimos dois itens e na main fazemos o controle do caminho a ser redirecionado ao clicar cada uma das opções:

```kotlin
override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.menu_item1 -> {
            // Going to the settings screen, but needs to login first
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return true
        }
        R.id.menu_item2 -> {
            // Going to boat's competition list
            val intent = Intent(this, BoatsActivity::class.java)
            startActivity(intent)
            return true
        }
        else -> return super.onOptionsItemSelected(item)
    }
}

```
### Tela Settings
Aqui temos algo bem interessante! Escutamos quando um item do spinner for selecionado, a partir da seleção nós guardamos o nome e posição desse time selecionado (usando SharedPreference). 

```kotlin

binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
    // When an item is selected, update the preference and TextView with the selected item

    override fun onItemSelected(
        parent: AdapterView<*>,
        view: View,
        position: Int,
        id: Long
    ) {
        val selectedItem = parent.getItemAtPosition(position).toString()
        preferences.setString("TEAM", selectedItem)
        preferences.setString("POSITION", position.toString())
        if (position.toString() != savedTeamIndex.toString()) {
            binding.switchButton.isChecked = false
        }
    }

    // Empty implementation for when no item is selected (needed to use "object" class)
    override fun onNothingSelected(parent: AdapterView<*>) {}
}

```
Assim, ao fechar o aplicativo e voltarmos na tela, o elemento selecionado será o que guardamos, fazemos essa verificação logo no começo do onCreate deste elemento:
```kotlin

// Get the saved "TEAM" preference
val savedTeam = preferences.getString("TEAM")

// Get the index of the saved "TEAM" in the spinner's adapter
val savedTeamIndex =
    (binding.spinner.adapter as ArrayAdapter<String>).getPosition(savedTeam)

// Set the saved "TEAM" as the selected item in the spinner
binding.spinner.setSelection(savedTeamIndex)
```

### Tela TEAMS
O que temos de diferente aqui é que para resolver problemas de segurança SSL em dispositivos Android com versões antigas, é feita a instalação de um provedor SSL. Em seguida, é criado um objeto SSLContext para criptografia TLSv1.2.

```kotlin
ProviderInstaller.installIfNeeded(applicationContext)
val sslContext: SSLContext = SSLContext.getInstance("TLSv1.2")
sslContext.init(null, null, null)
val engine: SSLEngine = sslContext.createSSLEngine()
```

Em seguida, é iniciada uma tarefa em segundo plano (GlobalScope.launch) para realizar uma chamada de rede. A resposta da chamada é então parseada em uma lista de objetos BoatModel e adicionada ao boatArray. Por fim, o adapter é definido para a RecyclerView, passando o boatArray como parâmetro:

```kotlin
GlobalScope.launch(Dispatchers.IO) {
  val response =
      getDataFromApi("https://andreocunha.github.io/upload_files_test/boats.json")

  // parse response to a list of BoatModel objects
  val boats = parseResponse(response)

  // add the boats to the boatArray
  boatArray.addAll(boats)
  println(boatArray)
  withContext(Dispatchers.Main) {
      newRecylerview.adapter = MyAdapter(boatArray)
  }
}
```

Além disso, para injetar as informações no model de um barco, nós buscamos a imagem a partir de uma URL, com a biblioteca Glide:
```kotlin
Glide.with(holder.itemView.context).load(currentItem.image).into(holder.imageView)
```

## 📽️ Vídeo demonstrativo
* <a href="https://youtu.be/p1qgmu9adfg"> App rastreamento do Desafio Solar Brasil (DSB)</a>

## 🧠 Autores

| [<img src="https://avatars.githubusercontent.com/u/21970707?v=4" width=115><br><sub>Emerson Laranja</sub>](https://github.com/EmersonLaranja) | [<img src="https://avatars.githubusercontent.com/u/54721131?v=4" width=115><br><sub>André Cunha</sub>](https://github.com/andreocunha) |
| :--------------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------:|
