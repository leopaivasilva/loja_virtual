package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.NotaFiscalCompra;

@Repository
@Transactional
public interface NotaFiscalCompraRepository  extends JpaRepository<NotaFiscalCompra, Long> {
	
	@Query("Select a from NotaFiscalCompra a Where upper(trim(a.descricaoOBS)) like %?1%")
	List<NotaFiscalCompra> buscarNotaDesc(String desc);
	
	@Query("Select a from NotaFiscalCompra a Where a.pessoa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorPessoa(Long idPessoa);
	
	@Query("Select a from NotaFiscalCompra a Where a.contaPagar.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorContaPagar(Long idContaPagar);
	
	
	@Query("Select a from NotaFiscalCompra a Where a.empresa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorEmpresa(Long idEmpresa);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value = "delete from nota_item_produto Where nota_fiscal_compra_id = ?1")
	void deleteItemNotaFiscalCompra(Long idNotaFiscalCompra);
	
}
