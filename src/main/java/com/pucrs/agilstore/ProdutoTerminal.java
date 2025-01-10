package com.pucrs.agilstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pucrs.agilstore.enums.Categoria;
import com.pucrs.agilstore.model.Produto;
import com.pucrs.agilstore.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ProdutoTerminal {

    private static final String ARQUIVO_JSON = "produtos.json";

    private final ProdutoService produtoService;
    private final Scanner scanner;

    @Autowired
    public ProdutoTerminal(ProdutoService produtoService) {
        this.produtoService = produtoService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        Integer opcao = -1;
        
        while (opcao != 0) {
            System.out.println("\nMenu de opções:\n");
            System.out.println("1 - Adicionar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Atualizar Produto");
            System.out.println("4 - Excluir Produto");
            System.out.println("5 - Buscar Produto");
            System.out.println("0 - Sair\n");

            opcao = null;
            while (opcao == null) {
                System.out.print("Informe o número da opção desejada: ");
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                } else {
                    System.out.println("Entrada inválida! Digite um número entre 0 e 5.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    atualizarProduto();
                    break;
                case 4:
                    excluirProduto();
                    break;
                case 5:
                    buscarProduto();
                    break;
                case 0:
                    System.out.println("Encerrando aplicação...");
                    break;
                default:
                    System.out.println("Opção Inválida! Digite um número entre 0 e 5.");
            }
        }
    }

    private void salvarProdutosEmArquivo() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Produto> produtos = produtoService.findAll();
            objectMapper.writeValue(new File(ARQUIVO_JSON), produtos);
            System.out.println("Dados salvos no arquivo JSON com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados no arquivo JSON: " + e.getMessage());
        }
    }

    private void buscarProduto() {
        String resposta = "";
        while (!resposta.equalsIgnoreCase("I") && !resposta.equalsIgnoreCase("N")) {
            System.out.println("Deseja buscar por ID ou por nome? (Digite I para ID ou N para nome)");
            resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("I") && !resposta.equalsIgnoreCase("N")) {
                System.out.println("Opção inválida! Digite I para ID ou N para nome.");
            }
        }

        if (resposta.equalsIgnoreCase("I")) {
            Long id = null;
            while (id == null) {
                System.out.print("Informe o ID do Produto: ");
                if (scanner.hasNextLong()) {
                    id = scanner.nextLong();
                    if (id <= 0) {
                        System.out.println("ID inválido! Deve ser um número positivo.");
                        id = null;
                    }
                } else {
                    System.out.println("Entrada inválida! Por favor, digite um número.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();

            Optional<Produto> optionalProduto = produtoService.findById(id);
            Produto produto = optionalProduto.orElse(null);

            if (produto == null) {
                System.out.println("Produto não encontrado");
            } else {
                imprimirProduto(produto);
            }

        } else if (resposta.equalsIgnoreCase("N")) {
            System.out.print("Informe o nome do Produto: ");
            String nome = scanner.nextLine();

            List<Produto> produtos = produtoService.findByNomeContainingIgnoreCase(nome);

            if (produtos.isEmpty()) {
                System.out.println("Produto não encontrado");
            } else {
                imprimirListaProdutos(produtos);
            }
        }
    }

    private void excluirProduto() {
        Long id = null;
        while (id == null) {
            System.out.print("Informe o ID do Produto: ");
            if (scanner.hasNextLong()) {
                id = scanner.nextLong();
                if (id <= 0) {
                    System.out.println("ID inválido! Deve ser um número positivo.");
                    id = null;
                }
            } else {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();


        Optional<Produto> optionalProduto = produtoService.findById(id);
        Produto produto = optionalProduto.orElse(null);

        if (produto == null) {
            System.out.println("Produto não encontrado");
            return;
        }

        imprimirProduto(produto);
        System.out.println();
        String confirmacao = "";
        while (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
            System.out.print("Deseja realmente excluir o produto? (S/N): ");
            confirmacao = scanner.nextLine();
            if (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
                System.out.println("Opção inválida! Digite S para Sim ou N para Não.");
            }
        }

        if (confirmacao.equalsIgnoreCase("N")) {
            System.out.println("Operação cancelada");
            return;
        }

        produtoService.deletar(id);
        salvarProdutosEmArquivo();
        System.out.println("Produto excluído com sucesso!");
    }

    private void atualizarProduto() {
        Long id = null;
        while (id == null) {
            System.out.print("Informe o ID do Produto: ");
            if (scanner.hasNextLong()) {
                id = scanner.nextLong();
                if (id <= 0) {
                    System.out.println("ID inválido! Deve ser um número positivo.");
                    id = null;
                }
            } else {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        Optional<Produto> optionalProduto = produtoService.findById(id);
        Produto produto = optionalProduto.orElse(null);

        if (produto == null) {
            System.out.println("Produto não encontrado");
            return;
        }

        imprimirProduto(produto);

        String confirmacao = "";
        while (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
            System.out.print("Deseja atualizar o Nome do produto? (S/N): ");
            confirmacao = scanner.nextLine();
            if (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
                System.out.println("Opção inválida! Digite S para Sim ou N para Não.");
            }
        }
        if (confirmacao.equalsIgnoreCase("S")) {
            System.out.print("Nome do Produto: ");
            String nome = scanner.nextLine();
            produto.setNome(nome);
        }

        confirmacao = "";
        while (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
            System.out.print("Deseja atualizar a Categoria do produto? (S/N): ");
            confirmacao = scanner.nextLine();
            if (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
                System.out.println("Opção inválida! Digite S para Sim ou N para Não.");
            }
        }
        if (confirmacao.equalsIgnoreCase("S")) {
            Categoria categoria = null;
            while (categoria == null) {
                try {
                    System.out.print("Categoria: ");
                    categoria = Categoria.valueOf(scanner.nextLine().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Categoria inválida! Tente novamente.");
                }
            }
            produto.setCategoria(categoria);
        }

        confirmacao = "";
        while (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
            System.out.print("Deseja atualizar a Quantidade do produto? (S/N): ");
            confirmacao = scanner.nextLine();
            if (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
                System.out.println("Opção inválida! Digite S para Sim ou N para Não.");
            }
        }
        if (confirmacao.equalsIgnoreCase("S")) {
            Integer quantidade = null;
            while (quantidade == null) {
                System.out.print("Quantidade em Estoque: ");
                if (scanner.hasNextInt()) {
                    quantidade = scanner.nextInt();
                    if (quantidade < 0) {
                        System.out.println("Quantidade não pode ser negativa. Tente novamente.");
                        quantidade = null;
                    }
                } else {
                    System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            produto.setQuantidade(quantidade);
        }

        confirmacao = "";
        while (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
            System.out.print("Deseja atualizar o Preço do produto? (S/N): ");
            confirmacao = scanner.nextLine();
            if (!confirmacao.equalsIgnoreCase("S") && !confirmacao.equalsIgnoreCase("N")) {
                System.out.println("Opção inválida! Digite S para Sim ou N para Não.");
            }
        }
        if (confirmacao.equalsIgnoreCase("S")) {
            BigDecimal preco = null;
            while (preco == null) {
                System.out.print("Preço: ");
                if (scanner.hasNextBigDecimal()) {
                    preco = scanner.nextBigDecimal();
                    scanner.nextLine();
                    if (preco.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.println("O preço não pode ser negativo. Tente novamente.");
                        preco = null;
                    }
                } else {
                    System.out.println("Entrada inválida! Por favor, digite um número decimal.");
                    scanner.nextLine();
                }
            }
            produto.setPreco(preco);
        }

        produtoService.salvar(produto);
        salvarProdutosEmArquivo();
        System.out.println("Produto atualizado com sucesso!");
    }

    private void listarProdutos() {
        System.out.println("Deseja filtrar por categoria ou ordenar por nome ou preço? (Digite F para filtrar, O para ordenar ou qualquer outra tecla para listar todos)");
        String opcao = scanner.nextLine();
        System.out.println();

        if (opcao.equalsIgnoreCase("F")) {
            Categoria categoria = null;
            while (categoria == null) {
                try {
                    System.out.print("Informe a categoria para a filtragem: ");
                    categoria = Categoria.valueOf(scanner.nextLine().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Categoria inválida! Tente novamente.");
                }
            }

            List<Produto> produtos = produtoService.findByCategoria(categoria);
            imprimirListaProdutos(produtos);

        } else if (opcao.equalsIgnoreCase("O")) {
            String resposta = "";
            while (!resposta.equalsIgnoreCase("N") && !resposta.equalsIgnoreCase("P")) {
                System.out.print("Ordenar por nome ou preço? (Digite N para nome ou P para preço): ");
                resposta = scanner.nextLine();
                if (!resposta.equalsIgnoreCase("N") && !resposta.equalsIgnoreCase("P")) {
                    System.out.println("Opção inválida! Digite N para Nome ou P para Preço.");
                }
            }
            String ordenarPor = resposta.equalsIgnoreCase("N") ? "nome" : "preco";

            resposta = "";
            while (!resposta.equalsIgnoreCase("ASC") && !resposta.equalsIgnoreCase("DESC")) {
                System.out.print("Ordenação crescente ou decrescente? (Digite ASC para crescente ou DESC para decrescente): ");
                resposta = scanner.nextLine();
                if (!resposta.equalsIgnoreCase("ASC") && !resposta.equalsIgnoreCase("DESC")) {
                    System.out.println("Opção inválida! Digite ASC para crescente ou DESC para decrescente.");
                }
            }
            Sort.Direction direcaoOrdenacao = resposta.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;

            Sort sort = Sort.by(direcaoOrdenacao, ordenarPor);

            List<Produto> produtos = produtoService.findAll(sort);
            imprimirListaProdutos(produtos);
        } else {
            List<Produto> produtos = produtoService.findAll();
            imprimirListaProdutos(produtos);
        }
    }

    private void adicionarProduto() {
        System.out.print("Nome do Produto: ");
        String nome = scanner.nextLine();

        Categoria categoria = null;
        while (categoria == null) {
            try {
                System.out.print("Categoria: ");
                categoria = Categoria.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Categoria inválida! Tente novamente.");
            }
        }

        Integer quantidade = null;
        while (quantidade == null) {
            System.out.print("Quantidade em Estoque: ");
            if (scanner.hasNextInt()) {
                quantidade = scanner.nextInt();
                if (quantidade < 0) {
                    System.out.println("Quantidade não pode ser negativa. Tente novamente.");
                    quantidade = null;
                }
            } else {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        BigDecimal preco = null;
        while (preco == null) {
            System.out.print("Preço: ");
            if (scanner.hasNextBigDecimal()) {
                preco = scanner.nextBigDecimal();
                if (preco.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("O preço não pode ser negativo. Tente novamente.");
                    preco = null;
                }
            } else {
                System.out.println("Entrada inválida! Por favor, digite um número decimal.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        Produto produto = new Produto(nome, categoria, quantidade, preco);
        produtoService.salvar(produto);
        salvarProdutosEmArquivo();
        System.out.println("Produto adicionado com sucesso!");
    }

    private void imprimirProduto(Produto produto) {
        System.out.printf("%-10s %-30s %-15s %-22s %-7s\n", "ID", "Nome do Produto", "Categoria", "Quantidade em Estoque", "Preço");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println(produto);
    }

    private void imprimirListaProdutos(List<Produto> produtos) {
        System.out.printf("%-10s %-30s %-15s %-22s %-7s\n", "ID", "Nome do Produto", "Categoria", "Quantidade em Estoque", "Preço");
        System.out.println("----------------------------------------------------------------------------------------------");

        produtos.forEach(System.out::println);
    }
}
