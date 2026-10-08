// Modelo de Dados para Representar um Medicamento
data class Medicamento(
    val id: Int,
    val nome: String,
    val quantidade: Int,
    val quantidadeMinima: Int,
    val ehControlado: Boolean,
    val diasParaVencer: Int // Representação simples de validade em dias
)

// Estoque inicial da farmácia
val estoque = mutableListOf(
    Medicamento(1, "Dipirona 500mg", 45, 10, false, 180),
    Medicamento(2, "Amoxicilina 500mg", 8, 15, true, 30),
    Medicamento(3, "Rivotril 2mg (Controlado)", 5, 10, true, 5), // Próximo de vencer e estoque baixo
    Medicamento(4, "Paracetamol 750mg", 3, 12, false, 90),
    Medicamento(5, "Ibuprofeno 600mg", 20, 5, false, -2) // Vencido
)

fun main() {
    println("==============================================")
    println("    SISTEMA DE CONTROLE DE ESTOQUE DE FARMÁCIA ")
    println("==============================================")

    var executando = true

    // Laço de Repetição para o Menu Principal
    while (executando) {
        exibirMenu()
        print("Escolha uma opção: ")

        // Null Safety e tratamento de entrada
        val entrada = readlnOrNull()
        val opcao = entrada?.toIntOrNull() ?: -1

        // Estrutura de Condição (when)
        when (opcao) {
            1 -> listarEstoqueCompleto()
            2 -> verificarAlertasReabastecimento()
            3 -> verificarValidades()
            4 -> darBaixaMedicamento()
            5 -> {
                println("\nEncerrando o sistema de estoque... Até logo!")
                executando = false
            }
            else -> println("\n[ERRO] Opção inválida! Tente novamente.")
        }
    }
}

// Função para exibir as opções do menu
fun exibirMenu() {
    println("\n--- MENU PRINCIPAL ---")
    println("1. Listar Todo o Estoque")
    println("2. Verificar Alertas de Reabastecimento")
    println("3. Verificar Validade dos Medicamentos")
    println("4. Dar Baixa em Medicamento (Venda/Saída)")
    println("5. Sair")
}

// Função para listar todos os itens com suas informações
fun listarEstoqueCompleto() {
    println("\n==============================================")
    println("           ESTOQUE ATUAL DE FARMÁCIA          ")
    println("==============================================")

    // Laço de Repetição (for)
    for (med in estoque) {
        val tipo = if (med.ehControlado) "[RETENÇÃO DE RECEITA]" else "[VENDA LIVRE]"
        println("ID: ${med.id} | ${med.nome} $tipo")
        println("   Qtd em Estoque: ${med.quantidade} un | Qtd Mínima: ${med.quantidadeMinima} un")
        println("   Status Validade: ${formatarDiasValidade(med.diasParaVencer)}")
        println("----------------------------------------------")
    }
}

// Função com lógica relacional para emitir alertas de estoque baixo
fun verificarAlertasReabastecimento() {
    println("\n==============================================")
    println("       ALERTAS DE REABASTECIMENTO NECESSÁRIO  ")
    println("==============================================")

    var possuiAlerta = false

    for (med in estoque) {
        // Operadores Relacionais e Lógicos (<= para checar estoque mínimo)
        if (med.quantidade <= med.quantidadeMinima) {
            possuiAlerta = true
            val diferenca = med.quantidadeMinima - med.quantidade // Operador Matemático (-)
            println("⚠️ ALERTA: ${med.nome}")
            println("   Estoque atual: ${med.quantidade} | Mínimo exigido: ${med.quantidadeMinima}")
            println("   Necessário reabastecer pelo menos: $diferenca unidade(s).")

            if (med.ehControlado) {
                println("   🔴 ATENÇÃO: Medicamento CONTROLADO! Notificar fornecedor prioritário.")
            }
            println("----------------------------------------------")
        }
    }

    if (!possuiAlerta) {
        println("✅ Todos os medicamentos estão com estoque acima do limite mínimo.")
    }
}

// Função para checar validade usando condicionais e operadores lógicos
fun verificarValidades() {
    println("\n==============================================")
    println("          RELATÓRIO DE VALIDADE               ")
    println("==============================================")

    for (med in estoque) {
        // Operadores Relacionais e Lógicos
        if (med.diasParaVencer <= 0) {
            println("❌ VENCIDO: ${med.nome} (Venceu há ${med.diasParaVencer * -1} dias) - RETIRAR DO ESTOQUE!")
        } else if (med.diasParaVencer <= 15 && med.diasParaVencer > 0) {
            println("⚠️ VENCIMENTO PRÓXIMO: ${med.nome} (Vence em ${med.diasParaVencer} dias)")
        } else {
            println("✅ DENTRO DA VALIDADE: ${med.nome} (Vence em ${med.diasParaVencer} dias)")
        }
    }
}

// Função para realizar baixa/venda de medicamentos
fun darBaixaMedicamento() {
    println("\n--- DAR BAIXA EM MEDICAMENTO ---")
    print("Digite o ID do medicamento: ")

    // Null Safety usando Safe Call e Elvis Operator
    val idBuscado = readlnOrNull()?.toIntOrNull() ?: -1
    val medEncontrado = estoque.find { it.id == idBuscado }

    if (medEncontrado != null) {
        // Validação se está vencido antes de vender
        if (medEncontrado.diasParaVencer <= 0) {
            println("\n[ERRO BLOQUEANTE] Medicamento vencido! Não é permitida a saída/venda.")
            return
        }

        // Validação de retenção de receita para med controlado
        if (medEncontrado.ehControlado) {
            println("\n🔒 MEDICAMENTO CONTROLADO DETECTADO!")
            print("Você possui a receita médica retida em mãos? (S/N): ")
            val temReceita = readlnOrNull()?.trim()?.uppercase() ?: "N"

            if (temReceita != "S") {
                println("[ERRO] Saída RECUSADA: Não é possível vender medicamento controlado sem receita!")
                return
            }
        }

        print("Digite a quantidade de saída: ")
        val qtdSaida = readlnOrNull()?.toIntOrNull() ?: 0

        // Operadores Relacionais e Matemáticos
        if (qtdSaida > 0 && qtdSaida <= medEncontrado.quantidade) {
            val novaQtd = medEncontrado.quantidade - qtdSaida // Operador Matemático (-)

            // Atualiza o objeto no estoque
            val index = estoque.indexOf(medEncontrado)
            estoque[index] = medEncontrado.copy(quantidade = novaQtd)

            println("\n✅ Baixa realizada com sucesso!")
            println("Nova quantidade em estoque de '${medEncontrado.nome}': $novaQtd unidades.")

            // Verificação imediata se entrou na zona de reabastecimento
            if (novaQtd <= medEncontrado.quantidadeMinima) {
                println("⚠️ AVISO: O estoque deste item atingiu o limite mínimo para reabastecimento!")
            }
        } else {
            println("\n[ERRO] Quantidade inválida ou superior ao estoque disponível (${medEncontrado.quantidade} un).")
        }

    } else {
        println("\n[ERRO] Medicamento com ID $idBuscado não foi encontrado.")
    }
}

// Função auxiliar simples para texto de validade
fun formatarDiasValidade(dias: Int): String {
    return when {
        dias < 0 -> "VENCIDO"
        dias == 0 -> "Vence HOJE"
        else -> "$dias dia(s) restantes"
    }
}