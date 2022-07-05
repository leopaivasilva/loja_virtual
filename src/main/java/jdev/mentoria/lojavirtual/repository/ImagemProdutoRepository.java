package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.ImagemProduto;

@Repository
@Transactional
public interface ImagemProdutoRepository  extends JpaRepository<ImagemProduto, Long> {
	
	@Query("Select a from ImagemProduto a Where a.produto.id = ?1")
	List<ImagemProduto> buscarImagemProduto(Long idProduto);
	
	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "delete from imagem_produto where produto_id = ?1")
	void deleteImagens(Long idProduto);

}
