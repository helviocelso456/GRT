/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor_telefones;

/**
 *
 * @author Administrator
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
 import java.io.FileWriter;
import static java.lang.Integer.parseInt;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Cliente {
    
    private int idCliente = 100;
    private String Nome;
    private String Sobrenome;
    private String Nif;
    private static String linhas;
    
    //Main
    public static void main(String [] args)
    {
        
    }
    
    //idCliente
    public void setId(int valor)
    {   
        this.idCliente = valor;
        
    }
    //Criar Cliente
    public void criaCliente()
    {
       
        String cId ="cli"+idCliente+"ao";
        String nif = "Nif"+Nif+"AO";
        String caminho = "src/arquivos/cliente.txt";
        File linha = new File(caminho);
        
        if(Nome.isEmpty() || Sobrenome.isEmpty() || Nif.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Preencha os dados!!");
        }
        
        else{
         
         if(!linha.exists())
         {
             try(BufferedWriter br = new BufferedWriter(new FileWriter(caminho)))
            {
               br.write("Cliente" + "|" + cId + "|" + Nome + " " + Sobrenome + "|" + nif + "|Normal|Activo|000000000|0|AOA");
               br.newLine();
               br.flush();
               br.close();
               JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso" +"\n" +cId +"\n"+Nome+" "+Sobrenome+"\n"+nif); 
               
            } 
            catch(Exception e)
            {
               JOptionPane.showMessageDialog(null, "Erro ao cadastrar Cliente");   
            }
         }
         
         else{
             try(BufferedWriter br = new BufferedWriter(new FileWriter(caminho, true)))
             { 
               
               atualizarID();
                cId ="cli" +idCliente+ "ao";
               br.write("Cliente" + "|" + cId + "|" + Nome + " " + Sobrenome + "|" + nif + "|Normal|Activo|000000000|0|AOA");
               br.newLine();
               br.flush();
               br.close();
               JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso" +"\n" +cId +"\n"+Nome+" "+Sobrenome+"\n"+nif);
               
               
               
               
             } 
             catch(Exception e)
             {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar Cliente"); 
             }
         }
        
        }
        
        
        
        
    }
    
    public void visualizarCliente()
    {
        String caminho = "src/arquivos/cliente.txt";
        File arquivo  = new File(caminho);
        if(arquivo.exists())
        {
            try(BufferedReader ler = new BufferedReader(new FileReader(caminho)))
            {  
                String linha;
                StringBuilder conteudo = new StringBuilder();
                while((linha = ler.readLine()) !=null)
                {
                    linhas = conteudo.append(linha).append("\n").toString();
                }
                
                ler.close();
            
            }
        
            catch(Exception e)
            {
               linhas = "Não foi possível visualizar os clientes";
            }
        }
        
        else{
            linhas = "Arquivo Inexistete";
        }
              
        
        
    }
    
    public void atualizarID() {
        String caminho = "src/arquivos/cliente.txt";
        
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String l;
            String ultimo_id = null;
            
            // Ler o arquivo para encontrar o último ID
            while ((l = leitor.readLine()) != null) {
                String[] row = l.split("\\|");
                if (row.length > 1) {
                    ultimo_id = row[1];
                }
            }
            
            // Atualizar o ID do cliente
            if (ultimo_id != null) {
                try {
                    // Extrai apenas os dígitos do ID encontrado
                    String numeroId = ultimo_id.replaceAll("\\D", "");
                    int valor = Integer.parseInt(numeroId);
                    valor++;
                    setId(valor); // Incrementa e atualiza o ID
                    System.out.println(valor);
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao converter o ID do cliente");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo de clientes");
            
        }
    }
    
    public void consultarCliente(String valor)
    {
        File cliente = new File("src/arquivos/cliente.txt");
        Boolean clienteEncontrado = false;
        if(!cliente.exists())
        {
            JOptionPane.showMessageDialog(null, "Arquivo Inexistente");
            
        }
        
        else if(valor.isEmpty() || valor.isBlank())
        {
            JOptionPane.showMessageDialog(null, "Insira o Id do cliente");
        }
        
        else{
            try(BufferedReader leitor = new BufferedReader(new FileReader(cliente)))
            {
                StringBuilder c = new StringBuilder();
                String line;
                while((line = leitor.readLine()) !=null)
                {
                    if(line.contains(valor))
                    {
                        clienteEncontrado = true;
                         c.append(line).append("\n");    
                    }
                }
                
                if (!clienteEncontrado) {
                JOptionPane.showMessageDialog(null, "O Cliente não existe");
            } else {
                JOptionPane.showMessageDialog(null, "Dados do Cliente \n" + c.toString());
            }
            }
            
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Não foi possível consultar o cliente");
            }
        }
    }
    
    public void setlinha(String valor)
    {
        this.linhas = valor;
    }
    public String getlinhas()
    {
        return linhas;
    }
    
    public void setNome(String nome)
    {
        this.Nome = nome;
    }
    
    public void setSobrenome(String sobrenome)
    {
        this.Sobrenome = sobrenome;
    }
    
    public void setNif(String nif)
    {
        this.Nif = nif;
    }
}
