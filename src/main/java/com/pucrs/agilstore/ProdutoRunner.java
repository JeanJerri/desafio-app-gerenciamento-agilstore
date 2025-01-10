package com.pucrs.agilstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProdutoRunner implements CommandLineRunner {

    private final ProdutoTerminal produtoTerminal;

    // Injeção automática do ProdutoTerminal pelo Spring
    public ProdutoRunner(ProdutoTerminal produtoTerminal) {
        this.produtoTerminal = produtoTerminal;
    }

    @Override
    public void run(String... args) throws Exception {
        // Chama o método iniciar() quando o programa começa
        produtoTerminal.iniciar();
    }
}
