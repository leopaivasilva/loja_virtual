package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.CupDesconto;

@Repository
@Transactional
public interface CupDescontoRepository extends JpaRepository<CupDesconto, Long> {
	
	@Query(value = "select c from CupDesconto c where c.empresa.id = ?1")
	public List<CupDesconto> cupDescontoPorEmpresa(Long idEmpresa);	

}
