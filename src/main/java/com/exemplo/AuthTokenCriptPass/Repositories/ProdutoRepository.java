package com.exemplo.AuthTokenCriptPass.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.exemplo.AuthTokenCriptPass.entities.Produto;

//@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {

}
