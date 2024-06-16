/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor_telefones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Telefone 
{
    private String id_Cliente;
    private int idTelefone = 1;
    private int saldo = 1000;
    private static String linhasT;
    
    public static void main(String [] args)
    {

    }
    
    
     public void gerarTelefone() {
        String clienteFilePath = "src/arquivos/cliente.txt";
        File clienteFile = new File(clienteFilePath);

        // Primeira validação: verificar se o arquivo cliente.txt existe
        if (!clienteFile.exists()) {
            JOptionPane.showMessageDialog(null, "O arquivo do cliente não existe.");
            return;
        }

        // Segunda validação: verificar se o ID do cliente está presente no arquivo cliente.txt
        boolean clienteEncontrado = false;
        try (BufferedReader leitor = new BufferedReader(new FileReader(clienteFile))) {
            String line;
            while ((line = leitor.readLine()) != null) {
                if (line.contains(id_Cliente)) {
                    clienteEncontrado = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!clienteEncontrado) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado");
            return;
        }

        // Terceira validação: verificar se o cliente já possui um telefone registrado
        String telefoneFilePath = "src/arquivos/telefone.txt";
        File telefoneFile = new File(telefoneFilePath);
        boolean clienteJaPossuiTelefone = false;

        if (telefoneFile.exists()) {
            try (BufferedReader leitorTelefone = new BufferedReader(new FileReader(telefoneFile))) {
                String line;
                while ((line = leitorTelefone.readLine()) != null) {
                    if (line.contains(id_Cliente)) {
                        clienteJaPossuiTelefone = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (clienteJaPossuiTelefone) {
            JOptionPane.showMessageDialog(null, "O cliente já possui um telefone registrado.");
            return;
        }

        // Geração de um novo telefone
        int primeiroDigito = 9;
        int par = 2 + (2 * (int) (Math.random() * 4));
        int[] outrosDigitos = new int[7];
        for (int i = 0; i < 7; i++) {
            outrosDigitos[i] = (int) (Math.random() * 10);
        }

        StringBuilder telefone = new StringBuilder();
        telefone.append(primeiroDigito).append(par);
        for (int digito : outrosDigitos) {
            telefone.append(digito);
        }

        try (BufferedWriter linha = new BufferedWriter(new FileWriter(telefoneFilePath, true))) {
            atualizarIdTelefone();
            linha.write(idTelefone + "|" + telefone.toString() + "|" + id_Cliente + "|" + "Ligado" + "|" + saldo + "|" + "AOA");
            replaceCliente(telefone.toString(), "1000");
            linha.newLine();
            linha.flush();
            JOptionPane.showMessageDialog(null, "Telefone Criado com Sucesso" + "\n" + idTelefone + "\n" + id_Cliente + "\n" + telefone.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível gerar um numero de telefone");
        }
    }
     
     public void visualizarTelefone()
     {
         String caminho = "src/arquivos/telefone.txt";
         File arquivo = new File(caminho);
         if(!arquivo.exists())
         {
             linhasT = "Nenhum Registo foi Detetado";
         }
         
         else{
             try(BufferedReader leitor = new BufferedReader(new FileReader(caminho)))
             {
                 String l;
                 StringBuilder conteudo = new StringBuilder();
                 while((l = leitor.readLine()) !=null)
                 {
                     linhasT = conteudo.append(l).append("\n").toString();
                 }
             }
             
             catch(Exception e)
             {
                 linhasT = "Nenhum Registo Existente";
             }
         }
     }
        
           
    
    public void replaceCliente(String telefone, String saldoT)
    {
        File inputFile = new File("src/arquivos/cliente.txt");
        File tempFile = new File("src/arquivos/temp.txt");
        try(BufferedReader leitor = new BufferedReader(new FileReader(inputFile));
        BufferedWriter escritor = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            
            //para ler o arquivo original
            while((line = leitor.readLine()) !=null)
            {   
                //verifica a linha que irá sofrer alterações
                if(line.contains(id_Cliente))
                {   
                    //delimita a linha afetada
                    String[] s = line.split("\\|");
                    
                    if(s.length > 1)
                    {   
                        //altera o arquivo
                        s[6] = telefone;
                        s[7] = saldoT;
                    }
                    
                    line = String.join("|", s);
                }
                
                escritor.write(line);
                escritor.newLine();
                
            }
            
        }
        
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível atualizar o cliente");
        }
        
        if (!inputFile.delete()) {
            System.out.println("Não foi possível deletar o arquivo original");
            return;
        }
        
        else{
            inputFile.delete();
        }

        // Renomear o arquivo temporário para o nome do arquivo original
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Não foi possível renomear o arquivo temporário");
        }
        
        else{
            tempFile.renameTo(inputFile);
        }
    
        
    }
    
    public void atualizarIdTelefone()
    {
        String caminho = "src/arquivos/telefone.txt";
        File arquivo = new File(caminho);
        if(arquivo.exists())
        {
            try(BufferedReader linha = new BufferedReader(new FileReader(caminho)))
            {   
                String id = null;
                String l;
                while((l = linha.readLine()) != null)
                {
                    String[] s = l.split("\\|");
                    if(s.length > 0)
                    {
                        id = s[0];
                    }
                }
                
                if(id != null)
                {
                    int valor = parseInt(id);
                    valor++;
                    idTelefone = valor;
                    System.out.println(idTelefone);
                }
            }
            
            catch(Exception e)
            {
                
            }
        }
    }
    
    public void setId_Cliente(String valor)
    {
        this.id_Cliente = valor;
    }
    
    public String getLinhas()
    {
        return linhasT;
    }
    
    
}





