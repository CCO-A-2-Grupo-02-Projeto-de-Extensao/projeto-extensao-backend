-- Genero
INSERT IGNORE INTO Genero (idGenero, nome) VALUES (1, 'Masculino'), (2, 'Feminino');

-- Classe
INSERT IGNORE INTO Classe (idClasse, nome) VALUES
    (1, 'Amigo'), (2, 'Companheiro'), (3, 'Pesquisador'), (4, 'Pioneiro'), (5, 'Guia'), (6, 'Excursionista');

-- Cargo
INSERT IGNORE INTO Cargo (idCargo, Nome) VALUES
    (1, 'Diretor'), (2, 'Secretário'), (3, 'Tesoureiro'), (4, 'Desbravador'), (5, 'Instrutor');

-- Unidade
INSERT IGNORE INTO Unidade (idUnidade, nome, Genero_idGenero, faixa_etaria) VALUES
    (1, 'Falcões', 1, '10 - 11'),
    (2, 'Tigres', 1, '12 - 13'),
    (3, 'Panteras', 1, '14 - 15'),
    (4, 'Lobos', 1, '12 - 13'),
    (5, 'Andorinhas', 2, '10 - 11'),
    (6, 'Corujas', 2, '12 - 13'),
    (7, 'Águias', 2, '14 - 15'),
    (8, 'Gaivotas', 2, '10 - 11');

-- Medicamento
INSERT IGNORE INTO Medicamento (idMedicamento, Nome) VALUES
    (1, 'Paracetamol'), (2, 'Ibuprofeno'), (3, 'Amoxicilina'), (4, 'Dipirona'), (5, 'Loratadina');

-- Comorbidade
INSERT IGNORE INTO Comorbidade (idComorbidade, Nome) VALUES
    (1, 'Diabetes Tipo 1'), (2, 'Hipertensão'), (3, 'Asma'), (4, 'Rinite Alérgica'), (5, 'Epilepsia');

-- Pessoa
INSERT IGNORE INTO Pessoa (idPessoa, Classe_idClasse, Genero_idGenero, Unidade_idUnidade, nome, cpf, rg, data_nascimento, telefone, isDesbravador, idResponsavel, fkCargo, escola, serie_escolar, nome_responsavel1, telefone_responsavel1) VALUES
    (1, 3, 1, 2, 'Carlos Silva', '100.007.919-89', '10.000.357-1', DATE_FORMAT(CURDATE() - INTERVAL 146 MONTH, '%Y-%m-%d'), '(11) 90137-1913', TRUE, NULL, 4, 'EMEF Jardim Paulista', '7º ano', 'Vera Silva', '(11) 98637-8413'),
    (2, 4, 2, 6, 'Ana Souza', '100.015.838-16', '10.000.714-2', DATE_FORMAT(CURDATE() - INTERVAL 159 MONTH, '%Y-%m-%d'), '(11) 90274-2826', TRUE, NULL, 4, 'Colégio São Vicente', '8º ano', 'Adriano Souza', '(11) 98774-9326'),
    (3, 5, 1, 3, 'Pedro Oliveira', '100.023.757-53', '10.001.071-3', DATE_FORMAT(CURDATE() - INTERVAL 184 MONTH, '%Y-%m-%d'), '(11) 90411-3739', TRUE, NULL, 4, 'EE Dom Pedro II', '1ª série EM', 'Rosana Oliveira', '(11) 98911-0239'),
    (4, 2, 2, 5, 'Maria Santos', '100.031.676-90', '10.001.428-4', DATE_FORMAT(CURDATE() - INTERVAL 137 MONTH, '%Y-%m-%d'), '(11) 90548-4652', TRUE, NULL, 4, 'EMEF Vila Andrade', '6º ano', 'Everton Santos', '(11) 99048-1152'),
    (5, 1, 1, 1, 'João Lima', '100.039.595-20', '10.001.785-5', DATE_FORMAT(CURDATE() - INTERVAL 126 MONTH, '%Y-%m-%d'), '(11) 90685-5565', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '5º ano', 'Eliane Lima', '(11) 99185-2065'),
    (6, NULL, 1, NULL, 'Roberto Costa', '100.047.514-00', '10.002.142-6', DATE_FORMAT(CURDATE() - INTERVAL 41 YEAR, '%Y-%m-%d'), '(11) 90822-6478', FALSE, NULL, 1, NULL, NULL, NULL, NULL),
    (7, NULL, 2, NULL, 'Fernanda Alves', '100.055.433-39', '10.002.499-7', DATE_FORMAT(CURDATE() - INTERVAL 36 YEAR, '%Y-%m-%d'), '(11) 90959-7391', FALSE, NULL, 2, NULL, NULL, NULL, NULL),
    (8, NULL, 1, NULL, 'Marcos Pereira', '100.063.352-76', '10.002.856-8', DATE_FORMAT(CURDATE() - INTERVAL 38 YEAR, '%Y-%m-%d'), '(11) 91096-8304', FALSE, NULL, 3, NULL, NULL, NULL, NULL),
    (9, 2, 1, 1, 'Lucas Andrade', '100.071.271-03', '10.003.213-9', DATE_FORMAT(CURDATE() - INTERVAL 142 MONTH, '%Y-%m-%d'), '(11) 91233-9217', TRUE, NULL, 4, 'EE Dom Pedro II', '6º ano', 'Otacílio Andrade', '(11) 99733-5717'),
    (10, 1, 1, 1, 'Gabriel Rocha', '100.079.190-42', '10.003.571-0', DATE_FORMAT(CURDATE() - INTERVAL 131 MONTH, '%Y-%m-%d'), '(11) 91370-0130', TRUE, NULL, 4, 'EMEF Vila Andrade', '5º ano', 'Sérgio Rocha', '(11) 99870-6630'),
    (11, 2, 1, 1, 'Matheus Nogueira', '100.087.109-61', '10.003.928-1', DATE_FORMAT(CURDATE() - INTERVAL 133 MONTH, '%Y-%m-%d'), '(11) 91507-1043', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '6º ano', 'Cláudio Nogueira', '(11) 90007-7543'),
    (12, 1, 1, 1, 'Rafael Teixeira', '100.095.028-07', '10.004.285-2', DATE_FORMAT(CURDATE() - INTERVAL 122 MONTH, '%Y-%m-%d'), '(11) 91644-1956', TRUE, NULL, 4, 'EE Prof. Onofre Ribeiro', '5º ano', 'Adriano Teixeira', '(11) 90144-8456'),
    (13, 2, 1, 1, 'Enzo Cardoso', '100.102.947-08', '10.004.642-3', DATE_FORMAT(CURDATE() - INTERVAL 135 MONTH, '%Y-%m-%d'), '(11) 91781-2869', TRUE, NULL, 4, 'EMEF Jardim Paulista', '6º ano', 'Wagner Cardoso', '(11) 90281-9369'),
    (14, 4, 1, 2, 'Davi Moreira', '100.110.866-37', '10.004.999-4', DATE_FORMAT(CURDATE() - INTERVAL 160 MONTH, '%Y-%m-%d'), '(11) 91918-3782', TRUE, NULL, 4, 'Colégio São Vicente', '8º ano', 'Márcia Moreira', '(11) 90418-0282'),
    (15, 3, 1, 2, 'Bernardo Pinto', '100.118.785-76', '10.005.356-5', DATE_FORMAT(CURDATE() - INTERVAL 149 MONTH, '%Y-%m-%d'), '(11) 92055-4695', TRUE, NULL, 4, 'EE Dom Pedro II', '7º ano', 'Eliane Pinto', '(11) 90555-1195'),
    (16, 4, 1, 2, 'Théo Barbosa', '100.126.704-48', '10.005.713-6', DATE_FORMAT(CURDATE() - INTERVAL 162 MONTH, '%Y-%m-%d'), '(11) 92192-5608', TRUE, NULL, 4, 'EMEF Vila Andrade', '8º ano', 'Solange Barbosa', '(11) 90692-2108'),
    (17, 3, 1, 2, 'Heitor Ramalho', '100.134.623-85', '10.006.070-7', DATE_FORMAT(CURDATE() - INTERVAL 151 MONTH, '%Y-%m-%d'), '(11) 92329-6521', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '7º ano', 'Denise Ramalho', '(11) 90829-3021'),
    (18, 4, 1, 2, 'Arthur Vasconcelos', '100.142.542-12', '10.006.427-8', DATE_FORMAT(CURDATE() - INTERVAL 164 MONTH, '%Y-%m-%d'), '(11) 92466-7434', TRUE, NULL, 4, 'EE Prof. Onofre Ribeiro', '8º ano', 'Tânia Vasconcelos', '(11) 90966-3934'),
    (19, 3, 1, 2, 'Miguel Fontes', '100.150.461-50', '10.006.784-9', DATE_FORMAT(CURDATE() - INTERVAL 153 MONTH, '%Y-%m-%d'), '(11) 92603-8347', TRUE, NULL, 4, 'EMEF Jardim Paulista', '7º ano', 'Neusa Fontes', '(11) 91103-4847'),
    (20, 5, 1, 3, 'Samuel Queiroz', '100.158.380-99', '10.007.142-0', DATE_FORMAT(CURDATE() - INTERVAL 190 MONTH, '%Y-%m-%d'), '(11) 92740-9260', TRUE, NULL, 4, 'Colégio São Vicente', '1ª série EM', 'Sandra Queiroz', '(11) 91240-5760'),
    (21, 6, 1, 3, 'Benício Duarte', '100.166.299-73', '10.007.499-1', DATE_FORMAT(CURDATE() - INTERVAL 179 MONTH, '%Y-%m-%d'), '(11) 92877-0173', TRUE, NULL, 4, 'EE Dom Pedro II', '9º ano', 'Vera Duarte', '(11) 91377-6673'),
    (22, 5, 1, 3, 'Nicolas Sampaio', '100.174.218-45', '10.007.856-2', DATE_FORMAT(CURDATE() - INTERVAL 181 MONTH, '%Y-%m-%d'), '(11) 93014-1086', TRUE, NULL, 4, 'EMEF Vila Andrade', '1ª série EM', 'Lúcia Sampaio', '(11) 91514-7586'),
    (23, 6, 1, 3, 'Vicente Aragão', '100.182.137-82', '10.008.213-3', DATE_FORMAT(CURDATE() - INTERVAL 170 MONTH, '%Y-%m-%d'), '(11) 93151-1999', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '9º ano', 'Rosana Aragão', '(11) 91651-8499'),
    (24, 5, 1, 3, 'Otávio Bastos', '100.190.056-10', '10.008.570-4', DATE_FORMAT(CURDATE() - INTERVAL 183 MONTH, '%Y-%m-%d'), '(11) 93288-2912', TRUE, NULL, 4, 'EE Prof. Onofre Ribeiro', '1ª série EM', 'Márcia Bastos', '(11) 91788-9412'),
    (25, 3, 1, 4, 'Caio Meireles', '100.197.975-32', '10.008.927-5', DATE_FORMAT(CURDATE() - INTERVAL 148 MONTH, '%Y-%m-%d'), '(11) 93425-3825', TRUE, NULL, 4, 'EMEF Jardim Paulista', '7º ano', 'Eliane Meireles', '(11) 91925-0325'),
    (26, 4, 1, 4, 'Felipe Antunes', '100.205.894-50', '10.009.284-6', DATE_FORMAT(CURDATE() - INTERVAL 161 MONTH, '%Y-%m-%d'), '(11) 93562-4738', TRUE, NULL, 4, 'Colégio São Vicente', '8º ano', 'Solange Antunes', '(11) 92062-1238'),
    (27, 3, 1, 4, 'Leonardo Guimarães', '100.213.813-21', '10.009.641-7', DATE_FORMAT(CURDATE() - INTERVAL 150 MONTH, '%Y-%m-%d'), '(11) 93699-5651', TRUE, NULL, 4, 'EE Dom Pedro II', '7º ano', 'Denise Guimarães', '(11) 92199-2151'),
    (28, 4, 1, 4, 'Murilo Peixoto', '100.221.732-69', '10.009.998-8', DATE_FORMAT(CURDATE() - INTERVAL 163 MONTH, '%Y-%m-%d'), '(11) 93836-6564', TRUE, NULL, 4, 'EMEF Vila Andrade', '8º ano', 'Tânia Peixoto', '(11) 92336-3064'),
    (29, 3, 1, 4, 'Isaac Cavalcanti', '100.229.651-06', '10.010.355-9', DATE_FORMAT(CURDATE() - INTERVAL 152 MONTH, '%Y-%m-%d'), '(11) 93973-7477', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '7º ano', 'Neusa Cavalcanti', '(11) 92473-3977'),
    (30, 4, 1, 4, 'Antônio Rezende', '100.237.570-35', '10.010.713-0', DATE_FORMAT(CURDATE() - INTERVAL 165 MONTH, '%Y-%m-%d'), '(11) 94110-8390', TRUE, NULL, 4, 'EE Prof. Onofre Ribeiro', '8º ano', 'Sandra Rezende', '(11) 92610-4890'),
    (31, 2, 2, 5, 'Helena Braga', '100.245.489-10', '10.011.070-1', DATE_FORMAT(CURDATE() - INTERVAL 142 MONTH, '%Y-%m-%d'), '(11) 94247-9303', TRUE, NULL, 4, 'EMEF Jardim Paulista', '6º ano', 'Cláudio Braga', '(11) 92747-5803'),
    (32, 1, 2, 5, 'Alice Monteiro', '100.253.408-91', '10.011.427-2', DATE_FORMAT(CURDATE() - INTERVAL 131 MONTH, '%Y-%m-%d'), '(11) 94384-0216', TRUE, NULL, 4, 'Colégio São Vicente', '5º ano', 'Adriano Monteiro', '(11) 92884-6716'),
    (33, 2, 2, 5, 'Laura Bittencourt', '100.261.327-29', '10.011.784-3', DATE_FORMAT(CURDATE() - INTERVAL 133 MONTH, '%Y-%m-%d'), '(11) 94521-1129', TRUE, NULL, 4, 'EE Dom Pedro II', '6º ano', 'Wagner Bittencourt', '(11) 93021-7629'),
    (34, 1, 2, 5, 'Manuela Freitas', '100.269.246-68', '10.012.141-4', DATE_FORMAT(CURDATE() - INTERVAL 122 MONTH, '%Y-%m-%d'), '(11) 94658-2042', TRUE, NULL, 4, 'EMEF Vila Andrade', '5º ano', 'Everton Freitas', '(11) 93158-8542'),
    (35, 2, 2, 5, 'Sophia Marinho', '100.277.165-03', '10.012.498-5', DATE_FORMAT(CURDATE() - INTERVAL 135 MONTH, '%Y-%m-%d'), '(11) 94795-2955', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '6º ano', 'Gilberto Marinho', '(11) 93295-9455'),
    (36, 4, 2, 6, 'Isabela Cordeiro', '100.285.084-32', '10.012.855-6', DATE_FORMAT(CURDATE() - INTERVAL 160 MONTH, '%Y-%m-%d'), '(11) 94932-3868', TRUE, NULL, 4, 'EE Prof. Onofre Ribeiro', '8º ano', 'Solange Cordeiro', '(11) 93432-0368'),
    (37, 3, 2, 6, 'Beatriz Vilela', '100.293.003-04', '10.013.212-7', DATE_FORMAT(CURDATE() - INTERVAL 149 MONTH, '%Y-%m-%d'), '(11) 95069-4781', TRUE, NULL, 4, 'EMEF Jardim Paulista', '7º ano', 'Denise Vilela', '(11) 93569-1281'),
    (38, 4, 2, 6, 'Lívia Sarmento', '100.300.922-05', '10.013.569-8', DATE_FORMAT(CURDATE() - INTERVAL 162 MONTH, '%Y-%m-%d'), '(11) 95206-5694', TRUE, NULL, 4, 'Colégio São Vicente', '8º ano', 'Tânia Sarmento', '(11) 93706-2194'),
    (39, 3, 2, 6, 'Cecília Pontes', '100.308.841-44', '10.013.926-9', DATE_FORMAT(CURDATE() - INTERVAL 151 MONTH, '%Y-%m-%d'), '(11) 95343-6607', TRUE, NULL, 4, 'EE Dom Pedro II', '7º ano', 'Neusa Pontes', '(11) 93843-3107'),
    (40, 4, 2, 6, 'Elisa Nunes', '100.316.760-81', '10.014.284-0', DATE_FORMAT(CURDATE() - INTERVAL 164 MONTH, '%Y-%m-%d'), '(11) 95480-7520', TRUE, NULL, 4, 'EMEF Vila Andrade', '8º ano', 'Sandra Nunes', '(11) 93980-4020'),
    (41, 3, 2, 6, 'Valentina Rios', '100.324.679-66', '10.014.641-1', DATE_FORMAT(CURDATE() - INTERVAL 153 MONTH, '%Y-%m-%d'), '(11) 95617-8433', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '7º ano', 'Vera Rios', '(11) 94117-4933'),
    (42, 6, 2, 7, 'Antonella Prado', '100.332.598-01', '10.014.998-2', DATE_FORMAT(CURDATE() - INTERVAL 178 MONTH, '%Y-%m-%d'), '(11) 95754-9346', TRUE, NULL, 4, 'EE Prof. Onofre Ribeiro', '9º ano', 'Adriano Prado', '(11) 94254-5846'),
    (43, 5, 2, 7, 'Mariana Caldeira', '100.340.517-75', '10.015.355-3', DATE_FORMAT(CURDATE() - INTERVAL 191 MONTH, '%Y-%m-%d'), '(11) 95891-0259', TRUE, NULL, 4, 'EMEF Jardim Paulista', '1ª série EM', 'Wagner Caldeira', '(11) 94391-6759'),
    (44, 6, 2, 7, 'Rebeca Lacerda', '100.348.436-04', '10.015.712-4', DATE_FORMAT(CURDATE() - INTERVAL 169 MONTH, '%Y-%m-%d'), '(11) 96028-1172', TRUE, NULL, 4, 'Colégio São Vicente', '9º ano', 'Everton Lacerda', '(11) 94528-7672'),
    (45, 5, 2, 7, 'Yasmin Furtado', '100.356.355-41', '10.016.069-5', DATE_FORMAT(CURDATE() - INTERVAL 182 MONTH, '%Y-%m-%d'), '(11) 96165-2085', TRUE, NULL, 4, 'EE Dom Pedro II', '1ª série EM', 'Gilberto Furtado', '(11) 94665-8585'),
    (46, 6, 2, 7, 'Clara Bandeira', '100.364.274-89', '10.016.426-6', DATE_FORMAT(CURDATE() - INTERVAL 171 MONTH, '%Y-%m-%d'), '(11) 96302-2998', TRUE, NULL, 4, 'EMEF Vila Andrade', '9º ano', 'Ronaldo Bandeira', '(11) 94802-9498'),
    (47, 5, 2, 7, 'Júlia Amorim', '100.372.193-16', '10.016.783-7', DATE_FORMAT(CURDATE() - INTERVAL 184 MONTH, '%Y-%m-%d'), '(11) 96439-3911', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '1ª série EM', 'Fábio Amorim', '(11) 94939-0411'),
    (48, 1, 2, 8, 'Lorena Tavares', '100.380.112-98', '10.017.140-8', DATE_FORMAT(CURDATE() - INTERVAL 125 MONTH, '%Y-%m-%d'), '(11) 96576-4824', TRUE, NULL, 4, 'EE Prof. Onofre Ribeiro', '5º ano', 'Nelson Tavares', '(11) 95076-1324'),
    (49, 2, 2, 8, 'Eloá Bezerra', '100.388.031-27', '10.017.497-9', DATE_FORMAT(CURDATE() - INTERVAL 138 MONTH, '%Y-%m-%d'), '(11) 96713-5737', TRUE, NULL, 4, 'EMEF Jardim Paulista', '6º ano', 'Otacílio Bezerra', '(11) 95213-2237'),
    (50, 1, 2, 8, 'Melissa Drummond', '100.395.950-48', '10.017.855-0', DATE_FORMAT(CURDATE() - INTERVAL 127 MONTH, '%Y-%m-%d'), '(11) 96850-6650', TRUE, NULL, 4, 'Colégio São Vicente', '5º ano', 'Sérgio Drummond', '(11) 95350-3150'),
    (51, 2, 2, 8, 'Catarina Vidal', '100.403.869-02', '10.018.212-1', DATE_FORMAT(CURDATE() - INTERVAL 140 MONTH, '%Y-%m-%d'), '(11) 96987-7563', TRUE, NULL, 4, 'EE Dom Pedro II', '6º ano', 'Cláudio Vidal', '(11) 95487-4063'),
    (52, 1, 2, 8, 'Olívia Serpa', '100.411.788-40', '10.018.569-2', DATE_FORMAT(CURDATE() - INTERVAL 129 MONTH, '%Y-%m-%d'), '(11) 97124-8476', TRUE, NULL, 4, 'EMEF Vila Andrade', '5º ano', 'Adriano Serpa', '(11) 95624-4976'),
    (53, 2, 2, 8, 'Emanuelly Paiva', '100.419.707-13', '10.018.926-3', DATE_FORMAT(CURDATE() - INTERVAL 142 MONTH, '%Y-%m-%d'), '(11) 97261-9389', TRUE, NULL, 4, 'Colégio Adventista de Interlagos', '6º ano', 'Wagner Paiva', '(11) 95761-5889'),
    (54, 1, 1, 1, 'Paulo Sérgio Matos', '100.427.626-50', '10.019.283-4', DATE_FORMAT(CURDATE() - INTERVAL 33 YEAR, '%Y-%m-%d'), '(11) 97398-0302', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (55, 3, 1, 2, 'André Luiz Cunha', '100.435.545-98', '10.019.640-5', DATE_FORMAT(CURDATE() - INTERVAL 34 YEAR, '%Y-%m-%d'), '(11) 97535-1215', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (56, 6, 1, 3, 'Tiago Ferrarini', '100.443.464-25', '10.019.997-6', DATE_FORMAT(CURDATE() - INTERVAL 35 YEAR, '%Y-%m-%d'), '(11) 97672-2128', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (57, 4, 1, 4, 'Rogério Sampaio', '100.451.383-62', '10.020.354-7', DATE_FORMAT(CURDATE() - INTERVAL 36 YEAR, '%Y-%m-%d'), '(11) 97809-3041', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (58, 2, 2, 5, 'Patrícia Menezes', '100.459.302-36', '10.020.711-8', DATE_FORMAT(CURDATE() - INTERVAL 37 YEAR, '%Y-%m-%d'), '(11) 97946-3954', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (59, 3, 2, 6, 'Simone Vasques', '100.467.221-73', '10.021.068-9', DATE_FORMAT(CURDATE() - INTERVAL 38 YEAR, '%Y-%m-%d'), '(11) 98083-4867', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (60, 5, 2, 7, 'Carla Estefani', '100.475.140-00', '10.021.426-0', DATE_FORMAT(CURDATE() - INTERVAL 24 YEAR, '%Y-%m-%d'), '(11) 98220-5780', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (61, 1, 2, 8, 'Juliana Boaventura', '100.483.059-95', '10.021.783-1', DATE_FORMAT(CURDATE() - INTERVAL 25 YEAR, '%Y-%m-%d'), '(11) 98357-6693', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (62, 2, 1, NULL, 'Marcelo Tostes', '100.490.978-06', '10.022.140-2', DATE_FORMAT(CURDATE() - INTERVAL 26 YEAR, '%Y-%m-%d'), '(11) 98494-7606', FALSE, NULL, 5, NULL, NULL, NULL, NULL),
    (63, 4, 2, NULL, 'Renata Vilhena', '100.498.897-45', '10.022.497-3', DATE_FORMAT(CURDATE() - INTERVAL 27 YEAR, '%Y-%m-%d'), '(11) 98631-8519', FALSE, NULL, 5, NULL, NULL, NULL, NULL);

-- Conselheiro de cada unidade
UPDATE Unidade SET Pessoa_idConselheiro = 54 WHERE idUnidade = 1 AND Pessoa_idConselheiro IS NULL;
UPDATE Unidade SET Pessoa_idConselheiro = 55 WHERE idUnidade = 2 AND Pessoa_idConselheiro IS NULL;
UPDATE Unidade SET Pessoa_idConselheiro = 56 WHERE idUnidade = 3 AND Pessoa_idConselheiro IS NULL;
UPDATE Unidade SET Pessoa_idConselheiro = 57 WHERE idUnidade = 4 AND Pessoa_idConselheiro IS NULL;
UPDATE Unidade SET Pessoa_idConselheiro = 58 WHERE idUnidade = 5 AND Pessoa_idConselheiro IS NULL;
UPDATE Unidade SET Pessoa_idConselheiro = 59 WHERE idUnidade = 6 AND Pessoa_idConselheiro IS NULL;
UPDATE Unidade SET Pessoa_idConselheiro = 60 WHERE idUnidade = 7 AND Pessoa_idConselheiro IS NULL;
UPDATE Unidade SET Pessoa_idConselheiro = 61 WHERE idUnidade = 8 AND Pessoa_idConselheiro IS NULL;

-- turma
INSERT IGNORE INTO turma (idTurma, Classe_idClasse, Unidade_idUnidade) VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 2),
    (4, 4, 2),
    (5, 6, 3),
    (6, 5, 3),
    (7, 3, 4),
    (8, 4, 4),
    (9, 1, 5),
    (10, 2, 5),
    (11, 3, 6),
    (12, 4, 6),
    (13, 6, 7),
    (14, 5, 7),
    (15, 1, 8),
    (16, 2, 8);

-- Usuario
INSERT IGNORE INTO Usuario (idUsuario, Pessoa_idPessoa, fkCargo, email, senha, ativo) VALUES
    (1, 6, 1, 'roberto.costa@clube.com',  '$2a$10$DBFC490wr.eMeDvHOSmB7O0fOroyVLsy4qxmcThxcYKpfUfLhtlbO', TRUE),
    (2, 7, 2, 'fernanda.alves@clube.com', '$2a$10$DBFC490wr.eMeDvHOSmB7O0fOroyVLsy4qxmcThxcYKpfUfLhtlbO', TRUE),
    (3, 8, 3, 'marcos.pereira@clube.com', '$2a$10$DBFC490wr.eMeDvHOSmB7O0fOroyVLsy4qxmcThxcYKpfUfLhtlbO', TRUE);

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

-- Especialidade
INSERT IGNORE INTO Especialidade (idEspecialidade, nome, categoria, descricao, imagem) VALUES
    (1, 'Cerâmica', 'Artes e Habilidades Manuais (HM)', 'Modelagem e queima de peças em argila, das técnicas de preparo da massa ao acabamento.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Ceramics_AY_Honor.png?width=120'),
    (2, 'Crochê', 'Artes e Habilidades Manuais (HM)', 'Pontos básicos do crochê e confecção de peças simples com agulha e linha.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Crocheting_AY_Honor.png?width=120'),
    (3, 'Decoração de Bolos', 'Artes e Habilidades Manuais (HM)', 'Técnicas de cobertura, bicos e acabamento na decoração de bolos.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cake_Decorating_AY_Honor.png?width=120'),
    (4, 'Confecção de Velas', 'Artes e Habilidades Manuais (HM)', 'Preparo de parafina, pavios e moldes para a produção artesanal de velas.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Candle_Making_AY_Honor.png?width=120'),
    (5, 'Esmaltado em Cobre', 'Artes e Habilidades Manuais (HM)', 'Aplicação de esmalte sobre peças de cobre com uso de forno e pigmentos.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Copper_Enameling_AY_Honor.png?width=120'),
    (6, 'Ponto Cruz Contado', 'Artes e Habilidades Manuais (HM)', 'Leitura de gráficos e execução de bordado em ponto cruz sobre tecido de trama contada.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Counted_Cross_Stitch_AY_Honor.png?width=120'),
    (7, 'Fuxico', 'Artes e Habilidades Manuais (HM)', 'Confecção de rosetas de tecido e sua aplicação em peças decorativas.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Fuxico_AY_Honor.png?width=120'),
    (8, 'Agricultura', 'Atividades Agrícolas (AA)', 'Preparo do solo, plantio, tratos culturais e colheita de culturas comuns.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Agriculture_AY_Honor.png?width=120'),
    (9, 'Apicultura', 'Atividades Agrícolas (AA)', 'Manejo de colmeias, comportamento das abelhas e extração segura do mel.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Beekeeping_AY_Honor.png?width=120'),
    (10, 'Criação de Gado', 'Atividades Agrícolas (AA)', 'Manejo, alimentação e cuidados sanitários na criação de bovinos.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cattle_Husbandry_AY_Honor.png?width=120'),
    (11, 'Cactos', 'Atividades Agrícolas (AA)', 'Identificação, cultivo e propagação de cactos e outras suculentas.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cacti_AY_Honor.png?width=120'),
    (12, 'Arte de Contar Histórias Cristãs', 'Atividades Missionárias e Comunitárias (AM)', 'Preparo e apresentação de histórias bíblicas para diferentes faixas etárias.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Christian_Storytelling_AY_Honor.png?width=120'),
    (13, 'Cidadania Cristã', 'Atividades Missionárias e Comunitárias (AM)', 'Deveres do cidadão e o papel do cristão na vida pública e comunitária.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Christian_Citizenship_AY_Honor.png?width=120'),
    (14, 'Adoração Cristã', 'Atividades Missionárias e Comunitárias (AM)', 'Formas de culto e participação do desbravador na adoração em grupo.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Christian_Worship_AY_Honor.png?width=120'),
    (15, 'Compaixão', 'Atividades Missionárias e Comunitárias (AM)', 'Prática do cuidado com o próximo por meio de ações concretas de serviço.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Compassion_AY_Honor.png?width=120'),
    (16, 'Melhoramento da Comunidade', 'Atividades Missionárias e Comunitárias (AM)', 'Diagnóstico de necessidades locais e execução de um projeto de melhoria.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Community_Improvement_AY_Honor.png?width=120'),
    (17, 'Prevenção ao Crime', 'Atividades Missionárias e Comunitárias (AM)', 'Noções de segurança pessoal, prevenção e apoio à comunidade.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Crime_Prevention_AY_Honor.png?width=120'),
    (18, 'Carpintaria', 'Atividades Profissionais (AP)', 'Uso de ferramentas manuais, tipos de madeira e execução de peças simples.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Carpentry_AY_Honor.png?width=120'),
    (19, 'Comunicações', 'Atividades Profissionais (AP)', 'Meios de comunicação, transmissão de mensagens e códigos usados no clube.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Communications_AY_Honor.png?width=120'),
    (20, 'Computadores', 'Atividades Profissionais (AP)', 'Componentes, uso responsável e noções de manutenção de computadores.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Computers_AY_Honor.png?width=120'),
    (21, 'Radioamadorismo', 'Atividades Profissionais (AP)', 'Operação de rádio de faixa cidadã, etiqueta de comunicação e alcance.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/CB_Radio_AY_Honor.png?width=120'),
    (22, 'Acampamento', 'Atividades Recreativas (AR)', 'Montagem de barracas, escolha do terreno e organização da vida no campo.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Camping_AY_Honor.png?width=120'),
    (23, 'Arte de Acampar', 'Atividades Recreativas (AR)', 'Técnicas de campo, uso de cordas, fogueiras e equipamentos de acampamento.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Camp_Craft_AY_Honor.png?width=120'),
    (24, 'Segurança no Acampamento', 'Atividades Recreativas (AR)', 'Prevenção de acidentes, cuidados com fogo e procedimentos de emergência no campo.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Camp_Safety_AY_Honor.png?width=120'),
    (25, 'Canoagem', 'Atividades Recreativas (AR)', 'Manejo da canoa, remadas básicas e segurança na água.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Canoeing_AY_Honor.png?width=120'),
    (26, 'Ciclismo', 'Atividades Recreativas (AR)', 'Manutenção da bicicleta, regras de trânsito e condução segura.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cycling_AY_Honor.png?width=120'),
    (27, 'Espeleologia', 'Atividades Recreativas (AR)', 'Formação de cavernas, equipamentos e segurança na exploração subterrânea.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Caving_AY_Honor.png?width=120'),
    (28, 'Xadrez', 'Atividades Recreativas (AR)', 'Movimentos das peças, aberturas básicas e conduta esportiva no jogo.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Chess_AY_Honor.png?width=120'),
    (29, 'Química', 'Ciência e Saúde (CS)', 'Estados da matéria, reações simples e segurança na manipulação de substâncias.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Chemistry_AY_Honor.png?width=120'),
    (30, 'Reanimação Cardiopulmonar', 'Ciência e Saúde (CS)', 'Reconhecimento da parada cardiorrespiratória e execução das manobras de RCP.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/CPR_AY_Honor.png?width=120'),
    (31, 'Sistema Cardiopulmonar', 'Ciência e Saúde (CS)', 'Anatomia e funcionamento do coração e dos pulmões e hábitos que os preservam.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cardiopulmonary_System_AY_Honor.png?width=120'),
    (32, 'Consciência sobre o Câncer', 'Ciência e Saúde (CS)', 'Fatores de risco, prevenção e importância do diagnóstico precoce.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cancer_Awareness_AY_Honor.png?width=120'),
    (33, 'Citologia', 'Ciência e Saúde (CS)', 'Estrutura da célula, suas organelas e o uso do microscópio.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cytology_AY_Honor.png?width=120'),
    (34, 'Recifes de Coral', 'Estudo da Natureza (EN)', 'Formação dos recifes, espécies associadas e ameaças à sua conservação.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Coral_Reefs_AY_Honor.png?width=120'),
    (35, 'Plantas Carnívoras', 'Estudo da Natureza (EN)', 'Espécies, mecanismos de captura e cultivo de plantas carnívoras.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Carnivorous_Plants_AY_Honor.png?width=120'),
    (36, 'Crustáceos', 'Estudo da Natureza (EN)', 'Características, ciclo de vida e importância ecológica dos crustáceos.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Crustaceans_AY_Honor.png?width=120'),
    (37, 'Cetáceos', 'Estudo da Natureza (EN)', 'Baleias e golfinhos: comportamento, migração e conservação.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cetaceans_AY_Honor.png?width=120'),
    (38, 'Gatos', 'Estudo da Natureza (EN)', 'Raças, comportamento e cuidados básicos com gatos domésticos.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cats_AY_Honor.png?width=120'),
    (39, 'Cigarras', 'Estudo da Natureza (EN)', 'Ciclo de vida, canto e papel das cigarras no ambiente.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cicadas_AY_Honor.png?width=120'),
    (40, 'Mudança Climática', 'Estudo da Natureza (EN)', 'Causas e efeitos da mudança do clima e atitudes de redução de impacto.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Climate_Change_AY_Honor.png?width=120'),
    (41, 'Cristais', 'Estudo da Natureza (EN)', 'Formação, sistemas cristalinos e identificação de cristais comuns.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Crystals_AY_Honor.png?width=120'),
    (42, 'Arte Culinária', 'Habilidades Domésticas (HD)', 'Higiene, medidas, métodos de cocção e preparo de refeições simples.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cooking_AY_Honor.png?width=120'),
    (43, 'Segurança na Cozinha', 'Habilidades Domésticas (HD)', 'Prevenção de queimaduras e cortes e manuseio seguro de alimentos.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cooking_Safety_AY_Honor.png?width=120'),
    (44, 'Preparo de Alimentos Culturais', 'Habilidades Domésticas (HD)', 'Pratos típicos de diferentes culturas e o contexto em que surgiram.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cultural_Food_Preparation_AY_Honor.png?width=120'),
    (45, 'Cuidado de Crianças', 'Habilidades Domésticas (HD)', 'Rotina, segurança e cuidados básicos no atendimento a crianças pequenas.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Child_Care_AY_Honor.png?width=120'),
    (46, 'Herança Cultural', 'Especialidades Regionais (ER)', 'Origens, costumes e manifestações culturais da própria região.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cultural_Heritage_AY_Honor.png?width=120'),
    (47, 'Diversidade Cultural', 'Especialidades Regionais (ER)', 'Convivência e respeito entre povos e culturas diferentes.', 'https://wiki.pathfindersonline.org/w/Special:FilePath/Cultural_Diversity_AY_Honor.png?width=120');

-- Disciplina
INSERT IGNORE INTO Disciplina (Classe_idClasse, Especialidade_idEspecialidade) VALUES
    (1, 22), (1, 42), (1, 38), (1, 12), (1, 26),
    (2, 23), (2, 25), (2, 35), (2, 19), (2, 15),
    (3, 24), (3, 34), (3, 29), (3, 2), (3, 13),
    (4, 30), (4, 36), (4, 18), (4, 43), (4, 16),
    (5, 31), (5, 40), (5, 20), (5, 6), (5, 46),
    (6, 27), (6, 37), (6, 8), (6, 28), (6, 47);

-- Evento
INSERT IGNORE INTO evento (idEvento, nome, tipo, data_inicio, data_fim, descricao) VALUES
    (1, 'Campori 2026', 'Acampamento', '2026-07-10', '2026-07-15', 'Grande acampamento regional'),
    (2, 'Reunião Mensal Maio', 'Reunião', '2026-05-20', NULL, 'Reunião mensal do clube');

-- Chamada
INSERT IGNORE INTO chamada (idChamada, Evento_idEvento, data_chamada, titulo) VALUES
    (1, 2, '2026-05-20', 'Chamada Reunião Maio');

-- Presenca
INSERT IGNORE INTO presenca (idPresenca, Chamada_idChamada, Pessoa_idPessoa, presenca) VALUES
    (1, 1, 1, TRUE),
    (2, 1, 2, TRUE),
    (3, 1, 3, FALSE),
    (4, 1, 4, TRUE),
    (5, 1, 5, TRUE);

-- Ocorrencia
INSERT IGNORE INTO Ocorrencia (idOcorrencia, data, descricao, Pessoa_idPessoa) VALUES
    (1, '2026-04-10', 'Leve torção no tornozelo durante atividade física.', 1),
    (2, '2026-03-22', 'Reação alérgica leve após contato com planta.', 2);
