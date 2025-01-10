package com.pucrs.agilstore.model;

import com.pucrs.agilstore.enums.Categoria;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTO")
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_SEQ")
    @SequenceGenerator(name = "PRODUTO_SEQ", sequenceName = "SEQUENCE_PRODUTO", allocationSize = 1)
    @Column(name = "CODIGO_PRODUTO", nullable = false, unique = true)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA", nullable = false)
    private Categoria categoria;

    @Column(name = "QUANTIDADE", nullable = false)
    private Integer quantidade;

    @Column(name = "PRECO", nullable = false)
    private BigDecimal preco;

    public Produto(String nome, Categoria categoria, Integer quantidade, BigDecimal preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-30s %-15s %-22d %-7.2f", id, nome, categoria, quantidade, preco);
    }
}
