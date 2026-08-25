USE clube_tamoios;

-- Genero
INSERT INTO Genero (nome) VALUES ('Masculino'), ('Feminino');

-- Classe
INSERT INTO Classe (nome) VALUES
    ('Amigo'), ('Companheiro'), ('Pesquisador'), ('Pioneiro'), ('Guia'), ('Excursionista');

-- Especialidade
INSERT INTO Especialidade (nome, categoria, descricao) VALUES
    ('Primeiros Socorros', 'Atividades Recreativas (AR)', 'Técnicas básicas de primeiros socorros'),
    ('Astronomia', 'Estudos da Natureza (EN)', 'Estudo dos corpos celestes'),
    ('Culinária', 'Habilidades Domésticas (HD)', 'Preparo de alimentos ao ar livre'),
    ('Nós e Amarras', 'Artes Manuais (AM)', 'Amarras e nós usados em acampamento'),
    ('Sementes', 'Estudos da Natureza (EN)', 'Identificação e germinação de sementes'),
    ('Reciclagem', 'Estudos da Natureza (EN)', 'Separação de resíduos e destino correto'),
    ('Música', 'Atividades Espirituais (AE)', 'Fundamentos musicais aplicados ao clube'),
    ('Natação', 'Atividades Recreativas (AR)', 'Habilidades básicas de natação');

-- Cargo
INSERT INTO Cargo (Nome) VALUES
    ('Diretor'), ('Secretário'), ('Tesoureiro'), ('Desbravador'), ('Instrutor');

-- Unidade
INSERT INTO Unidade (nome, Genero_idGenero, faixa_etaria) VALUES
    ('Unidade Tamoios', 1, '12 - 13'),
    ('Unidade Arandu', 2, '13 - 14'),
    ('Unidade Guaianases', 1, '10 - 11'),
    ('Unidade Tupiniquim', 2, '>15');

-- Evento
INSERT INTO Evento (nome, tipo, data_inicio, data_fim, descricao) VALUES
    ('Campori 2026', 'Acampamento', '2026-07-10', '2026-07-15', 'Grande acampamento regional'),
    ('Reunião Mensal Maio', 'Reunião', '2026-05-20', NULL, 'Reunião mensal do clube');

-- Chamada
INSERT INTO Chamada (Evento_idEvento, data_chamada, titulo) VALUES
    (2, '2026-05-20', 'Chamada Reunião Maio');

-- Medicamento
INSERT INTO Medicamento (Nome) VALUES
    ('Paracetamol'), ('Ibuprofeno'), ('Amoxicilina'), ('Dipirona'), ('Loratadina');

-- Comorbidade
INSERT INTO Comorbidade (Nome) VALUES
    ('Diabetes Tipo 1'), ('Hipertensão'), ('Asma'), ('Rinite Alérgica'), ('Epilepsia');

-- Pessoa
INSERT INTO Pessoa (Classe_idClasse, Genero_idGenero, Unidade_idUnidade, nome, cpf, rg, data_nascimento, telefone, isDesbravador, idResponsavel, fkCargo) VALUES
    (4, 1, 1, 'Carlos Silva',      '111.111.111-11', '1111111', '2012-03-15', '(11) 91111-1111', TRUE,  NULL, 4),
    (3, 2, 1, 'Ana Souza',         '222.222.222-22', '2222222', '2013-06-22', '(11) 92222-2222', TRUE,  NULL, 4),
    (5, 1, 2, 'Pedro Oliveira',    '333.333.333-33', '3333333', '2011-09-10', '(11) 93333-3333', TRUE,  NULL, 4),
    (2, 2, 2, 'Maria Santos',      '444.444.444-44', '4444444', '2014-01-05', '(11) 94444-4444', TRUE,  NULL, 4),
    (1, 1, 1, 'João Lima',         '555.555.555-55', '5555555', '2015-11-30', '(11) 95555-5555', TRUE,  NULL, 4),
    (NULL, 1, 1, 'Roberto Costa',  '666.666.666-66', '6666666', '1985-04-18', '(11) 96666-6666', FALSE, NULL, 1),
    (NULL, 2, 1, 'Fernanda Alves', '777.777.777-77', '7777777', '1990-08-25', '(11) 97777-7777', FALSE, NULL, 2),
    (NULL, 1, 2, 'Marcos Pereira', '888.888.888-88', '8888888', '1988-12-03', '(11) 98888-8888', FALSE, NULL, 3);

INSERT INTO Usuario (Pessoa_idPessoa, fkCargo, email, senha) VALUES
    (6, 1, 'roberto.costa@clube.com',   '$2a$10$DBFC490wr.eMeDvHOSmB7O0fOroyVLsy4qxmcThxcYKpfUfLhtlbO'),
    (7, 2, 'fernanda.alves@clube.com',  '$2a$10$DBFC490wr.eMeDvHOSmB7O0fOroyVLsy4qxmcThxcYKpfUfLhtlbO'),
    (8, 3, 'marcos.pereira@clube.com',  '$2a$10$DBFC490wr.eMeDvHOSmB7O0fOroyVLsy4qxmcThxcYKpfUfLhtlbO');

-- Pessoa sem Usuario vinculada (para testes)
INSERT INTO Pessoa (Classe_idClasse, Genero_idGenero, Unidade_idUnidade, nome, cpf, rg, data_nascimento, telefone, isDesbravador, idResponsavel, fkCargo) VALUES
    (NULL, 2, 1, 'Pessoa Sem Usuario', '123.456.789-10', '9999999', '1995-07-20', '(11) 99999-9999', FALSE, NULL, 2);

-- Turma
INSERT INTO Turma (Classe_idClasse, Unidade_idUnidade) VALUES
    (1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
    (1, 2), (3, 2), (4, 2), (5, 2),
    (1, 3), (1, 4), (2, 3), (6, 4);

-- Disciplina
INSERT INTO Disciplina (Classe_idClasse, Especialidade_idEspecialidade) VALUES
    (1, 4), (1, 5), (1, 6),
    (2, 1), (2, 5),
    (3, 1), (4, 1), (4, 2), (5, 1), (5, 2), (5, 3),
    (6, 7), (6, 8);

-- Ocorrencia
INSERT INTO Ocorrencia (data, descricao, Pessoa_idPessoa) VALUES
    ('2026-04-10', 'Leve torção no tornozelo durante atividade física.', 1),
    ('2026-03-22', 'Reação alérgica leve após contato com planta.', 2);

-- Presenca
INSERT INTO Presenca (Chamada_idChamada, Pessoa_idPessoa, presenca) VALUES
    (1, 1, TRUE), (1, 2, TRUE), (1, 3, FALSE), (1, 4, TRUE), (1, 5, TRUE);

-- Ficha_medica
INSERT INTO Ficha_medica (Pessoa_idPessoa) VALUES (1), (2), (3), (4);

-- Diagnostico (sem documento por ora)
INSERT INTO Diagnostico (Ficha_medica_idFichaMedica, Comorbidade_idComorbidade, Documento_idDocumento) VALUES
    (1, 3, NULL),
    (2, 4, NULL),
    (3, 1, NULL),
    (4, 2, NULL);

-- Medicacao (sem documento por ora)
INSERT INTO Medicacao (Ficha_medica_idFichaMedica, Medicamento_idMedicamento, Documento_idDocumento, horario_inicio, horario_fim, dose) VALUES
    (1, 5, NULL, '2026-01-01 08:00:00', NULL,                  10.00),
    (2, 1, NULL, '2026-01-01 08:00:00', '2026-01-07 20:00:00',  500.00),
    (3, 2, NULL, '2026-01-01 12:00:00', NULL,                  400.00),
    (4, 4, NULL, '2026-01-01 08:00:00', '2026-01-05 20:00:00', 500.00);
