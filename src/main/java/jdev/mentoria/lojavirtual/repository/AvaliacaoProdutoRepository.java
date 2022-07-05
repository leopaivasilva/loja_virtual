package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.AvaliacaoProduto;

@Repository
@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {
	
	@Query("Select a from AvaliacaoProduto a Where a.produto.id = ?1")
	List<AvaliacaoProduto> avaliacaoProduto(Long idProduto);
	
	@Query("Select a from AvaliacaoProduto a Where a.produto.id = ?1 and a.pessoa.id = ?2")
	List<AvaliacaoProduto> avaliacaoProdutoPessoa(Long idProduto, Long idPessoa);
	
	@Query("Select a from AvaliacaoProduto a Where a.pessoa.id = ?1")
	List<AvaliacaoProduto> avaliacaoPessoa(Long idPessoa);	

}
