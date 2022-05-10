package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query(nativeQuery = true, value = "Select count(1) > 0 from produto Where upper(trim(nome)) = ?1")
	public boolean existeProduto(String nomeProduto);
	
	@Query(nativeQuery = true, value = "Select count(1) > 0 from produto Where upper(trim(nome)) = ?1 ")
	public boolean existeProduto(String nomeProduto, Long idEmpresa);	

	@Query("Select a from Produto a Where upper(trim(a.nome)) like %?1%")
	public List<Produto> buscarProdutoNome(String nome);
	
	@Query("Select a from Produto a Where upper(trim(a.nome)) like %?1% and a.empresa.id = ?2")
	public List<Produto> buscarProdutoNome(String nome, Long idEmpresa);
	
	
}

