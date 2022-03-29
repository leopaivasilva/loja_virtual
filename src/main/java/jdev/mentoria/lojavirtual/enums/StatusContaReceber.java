package jdev.mentoria.lojavirtual.enums;

public enum StatusContaReceber {
	
	COBRANCA("Cobrança"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	LIQUIDADA("Liquidada");
	
   private String descricao;
	
	private StatusContaReceber(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}


}
