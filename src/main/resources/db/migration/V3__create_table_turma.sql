CREATE TABLE Turma (
    Codigo INT AUTO_INCREMENT PRIMARY KEY,
    Inicio DATE NOT NULL,
    Fim DATE NOT NULL,
    Local VARCHAR(200),
    Curso INT,
    FOREIGN KEY (Curso) REFERENCES Curso(Codigo)
);