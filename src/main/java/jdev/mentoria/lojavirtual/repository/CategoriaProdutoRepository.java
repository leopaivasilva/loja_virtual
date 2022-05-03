package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.CategoriaProduto;


@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long>{
	
	@Query(nativeQuery = true, value = "Select count(1) > 0 from categoria_produto Where upper(trim(nome_desc)) = ?1")
	public boolean existeCategoria(String nomeCategoria);

	@Query("Select a from CategoriaProduto a Where upper(trim(a.nomeDesc)) like %?1%")
	public List<CategoriaProduto> buscarCategoriaDesc(String nomeDesc);
	
}
