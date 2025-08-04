CREATE TABLE TurmaParticipante (
    Codigo INT AUTO_INCREMENT PRIMARY KEY,
    Turma INT,
    FOREIGN KEY (Turma) REFERENCES Turma(Codigo),
    Funcionario INT,
    FOREIGN KEY (Funcionario) REFERENCES Funcionario(Codigo)
);