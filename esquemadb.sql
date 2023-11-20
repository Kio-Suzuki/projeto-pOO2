create table Fornecedora(
    forId serial primary key,
    forCnpj VARCHAR(14) not null,
    forRazaoSocial VARCHAR(50) not null,
    forEmail VARCHAR(50) not null,
    forTelefone VARCHAR(11) not null,
    forLogradouro VARCHAR(50) not null,
    forNumero INTEGER not null,
    forCep INTEGER not null,
	forCidade VARCHAR(50) not null,
    forEstado VARCHAR(2) not null
);

create table Produto(
    proId serial primary key,
    proNome VARCHAR(50) not null,
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
    traCep INT not null,
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

create view ver_estoque_produto as
select
	p.proid as produto_id,
	p.pronome,
	p.propreco,
	p.procategoria,
	e.estquantidade,
	f.forRazaoSocial,
	e.estid,
	e.estlocal,
	e.estdataentrada,
	e.estdatavalidade
from
	produto p
inner join estoque e on
	p.proid = e.proid
inner join Fornecedora f on
	p.forid = f.forid
where
	e.estquantidade > 0;

create or replace
function add_produto_estoque(
    p_pronome VARCHAR(50),
    p_propreco FLOAT,
    p_procategoria VARCHAR(20),
    p_forID INTEGER,
    p_estquantidade INTEGER,
    p_estlocal VARCHAR(50),
    p_estdataentrada DATE,
    p_estdatavalidade DATE,
    p_funid INTEGER
) returns VOID as $$
declare
    new_produto_id INTEGER;

existing_produto_id INTEGER;

begin

    select
	produto.proId
into
	existing_produto_id
from
	produto
inner join estoque on
	produto.proId = estoque.proId
where
	produto.proNome = p_pronome
	and estoque.estDataValidade = p_estdatavalidade
	and estoque.estDataEntrada = p_estdataentrada
limit 1;

if existing_produto_id is not null then
-- Se existir, adiciona a quantidade atual do produto
        update
	estoque
set
	estQuantidade = estQuantidade + p_estquantidade
where
	proId = existing_produto_id;

new_produto_id := existing_produto_id;
else
        select
	proId
into
	new_produto_id
from
	produto
where
	proNome = p_pronome
limit 1;

if new_produto_id is null then
            insert
	into
	produto (proNome,
	proPreco,
	proCategoria,
	forId)
values (p_pronome,
p_propreco,
p_procategoria,
p_forID)
            returning proId
into
	new_produto_id;

insert
	into
	estoque (proId,
	estQuantidade,
	estLocal,
	estDataEntrada,
	estDataValidade)
values (new_produto_id,
p_estquantidade,
p_estlocal,
p_estdataentrada,
p_estdatavalidade);
else
            insert
	into
	estoque (proId,
	estQuantidade,
	estLocal,
	estDataEntrada,
	estDataValidade)
values (new_produto_id,
p_estquantidade,
p_estlocal,
p_estdataentrada,
p_estdatavalidade);
end if;
end if;
-- Insere na auditoria
    insert
	into
	auditoria (funId,
	proId,
	acao,
	quantidade,
	datahora)
values (p_funid,
new_produto_id,
1,
p_estquantidade,
NOW());
end $$ language plpgsql;

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
	
	select
	estQuantidade
into
	quantidade_atual
from
	estoque
where
	estoque.proId = p_proid
	and estDataValidade = origem_estdatavalidade
	and estDataEntrada = origem_estdataentrada
limit 1;

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

insert
	into
	auditoria (funId,
	proId,
	acao,
	quantidade,
	datahora)
values (p_funid,
p_proid,
2,
p_estquantidade - quantidade_atual,
NOW());
end $$ language plpgsql;

create or replace
function delete_produto_estoque(p_proID INTEGER,
p_estdataentrada DATE,
p_estdatavalidade DATE,
p_funid INTEGER) returns VOID as $$
declare
	quantidade_atual INTEGER;

begin
	
	select
	estQuantidade
into
	quantidade_atual
from
	produto p
inner join estoque e on
	p.proid = e.proid
where
	p.proid = p_proID
	and e.estDataEntrada = p_estdataentrada
	and e.estDataValidade = p_estdatavalidade
limit 1;

delete
from
	estoque
where
	proId = p_proID;
end $$ language plpgsql;




    private void popularComboBox() throws ClassNotFoundException {
        TransportadoraDao transportadoraDao = new TransportadoraDaoJDBC(DB.getConnection());
        List<Pair<Integer, String>> transportadoraes = transportadoraDao.findAllForCombobox();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (Pair<Integer, String> transportadora : transportadoraes) {
            model.addElement(transportadora.getSecond());
        }

        jComboBoxTransportadoras.setModel(model);
    }