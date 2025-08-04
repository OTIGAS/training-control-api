CREATE TABLE IF NOT EXISTS Funcionario (
    Codigo INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(200) NOT NULL,
    CPF CHAR(11) NOT NULL,
    Nascimento DATE NOT NULL,
    Cargo VARCHAR(200) NOT NULL,
    Admissao DATE NOT NULL,
    Status BIT NOT NULL
);

-- INSERT INTO Funcionario (Nome, CPF, Nascimento, Cargo, Admissao, Status) VALUES
--     ('Ana Maria', '12345678901', '1985-07-10', 'Analista de RH', '2020-03-01', 1),
--     ('Bruno Souza', '98765432100', '1990-11-25', 'Desenvolvedor', '2021-06-15', 1),
--     ('Carla Lima', '11122233344', '1988-02-14', 'Gerente de Projetos', '2019-09-10', 1);