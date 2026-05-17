-- Genero
INSERT IGNORE INTO Genero (idGenero, nome) VALUES (1, 'Masculino'), (2, 'Feminino');

-- Classe
INSERT IGNORE INTO Classe (idClasse, nome) VALUES
    (1, 'Amigo'), (2, 'Companheiro'), (3, 'Pesquisador'), (4, 'Pioneiro'), (5, 'Guia');

-- Cargo
INSERT IGNORE INTO Cargo (idCargo, Nome) VALUES
    (1, 'Diretor'), (2, 'Secretário'), (3, 'Tesoureiro'), (4, 'Desbravador');

-- Unidade
INSERT IGNORE INTO Unidade (idUnidade, nome, Genero_idGenero) VALUES
    (1, 'Unidade Tamoios', 1),
    (2, 'Unidade Arandu', 2);

-- Medicamento
INSERT IGNORE INTO Medicamento (idMedicamento, Nome) VALUES
    (1, 'Paracetamol'), (2, 'Ibuprofeno'), (3, 'Amoxicilina'), (4, 'Dipirona'), (5, 'Loratadina');

-- Comorbidade
INSERT IGNORE INTO Comorbidade (idComorbidade, Nome) VALUES
    (1, 'Diabetes Tipo 1'), (2, 'Hipertensão'), (3, 'Asma'), (4, 'Rinite Alérgica'), (5, 'Epilepsia');

-- Pessoa
INSERT IGNORE INTO Pessoa (idPessoa, Classe_idClasse, Genero_idGenero, Unidade_idUnidade, nome, cpf, rg, data_nascimento, telefone, isDesbravador, idResponsavel, fkCargo) VALUES
    (1, 4, 1, 1, 'Carlos Silva',     '111.111.111-11', '1111111', '2012-03-15', '(11) 91111-1111', TRUE,  NULL, 4),
    (2, 3, 2, 1, 'Ana Souza',        '222.222.222-22', '2222222', '2013-06-22', '(11) 92222-2222', TRUE,  NULL, 4),
    (3, 5, 1, 2, 'Pedro Oliveira',   '333.333.333-33', '3333333', '2011-09-10', '(11) 93333-3333', TRUE,  NULL, 4),
    (4, 2, 2, 2, 'Maria Santos',     '444.444.444-44', '4444444', '2014-01-05', '(11) 94444-4444', TRUE,  NULL, 4),
    (5, 1, 1, 1, 'João Lima',        '555.555.555-55', '5555555', '2015-11-30', '(11) 95555-5555', TRUE,  NULL, 4),
    (6, NULL, 1, 1, 'Roberto Costa', '666.666.666-66', '6666666', '1985-04-18', '(11) 96666-6666', FALSE, NULL, 1),
    (7, NULL, 2, 1, 'Fernanda Alves','777.777.777-77', '7777777', '1990-08-25', '(11) 97777-7777', FALSE, NULL, 2),
    (8, NULL, 1, 2, 'Marcos Pereira','888.888.888-88', '8888888', '1988-12-03', '(11) 98888-8888', FALSE, NULL, 3);

-- Usuario
INSERT IGNORE INTO Usuario (idUsuario, Pessoa_idPessoa, fkCargo, email, senha, ativo) VALUES
    (1, 6, 1, 'roberto.costa@clube.com',  'senha123', TRUE),
    (2, 7, 2, 'fernanda.alves@clube.com', 'senha123', TRUE),
    (3, 8, 3, 'marcos.pereira@clube.com', 'senha123', TRUE);

-- Ficha_medica
INSERT IGNORE INTO Ficha_medica (idFichaMedica, Pessoa_idPessoa) VALUES
    (1, 1), (2, 2), (3, 3), (4, 4);

-- Diagnostico
INSERT IGNORE INTO Diagnostico (idDiagnostico, Ficha_medica_idFichaMedica, Comorbidade_idComorbidade, Documento_idDocumento) VALUES
    (1, 1, 3, NULL),
    (2, 2, 4, NULL),
    (3, 3, 1, NULL),
    (4, 4, 2, NULL);

-- Medicacao
INSERT IGNORE INTO Medicacao (idMedicacao, Ficha_medica_idFichaMedica, Medicamento_idMedicamento, Documento_idDocumento, horario_inicio, horario_fim, dose) VALUES
    (1, 1, 5, NULL, '2026-01-01 08:00:00', NULL,                  10.00),
    (2, 2, 1, NULL, '2026-01-01 08:00:00', '2026-01-07 20:00:00', 500.00),
    (3, 3, 2, NULL, '2026-01-01 12:00:00', NULL,                  400.00),
    (4, 4, 4, NULL, '2026-01-01 08:00:00', '2026-01-05 20:00:00', 500.00);
