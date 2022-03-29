CREATE OR REPLACE FUNCTION validaChavePessoa()
RETURNS trigger 
LANGUAGE PLPGSQL
AS $$
DECLARE existe integer;
BEGIN

  existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_id);

  if (existe <= 0) then
    existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_id);

  if (existe <= 0) then
    RAISE EXCEPTION 'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
  end if;
  end if;  

RETURN NEW;
END;
$$


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoAtualizar
BEFORE UPDATE
ON avaliacao_produto
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoInserir
BEFORE INSERT
ON avaliacao_produto
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


CREATE OR REPLACE FUNCTION validaChavePessoaFornecedor()
RETURNS trigger 
LANGUAGE PLPGSQL
AS $$
DECLARE existe integer;
BEGIN

  existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_forn_id);

  if (existe <= 0) then
    existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_forn_id);

  if (existe <= 0) then
    RAISE EXCEPTION 'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
  end if;
  end if;  

RETURN NEW;
END;
$$

CREATE TRIGGER validaChavePessoaFornecedorContaPagarAtualizar
BEFORE UPDATE
ON conta_pagar
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoaFornecedor();


CREATE TRIGGER validaChavePessoaFornecedorContaPagarInserir
BEFORE INSERT
ON conta_pagar
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoaFornecedor();


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoAtualizar
BEFORE UPDATE
ON conta_receber
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoInserir
BEFORE INSERT
ON conta_receber
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaAvaliacaoProdutoAtualizar
BEFORE UPDATE
ON endereco
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoInserir
BEFORE INSERT
ON endereco
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaAvaliacaoProdutoAtualizar
BEFORE UPDATE
ON nota_fiscal_compra
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoInserir
BEFORE INSERT
ON nota_fiscal_compra
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaAvaliacaoProdutoAtualizar
BEFORE UPDATE
ON usuario
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoInserir
BEFORE INSERT
ON usuario
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaAvaliacaoProdutoAtualizar
BEFORE UPDATE
ON vd_cp_loja_virt
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


CREATE TRIGGER validaChavePessoaAvaliacaoProdutoInserir
BEFORE INSERT
ON vd_cp_loja_virt
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();