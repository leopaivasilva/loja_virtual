package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.ContaPagar;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
	
	@Query("Select a from ContaPagar a Where upper(trim(a.descricao)) like %?1%")
	List<ContaPagar> buscarContaDesc(String desc);

	@Query("Select a from ContaPagar a Where a.pessoa.id = ?1")
	List<ContaPagar> buscarContaPorPessoa(Long idPessoa);

	@Query("Select a from ContaPagar a Where a.pessoa_fornecedor.id = ?1")
	List<ContaPagar> buscarContaPorFornecedor(Long idFornecedor);

	@Query("Select a from ContaPagar a Where a.empresa.id = ?1")
	List<ContaPagar> buscarContaPorEmpresa(Long idEmpresa);
	
}
