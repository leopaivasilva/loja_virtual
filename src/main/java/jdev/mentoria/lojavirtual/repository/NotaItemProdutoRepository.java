package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.NotaItemProduto;

@Repository
@Transactional
public interface NotaItemProdutoRepository  extends JpaRepository<NotaItemProduto, Long> {
	
	@Query("Select a from NotaItemProduto a Where a.produto.id =?1 and a.notaFiscalCompra.id = ?2")
	List<NotaItemProduto> buscaNotaItemPorProdutoNota(Long idProduto, Long idNotaFiscal);
	
	@Query("Select a from NotaItemProduto a Where a.produto.id = ?1")
	List<NotaItemProduto> buscaNotaItemPorProduto(Long idProduto);
	
	@Query("Select a from NotaItemProduto a Where a.notaFiscalCompra.id = ?1")
	List<NotaItemProduto> buscaNotaItemPorNotaFiscal(Long idNotaFiscal);	
	
	@Query("Select a from NotaItemProduto a Where a.empresa.id = ?1")
	List<NotaItemProduto> buscaNotaItemPorEmpresa(Long idEmpresa);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value = "delete from nota_item_produto Where id = ?1")
	void deleteByIdNotaItem(Long id);	
	
}
