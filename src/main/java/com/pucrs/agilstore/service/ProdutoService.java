package com.pucrs.agilstore.service;

import com.pucrs.agilstore.enums.Categoria;
import com.pucrs.agilstore.model.Produto;
import com.pucrs.agilstore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public List<Produto> findAll(Sort sort) {
        return produtoRepository.findAll(sort);
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> findByNomeContainingIgnoreCase(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Produto> findByCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
