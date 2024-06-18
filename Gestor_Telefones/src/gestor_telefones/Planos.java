/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor_telefones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import static java.lang.Integer.parseInt;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Planos {
    private String telefone;
    
   public void novoPlanoCliente(int valor) {
    new Thread(() -> {
        File inputFile = new File("src/arquivos/cliente.txt");
        File tempFile = new File("src/arquivos/tempFile.txt");
        boolean telefoneEncontrado = false;

        if (!inputFile.exists()) {
            JOptionPane.showMessageDialog(null, "Arquivo Inexistente");
        } else if (telefone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira o Telefone");
        } else {
            try (BufferedReader leitor = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter escritor = new BufferedWriter(new FileWriter(tempFile))) {

                String linha;
                String sF = null;

                while ((linha = leitor.readLine()) != null) {
                    if (linha.contains(telefone)) {
                        telefoneEncontrado = true;
                        String[] s = linha.split("\\|");
                        if (s.length > 1) {
                            int saldoInicial = Integer.parseInt(s[7]);
                            int saldoFinal = saldoInicial + valor;
                            sF = saldoFinal + " Kzs";
                            s[7] = Integer.toString(saldoFinal);
                        }
                        linha = String.join("|", s);
                    }
                    escritor.write(linha);
                    escritor.newLine();
                }

                if (telefoneEncontrado) {
                    JOptionPane.showMessageDialog(null, "Carregamento Efetuado, saldo atual: " + sF);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não Foi possível efetuar o carregamento");
            }

            if (telefoneEncontrado) {
                if (!inputFile.delete()) {
                    System.out.println("Não foi possível deletar o arquivo original");
                } else {
                    if (!tempFile.renameTo(inputFile)) {
                        System.out.println("Não foi possível renomear o arquivo temporário");
                    }
                }
            } else {
                tempFile.delete();
                JOptionPane.showMessageDialog(null, "O Telefone não foi encontrado");
            }
        }
    }).start();
}

public void novoPlanoTelefone(int valor) {
    new Thread(() -> {
        File inputFile = new File("src/arquivos/telefone.txt");
        File tempFile = new File("src/arquivos/temp.txt");
        boolean telefoneEncontrado = false;

        if (!inputFile.exists()) {
            JOptionPane.showMessageDialog(null, "Arquivo Inexistente");
        } else if (telefone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira o Telefone");
        } else {
            try (BufferedReader leitor = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter escritor = new BufferedWriter(new FileWriter(tempFile))) {

                String linha;
                String sF = null;

                while ((linha = leitor.readLine()) != null) {
                    if (linha.contains(telefone)) {
                        telefoneEncontrado = true;
                        String[] s = linha.split("\\|");
                        if (s.length > 4) {
                            int saldoInicial = Integer.parseInt(s[4]);
                            int saldoFinal = saldoInicial + valor;
                            sF = saldoFinal + " Kzs";
                            s[4] = Integer.toString(saldoFinal);
                            linha = String.join("|", s);
                        }
                    }
                    escritor.write(linha);
                    escritor.newLine();
                }

                if (telefoneEncontrado) {
                    JOptionPane.showMessageDialog(null, "Carregamento Efetuado, saldo atual: " + sF);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não Foi possível efetuar o carregamento");
            }

            if (telefoneEncontrado) {
                // Excluir o arquivo original
                if (!inputFile.delete()) {
                    System.out.println("Não foi possível deletar o arquivo original");
                } else {
                    // Renomear o arquivo temporário para o nome do arquivo original
                    if (!tempFile.renameTo(inputFile)) {
                        System.out.println("Não foi possível renomear o arquivo temporário");
                    }
                }
            } else {
                // Excluir o arquivo temporário se o telefone não foi encontrado
                if (!tempFile.delete()) {
                    System.out.println("Não foi possível deletar o arquivo temporário");
                }
                JOptionPane.showMessageDialog(null, "O Telefone não foi encontrado");
            }
        }
    }).start();
}
    
    public void setTelefone(String valor)
    {
        this.telefone = valor;
    }
}
