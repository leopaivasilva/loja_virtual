package jdev.mentoria.lojavirtual.enums;

public enum StatusContaPagar {
	
	COBRANCA("Cobrança"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	LIQUIDADA("Liquidada"),
	NEGOCIADA("Renegociada");
	
   private String descricao;
	
	private StatusContaPagar(String descricao) {
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
