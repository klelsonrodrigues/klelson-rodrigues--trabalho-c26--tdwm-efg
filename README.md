klelson rodrigues- trabalho-c26- tdwm-efg
# SmartTask Secure

O SmartTask é um aplicativo Android nativo desenvolvido em Kotlin voltado para o gerenciamento eficiente e seguro de tarefas diárias. O projeto foi reestruturado profissionalmente com foco em segurança mobile, garantindo a proteção dos dados armazenados, o controle restrito de acessos e a conformidade básica com as diretrizes da LGPD (Lei Geral de Proteção de Dados).

---

## Descrição do Projeto

O aplicativo funciona como um organizador pessoal e profissional de afazeres, permitindo que o usuário manipule suas tarefas em uma interface fluida, moderna e totalmente responsiva através de uma RecyclerView. Os dados das tarefas e do estado da sessão do usuário não são perdidos ao fechar o app, pois são persistidos localmente no dispositivo utilizando a biblioteca Gson para serialização em JSON e o mecanismo SharedPreferences.

### Funcionalidades Gerais:
 Cadastrar e listar tarefas dinamicamente.
 Editar o título de tarefas existentes.
 Excluir tarefas da lista.
 Marcar e desmarcar tarefas como concluídas.
 Persistência de dados local.

---

## Funcionalidades de Segurança Implementadas

O aplicativo foi evoluído para aplicar conceitos rigorosos de segurança e privacidade no ecossistema mobile:

1. Autenticação Local Obrigatória: O aplicativo possui uma tela de login (usuário e senha) com validação local. É impossível visualizar ou interagir com as tarefas sem estar devidamente autenticado.
2. Controle de Acesso Restrito: Apenas usuários autenticados possuem permissão para criar, editar, visualizar ou excluir itens. Ao realizar o logout (sair), o aplicativo limpa a sessão e bloqueia a tela voltando ao Login.
3. Proteção de Dados Armazenados: O aplicativo mitiga riscos de vazamento não utilizando dados sensíveis fixados diretamente no código (hardcoded). O estado da sessão de autenticação e a lista são salvos utilizando as melhores práticas do Android com SharedPreferences.
4. Minimização de Permissões (Privacidade): O app opera estritamente com permissões locais em tempo de execução (runtime permissions), não solicitando acessos abusivos à internet, contatos ou localização sem uma real necessidade operacional.

---
instruções para Execução


Passo a Passo:

1    Clonar o repositório:
2    Abrir no Android Studio:

    Abra o Android Studio, clique em File > Open e selecione a pasta do projeto clonado.

3   Sincronizar o Gradle:

    O Android Studio irá detectar o arquivo build.gradle.kts. Aguarde a sincronização automática ou clique no botão Sync Now na barra de notificações superior.

4 Configurar o Emulador (Dispositivo Virtual):

    Acesse Tools > Device Manager no menu superior.

    Clique em New (+) > Create Virtual Device.

    Selecione o hardware recomendado (Exemplo: Pixel 6) e clique em Next.

    Escolha a imagem de sistema correspondente ao Android 14 (API 34) e finalize a criação.

5 Executar o Aplicativo: