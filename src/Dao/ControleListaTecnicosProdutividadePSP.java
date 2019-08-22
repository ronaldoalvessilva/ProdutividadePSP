/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;


import Modelo.RegistroAtendimentoInternos;
import static Visao.Produtividade.tipoServidor;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ronal
 */
public class ControleListaTecnicosProdutividadePSP {

    ConexaoBancoDados conecta = new ConexaoBancoDados();
    RegistroAtendimentoInternos objProdKit = new RegistroAtendimentoInternos();
    //
    String dataInicial;
    String dataFinal;

    public List<RegistroAtendimentoInternos> read() throws Exception {
        conecta.abrirConexao();
        if (tipoServidor == null || tipoServidor.equals("")) {
            JOptionPane.showMessageDialog(null, "É necessário definir o parâmtero para o sistema operacional utilizado no servidor, (UBUNTU-LINUX ou WINDOWS SERVER).");
        } else if (tipoServidor.equals("Servidor Linux (Ubuntu)/MS-SQL Server")) {
            List<RegistroAtendimentoInternos> listaTecnicosPSP = new ArrayList<RegistroAtendimentoInternos>();
            try {
               SimpleDateFormat formatoAmerica = new SimpleDateFormat("yyyy/MM/dd");
                conecta.executaSQL("SELECT REGISTRO_ATENDIMENTO_INTERNO_PSP.UsuarioUp, DEPARTAMENTOS.NomeDepartamento,"
                        + "SUM(CASE WHEN REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento >= DATEADD(DAY, DATEDIFF(DAY, 0, GETDATE()),0) THEN REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd END) AS TotalDiario, "
                        + "SUM(CASE WHEN REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento >= DATEADD(WEEK, DATEDIFF(WEEK, 0, GETDATE()),0) THEN REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd END) AS TotalSemanal, "
                        + "SUM(CASE WHEN REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento >= DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()),0) THEN REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd END) AS TotalMensal "
                        + "FROM REGISTRO_ATENDIMENTO_INTERNO_PSP "
                        + "LEFT JOIN DEPARTAMENTOS "
                        + "ON REGISTRO_ATENDIMENTO_INTERNO_PSP.IdDepartamento=DEPARTAMENTOS.IdDepartamento "
                        + "WHERE REGISTRO_ATENDIMENTO_INTERNO_PSP.Atendido LIKE 'Sim' AND "
                        + "DATEPART(YEAR, REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento) = DATEPART(YEAR, GETDATE()) "
                        + "GROUP BY REGISTRO_ATENDIMENTO_INTERNO_PSP.UsuarioUp, DEPARTAMENTOS.NomeDepartamento, REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd");
                while (conecta.rs.next()) {
                    RegistroAtendimentoInternos pDigi = new RegistroAtendimentoInternos();
                    pDigi.setNomeFunc(conecta.rs.getString("UsuarioUp"));
                    pDigi.setNomeDepartamento(conecta.rs.getString("NomeDepartamento"));
                    pDigi.setQtdAtend(conecta.rs.getInt("TotalDiario"));
                    pDigi.setQtdAtendSem(conecta.rs.getInt("TotalSemanal"));
                    pDigi.setQtdAtendMes(conecta.rs.getInt("TotalMensal"));
                    listaTecnicosPSP.add(pDigi);
                    //qtdTecnicosPSP = qtdTecnicosPSP + 1;
                }
                return listaTecnicosPSP;
            } catch (SQLException ex) {
                Logger.getLogger(ControleListaTecnicosProdutividadePSP.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conecta.desconecta();
            }
        } else if (tipoServidor.equals("Servidor Windows/MS-SQL Server")) {
            List<RegistroAtendimentoInternos> listaTecnicosPSP = new ArrayList<RegistroAtendimentoInternos>();
            try {
              SimpleDateFormat formatoAmerica = new SimpleDateFormat("dd/MM/yyyy");
                conecta.executaSQL("SELECT REGISTRO_ATENDIMENTO_INTERNO_PSP.UsuarioUp, DEPARTAMENTOS.NomeDepartamento,"
                        + "SUM(CASE WHEN REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento >= DATEADD(DAY, DATEDIFF(DAY, 0, GETDATE()),0) THEN REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd END) AS TotalDiario, "
                        + "SUM(CASE WHEN REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento >= DATEADD(WEEK, DATEDIFF(WEEK, 0, GETDATE()),0) THEN REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd END) AS TotalSemanal, "
                        + "SUM(CASE WHEN REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento >= DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()),0) THEN REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd END) AS TotalMensal "
                        + "FROM REGISTRO_ATENDIMENTO_INTERNO_PSP "
                        + "LEFT JOIN DEPARTAMENTOS "
                        + "ON REGISTRO_ATENDIMENTO_INTERNO_PSP.IdDepartamento=DEPARTAMENTOS.IdDepartamento "
                        + "WHERE REGISTRO_ATENDIMENTO_INTERNO_PSP.Atendido LIKE 'Sim' AND "
                        + "DATEPART(YEAR, REGISTRO_ATENDIMENTO_INTERNO_PSP.DataAtendimento) = DATEPART(YEAR, GETDATE()) "
                        + "GROUP BY REGISTRO_ATENDIMENTO_INTERNO_PSP.UsuarioUp, DEPARTAMENTOS.NomeDepartamento, REGISTRO_ATENDIMENTO_INTERNO_PSP.Qtd");
                while (conecta.rs.next()) {
                    RegistroAtendimentoInternos pDigi = new RegistroAtendimentoInternos();
                    pDigi.setNomeFunc(conecta.rs.getString("UsuarioUp"));
                    pDigi.setNomeDepartamento(conecta.rs.getString("NomeDepartamento"));
                    pDigi.setQtdAtend(conecta.rs.getInt("TotalDiario"));
                    pDigi.setQtdAtendSem(conecta.rs.getInt("TotalSemanal"));
                    pDigi.setQtdAtendMes(conecta.rs.getInt("TotalMensal"));
                    listaTecnicosPSP.add(pDigi);
                    //qtdTecnicosPSP = qtdTecnicosPSP + 1;
                }
                return listaTecnicosPSP;
            } catch (SQLException ex) {
                Logger.getLogger(ControleListaTecnicosProdutividadePSP.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conecta.desconecta();
            }
        }
        return null;
    }

}
