package com.pucrs.agilstore.repository;

import com.pucrs.agilstore.enums.Categoria;
import com.pucrs.agilstore.model.Produto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findAll(Sort sort);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoria(Categoria categoria);
}
