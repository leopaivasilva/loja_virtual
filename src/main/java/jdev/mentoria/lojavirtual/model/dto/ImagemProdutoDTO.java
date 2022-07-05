package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class ImagemProdutoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String imagemOriginal;
	
	private String imagemMinitura;	
	
	private Long produto;
	
	private Long empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImagemOriginal() {
		return imagemOriginal;
	}

	public void setImagemOriginal(String imagemOriginal) {
		this.imagemOriginal = imagemOriginal;
	}

	public String getImagemMinitura() {
		return imagemMinitura;
	}

	public void setImagemMinitura(String imagemMinitura) {
		this.imagemMinitura = imagemMinitura;
	}

	public Long getProduto() {
		return produto;
	}

	public void setProduto(Long produto) {
		this.produto = produto;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

}
