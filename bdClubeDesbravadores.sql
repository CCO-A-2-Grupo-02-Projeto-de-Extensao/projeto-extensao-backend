CREATE DATABASE bdClubeDesbravadores;
USE bdClubeDesbravadores;

CREATE TABLE IF NOT EXISTS Genero (
    idGenero INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS Classe (
    idClasse INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Especialidade (
    idEspecialidade INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    descricao TEXT
);

CREATE TABLE IF NOT EXISTS Cargo (
    idCargo INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS Evento (
    idEvento INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50),
    data_inicio DATE NOT NULL,
    data_fim DATE,
    descricao TEXT
);

CREATE TABLE IF NOT EXISTS Chamada (
    idChamada INT AUTO_INCREMENT PRIMARY KEY,
    Evento_idEvento INT NOT NULL,
    data_chamada DATE NOT NULL,
    titulo VARCHAR(100), -- "Chamada de Sábado de Manhã", "Chamada Principal" seria para quando o evento fosse durar mais de um dia
    FOREIGN KEY (Evento_idEvento) REFERENCES Evento(idEvento) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Medicamento (
    idMedicamento INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Comorbidade (
    idComorbidade INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Unidade (
    idUnidade INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    Genero_idGenero INT,
    FOREIGN KEY (Genero_idGenero) REFERENCES Genero(idGenero) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Turma (
    Classe_idClasse INT,
    Unidade_idUnidade INT,
    PRIMARY KEY (Classe_idClasse, Unidade_idUnidade),
    FOREIGN KEY (Classe_idClasse) REFERENCES Classe(idClasse) ON DELETE CASCADE,
    FOREIGN KEY (Unidade_idUnidade) REFERENCES Unidade(idUnidade) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Disciplina (
    Classe_idClasse INT,
    Especialidade_idEspecialidade INT,
    PRIMARY KEY (Classe_idClasse, Especialidade_idEspecialidade),
    FOREIGN KEY (Classe_idClasse) REFERENCES Classe(idClasse) ON DELETE CASCADE,
    FOREIGN KEY (Especialidade_idEspecialidade) REFERENCES Especialidade(idEspecialidade) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Pessoa (
    idPessoa INT AUTO_INCREMENT PRIMARY KEY,
    Classe_idClasse INT,
    Genero_idGenero INT,
    Unidade_idUnidade INT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14),
    rg VARCHAR(20),
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(20),
    isDesbravador BOOLEAN DEFAULT FALSE,
    idResponsavel INT,
    fkCargo INT,
    FOREIGN KEY (Classe_idClasse) REFERENCES Classe(idClasse) ON DELETE SET NULL,
    FOREIGN KEY (Genero_idGenero) REFERENCES Genero(idGenero) ON DELETE SET NULL,
    FOREIGN KEY (Unidade_idUnidade) REFERENCES Unidade(idUnidade) ON DELETE SET NULL,
    FOREIGN KEY (idResponsavel) REFERENCES Pessoa(idPessoa) ON DELETE SET NULL,
    FOREIGN KEY (fkCargo) REFERENCES Cargo(idCargo) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    Pessoa_idPessoa INT NOT NULL,
    fkCargo INT,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    FOREIGN KEY (Pessoa_idPessoa) REFERENCES Pessoa(idPessoa) ON DELETE CASCADE,
    FOREIGN KEY (fkCargo) REFERENCES Cargo(idCargo) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Ocorrencia (
    idOcorrencia INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    descricao TEXT,
    Pessoa_idPessoa INT NOT NULL,
    FOREIGN KEY (Pessoa_idPessoa) REFERENCES Pessoa(idPessoa) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Documento (
    idDocumento INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    url VARCHAR(2048) NOT NULL,
    Pessoa_idPessoa INT NOT NULL,
    FOREIGN KEY (Pessoa_idPessoa) REFERENCES Pessoa(idPessoa) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Presenca (
    Chamada_idChamada INT,
    Pessoa_idPessoa INT,
    presenca BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (Chamada_idChamada, Pessoa_idPessoa),
    FOREIGN KEY (Chamada_idChamada) REFERENCES Chamada(idChamada) ON DELETE CASCADE,
    FOREIGN KEY (Pessoa_idPessoa) REFERENCES Pessoa(idPessoa) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Ficha_medica (
    idFichaMedica INT AUTO_INCREMENT PRIMARY KEY,
    Pessoa_idPessoa INT UNIQUE NOT NULL,
    FOREIGN KEY (Pessoa_idPessoa) REFERENCES Pessoa(idPessoa) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Medicacao (
    idMedicacao INT AUTO_INCREMENT PRIMARY KEY,
    Ficha_medica_idFichaMedica INT NOT NULL,
    Medicamento_idMedicamento INT NOT NULL,
    Documento_idDocumento INT,
    horario_inicio DATETIME,
    horario_fim DATETIME,
    dose DECIMAL(10,2),
    FOREIGN KEY (Ficha_medica_idFichaMedica) REFERENCES Ficha_medica(idFichaMedica) ON DELETE CASCADE,
    FOREIGN KEY (Medicamento_idMedicamento) REFERENCES Medicamento(idMedicamento) ON DELETE CASCADE,
    FOREIGN KEY (Documento_idDocumento) REFERENCES Documento(idDocumento) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Diagnostico (
    idDiagnostico INT AUTO_INCREMENT PRIMARY KEY,
    Ficha_medica_idFichaMedica INT NOT NULL,
    Comorbidade_idComorbidade INT NOT NULL,
    Documento_idDocumento INT,
    FOREIGN KEY (Ficha_medica_idFichaMedica) REFERENCES Ficha_medica(idFichaMedica) ON DELETE CASCADE,
    FOREIGN KEY (Comorbidade_idComorbidade) REFERENCES Comorbidade(idComorbidade) ON DELETE CASCADE,
    FOREIGN KEY (Documento_idDocumento) REFERENCES Documento(idDocumento) ON DELETE SET NULL
);

