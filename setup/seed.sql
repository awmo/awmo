insert into diagrama(name) values('Primeiro diagrama');

insert into classe(name, position_x, position_y, text_order, diagrama_id)
	values('Pessoa', 100, 100, 1, 1);

insert into classe(name, parent_classe_id, position_x, position_y, text_order, diagrama_id)
	values('Estudante', 1, 50, 300, 2, 1);

insert into classe(name, parent_classe_id, position_x, position_y, text_order, diagrama_id)
	values('Professor', 1, 150, 300, 3, 1);

insert into classe(name, position_x, position_y, text_order, diagrama_id)
	values('Gremio', 400, 300, 4, 1);
insert into classe(name, position_x, position_y, text_order, diagrama_id)
	values('C1', 400, 300, 4, 1);
insert into classe(name, position_x, position_y, text_order, diagrama_id)
	values('C2', 400, 300, 4, 1);
insert into classe(name, position_x, position_y, text_order, diagrama_id)
	values('C3', 400, 300, 4, 1);

insert into tipo(kind, classe_id) values (0, 1); -- Type Pessoa
insert into tipo(kind, classe_id) values (0, 2); -- Type Estudante
insert into tipo(kind, classe_id) values (0, 3); -- Type Professor
insert into tipo(kind, classe_id) values (0, 4); -- Type Gremio
insert into tipo(kind, classe_id) values (0, 5); -- Type Gremio
insert into tipo(kind, classe_id) values (0, 6); -- Type Gremio
insert into tipo(kind, classe_id) values (0, 7); -- Type Gremio

insert into relacao_classe(source_classe_id, target_classe_id, relation, source_cardinality, target_cardinality) 
	values(4, 2, 'associacao', '1', '*');

-- Pessoa
insert into atributo(name, visibility, tipo_id, classe_id) values('teste', 'private', 1, 1);

-- Estudante
insert into atributo(name, visibility, tipo_id, classe_id) values('nota', 'private', 3, 2);

insert into operacao(name, visibility, tipo_id, classe_id) values('getNota', 'public', 3, 2);
insert into operacao(name, visibility, tipo_id, classe_id) values('setNota', 'public', 4, 2);

insert into parametro(name, tipo_id, operacao_id) values ('nota', 3, 2);

-- Professor
insert into atributo(name, visibility, tipo_id, classe_id) values('id', 'private', 1, 3);
insert into atributo(name, visibility, tipo_id, classe_id) values('dois', 'public', 2, 3);

insert into operacao(name, visibility, tipo_id, classe_id) values('setId', 'public', 1, 2);

insert into parametro(name, tipo_id, operacao_id) values ('id', 1, 3);
insert into parametro(name, tipo_id, operacao_id) values ('sendEmail', 4, 3);

