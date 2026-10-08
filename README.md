# Controle de Estoque - Farmácia

Trabalho desenvolvido em Kotlin para a disciplina de **Programação Mobile Coding**. O sistema faz o controle básico de estoque de uma farmácia no terminal.

## 🚀 O que o sistema faz

- **Listagem de produtos:** Mostra os remédios cadastrados, quantidade em estoque, estoque mínimo e dias para o vencimento.
- **Alertas de reabastecimento:** Identifica quais remédios atingiram o limite mínimo e avisa se algum item controlado precisa de reposição prioritária.
- **Verificação de validade:** Avisa sobre remédios vencidos ou perto de vencer e bloqueia a saída de produtos fora do prazo.
- **Saída de estoque:** Registra a baixa/venda de medicamentos e exige a confirmação de retenção de receita para remédios controlados.

## 🛠️ Requisitos técnicos utilizados

- Entrada e saída no terminal (`println`, `readlnOrNull`)
- Operadores matemáticos, lógicos e relacionais
- Estruturas de decisão (`if/else` e `when`)
- Laços de repetição (`while` e `for` para percorrer coleções)
- Separação da lógica em funções
- Tratamento de nulos com Safe Call (`?.`) e Operador Elvis (`?:`)

## 💻 Como rodar

1. Certifique-se de ter o **JDK** e o **Kotlin** instalados (ou execute diretamente via **IntelliJ IDEA**).
2. Abra o arquivo `src/Main.kt`.
3. Execute a função `main()`.