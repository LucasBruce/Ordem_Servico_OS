-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Tempo de geração: 16-Dez-2020 às 05:45
-- Versão do servidor: 10.4.17-MariaDB
-- versão do PHP: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `db_infox`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_cliente`
--

CREATE TABLE `tbl_cliente` (
  `id_cliente` int(11) NOT NULL,
  `nome_cliente` varchar(50) NOT NULL,
  `endereco_cliente` varchar(100) DEFAULT NULL,
  `telefone_cliente` varchar(50) NOT NULL,
  `email_cliente` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `tbl_cliente`
--

INSERT INTO `tbl_cliente` (`id_cliente`, `nome_cliente`, `endereco_cliente`, `telefone_cliente`, `email_cliente`) VALUES
(1, 'Linsus Torvalds', 'rua Trovão da Silva', '5555-6666', 'linux@linux.com'),
(2, 'João da Mata', 'Rua Mario Andreazza', '4344-3333', 'joao@yahoo.com'),
(3, 'Edinaldo Pereira', 'Rua Jucelino Cubicheque', '3333-4444', 'edinaldo@outlook.com'),
(4, 'Anderson Silva', 'Avenida Brásil', '6666-6666', 'spiderkick@hotmail.com'),
(5, 'Jailson Mendes ', 'Rua suco de laranja', '6665-5555', 'paidefamilia@gmail.com');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_ordem_servico`
--

CREATE TABLE `tbl_ordem_servico` (
  `id_ordem_servico` int(11) NOT NULL,
  `data_ordem_servico` timestamp NOT NULL DEFAULT current_timestamp(),
  `tipo_ordem_servico` varchar(15) NOT NULL,
  `situacao_ordem_servico` varchar(20) NOT NULL,
  `equipamento` varchar(150) NOT NULL,
  `defeito` varchar(150) NOT NULL,
  `servico` varchar(150) DEFAULT NULL,
  `tecnico` varchar(50) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `id_cliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `tbl_ordem_servico`
--

INSERT INTO `tbl_ordem_servico` (`id_ordem_servico`, `data_ordem_servico`, `tipo_ordem_servico`, `situacao_ordem_servico`, `equipamento`, `defeito`, `servico`, `tecnico`, `valor`, `id_cliente`) VALUES
(1, '2020-12-07 17:09:47', 'Orçamento', 'Na bancada', 'Notebook', 'o dispositivo não liga', 'troca de fonte', 'An Changrim', '87.50', 1),
(2, '2020-12-12 14:56:37', 'Orçamento', 'Na bancada', 'notebook', 'não liga', 'trocar placa mãe', 'An Baul', '500.65', 5),
(3, '2020-12-12 14:57:50', 'Orçamento', 'Na bancada', 'impressora', 'Intupida', 'desintupir', 'An Changrim', '50.80', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_tecnico`
--

CREATE TABLE `tbl_tecnico` (
  `id_tecnico` int(11) NOT NULL,
  `nome_tecnico` varchar(30) NOT NULL,
  `email_tecnico` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `tbl_tecnico`
--

INSERT INTO `tbl_tecnico` (`id_tecnico`, `nome_tecnico`, `email_tecnico`) VALUES
(1, 'carluxo', 'carluxo@yahoo.com');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_usuario`
--

CREATE TABLE `tbl_usuario` (
  `id_usuario` int(11) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `fone` varchar(15) DEFAULT NULL,
  `login` varchar(15) NOT NULL,
  `senha` varchar(15) NOT NULL,
  `perfil` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `tbl_usuario`
--

INSERT INTO `tbl_usuario` (`id_usuario`, `usuario`, `fone`, `login`, `senha`, `perfil`) VALUES
(1, 'Lucas Bruce', '9999-9999', 'lucasbruce', '123456', 'admin'),
(2, 'Administrador', '8888-8888', 'admin', 'admin', 'admin');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `tbl_cliente`
--
ALTER TABLE `tbl_cliente`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Índices para tabela `tbl_ordem_servico`
--
ALTER TABLE `tbl_ordem_servico`
  ADD PRIMARY KEY (`id_ordem_servico`),
  ADD KEY `fk_id_ordem_servico_id_cliente` (`id_cliente`);

--
-- Índices para tabela `tbl_tecnico`
--
ALTER TABLE `tbl_tecnico`
  ADD PRIMARY KEY (`id_tecnico`);

--
-- Índices para tabela `tbl_usuario`
--
ALTER TABLE `tbl_usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `tbl_cliente`
--
ALTER TABLE `tbl_cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `tbl_ordem_servico`
--
ALTER TABLE `tbl_ordem_servico`
  MODIFY `id_ordem_servico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `tbl_tecnico`
--
ALTER TABLE `tbl_tecnico`
  MODIFY `id_tecnico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `tbl_usuario`
--
ALTER TABLE `tbl_usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `tbl_ordem_servico`
--
ALTER TABLE `tbl_ordem_servico`
  ADD CONSTRAINT `fk_id_ordem_servico_id_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `tbl_cliente` (`id_cliente`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
