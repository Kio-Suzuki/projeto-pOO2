do $$
declare
    table_name text;

function_name text;

trigger_name text;

begin
-- Dropping all tables
    for table_name in (
select
	information_schema.tables.table_name
from
	information_schema.tables
where
	information_schema.tables.table_schema = 'public'
	and information_schema.tables.table_type = 'BASE TABLE')
    loop
        execute 'DROP TABLE IF EXISTS public.' || table_name || ' CASCADE';
end loop;
-- Dropping all functions
    for function_name in (
select
	routine_name
from
	information_schema.routines
where
	routine_type = 'FUNCTION'
	and specific_schema = 'public')
    loop
        execute 'DROP FUNCTION IF EXISTS public.' || function_name || ' CASCADE';
end loop;
-- Dropping all triggers
    for trigger_name in (
select
	information_schema.triggers.trigger_name
from
	information_schema.triggers
where
	trigger_schema = 'public')
    loop
        execute 'DROP TRIGGER IF EXISTS public.' || information_schema.triggers.trigger_name || ' ON ' || information_schema.triggers.trigger_name || ' CASCADE';
end loop;
end $$;

create table Fornecedora(
    forId serial primary key,
    forCnpj VARCHAR(14) not null,
    forRazaoSocial VARCHAR(50) not null,
    forEmail VARCHAR(50) not null,
    forTelefone VARCHAR(11) not null,
    forLogradouro VARCHAR(50) not null,
    forNumero INTEGER not null,
    forCep VARCHAR(8) not null,
	forCidade VARCHAR(50) not null,
    forEstado VARCHAR(2) not null
);

create table Produto(
    proId serial priproNome VARCHAR(50) not null,
   mary key,
     proPreco FLOAT not null,
    proCategoria VARCHAR(20) not null,
    forId INTEGER not null
);

create table Estoque(
    estId serial primary key, 
    estQuantidade INTEGER not null, 
    estLocal VARCHAR(50) not null,
    estDataEntrada DATE not null,
    estDataValidade DATE not null,
    proId INTEGER not null
);

create table funcionario (
    funId serial primary key,
    funNome VARCHAR(50) not null,
    funSobrenome VARCHAR(50) not null,
    funCpf VARCHAR(11) not null,
    funTelefone VARCHAR(11) not null,
    funDepartamento VARCHAR(50) not null,
    funSalario FLOAT not null
);

create table transportadora (
    traId SERIAL primary key,
    traCnpj VARCHAR(14) not null,
    traRazaoSocial VARCHAR(50) not null,
    traEmail VARCHAR(50) not null,
    traTelefone VARCHAR(11) not null,
    traLogradouro VARCHAR(50) not null,
    traNumero INTEGER not null,
    traCep INTEGER not null,
    traCidade VARCHAR(50) not null,
    traEstado VARCHAR(50) not null
);

create table auditoria (
    audId SERIAL primary key,
    funId INTEGER,
    proId INTEGER,
    acao INTEGER,
    quantidade INTEGER,
    datahora TIMESTAMP
);



alter table Produto
add constraint fk_produto_Fornecedora
foreign key (forId) references Fornecedora(forId);

alter table Estoque
add constraint fk_estoque_produto
foreign key (proId) references Produto(proId);

alter table Auditoria
add constraint fk_auditoria_funcionario
foreign key (funId) references Funcionario(funId);

alter table Auditoria
add constraint fk_auditoria_produto
foreign key (proId) references Produto(proId);


CREATE VIEW ver_estoque_produto AS
SELECT
    p.proid AS produto_id,
    p.pronome,
    p.propreco,
    p.procategoria,
    e.estquantidade,
    e.estlocal,
    e.estdataentrada,
    e.estdatavalidade
FROM
    produto p
INNER JOIN estoque e ON p.proid = e.proid
WHERE
    e.estquantidade > 0;

	
CREATE OR REPLACE FUNCTION add_produto_estoque(
    p_pronome VARCHAR(50),
    p_propreco FLOAT,
    p_procategoria VARCHAR(20),
    p_forID INTEGER,
    p_estquantidade INTEGER,
    p_estlocal VARCHAR(50),
    p_estdataentrada DATE,
    p_estdatavalidade DATE,
    p_funid INTEGER
) RETURNS VOID AS $$
DECLARE
    new_produto_id INTEGER;
    existing_produto_id INTEGER;
BEGIN

    SELECT produto.proId INTO existing_produto_id
    FROM produto
    INNER JOIN estoque ON produto.proId = estoque.proId
    WHERE produto.proNome = p_pronome
        AND estoque.estDataValidade = p_estdatavalidade
        AND estoque.estDataEntrada = p_estdataentrada
    LIMIT 1;

    IF existing_produto_id IS NOT NULL THEN
        -- Se existir, adiciona a quantidade atual do produto
        UPDATE estoque
        SET estQuantidade = estQuantidade + p_estquantidade
        WHERE proId = existing_produto_id;
        new_produto_id := existing_produto_id;
    ELSE
        SELECT proId INTO new_produto_id
        FROM produto
        WHERE proNome = p_pronome
        LIMIT 1;

        IF new_produto_id IS NULL THEN
            INSERT INTO produto (proNome, proPreco, proCategoria, forId)
            VALUES (p_pronome, p_propreco, p_procategoria, p_forID)
            RETURNING proId INTO new_produto_id;

            INSERT INTO estoque (proId, estQuantidade, estLocal, estDataEntrada, estDataValidade)
            VALUES (new_produto_id, p_estquantidade, p_estlocal, p_estdataentrada, p_estdatavalidade);
        ELSE
            INSERT INTO estoque (proId, estQuantidade, estLocal, estDataEntrada, estDataValidade)
            VALUES (new_produto_id, p_estquantidade, p_estlocal, p_estdataentrada, p_estdatavalidade);
        END IF;
    END IF;

    -- Insere na auditoria
    INSERT INTO auditoria (funId, proId, acao, quantidade, datahora)
    VALUES (p_funid, new_produto_id, 1, p_estquantidade, NOW());

END $$ LANGUAGE plpgsql;	





create or replace
function upd_produto_estoque(
    p_proID INTEGER,
    p_pronome VARCHAR(50),
    p_propreco FLOAT,
    p_procategoria VARCHAR(20),
    p_forID INTEGER,
    p_estquantidade INTEGER,
    p_estlocal VARCHAR(50),
    p_estdataentrada DATE,
    p_estdatavalidade DATE,
    origem_estdataentrada DATE,
   	origem_estdatavalidade DATE,
    p_funid INTEGER
) returns VOID as $$
declare
	quantidade_atual INTEGER;
begin
	
	SELECT estQuantidade INTO quantidade_atual
    FROM estoque
    WHERE estoque.proId = p_proid
        AND estDataValidade = origem_estdatavalidade
        AND estDataEntrada = origem_estdataentrada
    LIMIT 1;
   
    update
	produto
set
	proNome = coalesce(p_pronome,
	proNome),
	proPreco = coalesce(p_propreco,
	proPreco),
	proCategoria = coalesce(p_procategoria,
	proCategoria),
	forId = coalesce(p_forID,
	forId)
where
	proId = p_proID;

update
	estoque
set
	estQuantidade = coalesce(p_estquantidade,
	estQuantidade),
	estLocal = coalesce(p_estlocal,
	estLocal),
	estDataEntrada = coalesce(p_estdataentrada,
	estDataEntrada),
	estDataValidade = coalesce(p_estdatavalidade,
	estDataValidade)
where
	proId = p_proID;

    INSERT INTO auditoria (funId, proId, acao, quantidade, datahora)
    VALUES (p_funid, p_proid, 2, p_estquantidade - quantidade_atual, NOW());
   
   

end $$ language plpgsql;


create or replace
function delete_produto_estoque(p_proID INTEGER,  p_estdataentrada DATE, p_estdatavalidade DATE, p_funid INTEGER) returns VOID as $$
declare
	quantidade_atual INTEGER;
begin
	
	SELECT estQuantidade INTO quantidade_atual
    FROM produto p
    INNER JOIN estoque e ON p.proid = e.proid 
    WHERE p.proid = p_proID
      AND e.estDataEntrada = p_estdataentrada
      AND e.estDataValidade = p_estdatavalidade
    LIMIT 1;
   
    delete
from
	estoque
where
	proId = p_proID;

insert into auditoria(funid, proid, acao, quantidade, datahora) values (p_funid, p_proid, 3, quantidade_atual-2*quantidade_atual, NOW());
end $$ language plpgsql;
