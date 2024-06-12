/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor_telefones;

/**
 *
 * @author Administrator
 */
import java.io.File;
 import java.io.FileWriter;
import java.nio.file.*;
import java.util.List;
import javax.swing.JOptionPane;

public class Cliente {
    
    private static int idCliente = 100;
    private String Nome;
    private String Sobrenome;
    private String Nif;
    public static String msg;
    //Main
    public static void main(String [] args)
    {
        
    }
    
    //idCliente
    public static void incrementarCliente()
    {
        ++idCliente;
    }
    //Criar Cliente
    public void criaCliente()
    {
        RegistarCliente r = new RegistarCliente();
        
        String caminho = "src\\arquivos\\cliente.txt";
        String id = "cli"+idCliente+"ao";
        
        if(Nif.length() <2)
        {
            JOptionPane.showMessageDialog(null, "Nif incompleto");
        }
        
        else{
        
        try{
           
            File arquivo = new File(caminho);
            FileWriter writer = new FileWriter(arquivo, true);
            if(!arquivo.exists())
            {   
                
                //Adicionando o conteudo
                writer.write("Cliente: "+id+" | "+Nome+" "+Sobrenome +" | NIF"+Nif+"AO" +" | Tipo de Cliente: Normal | Estado do Atendedor: Activo | 000000000 | Saldo: 0.0 AOA" +" \n");
                //Dialog
                JOptionPane.showMessageDialog(null, "Cliente Registado com sucesso!");
                //Concatenação
                 incrementarCliente();
            } else if(arquivo.exists()){
              
                //Adicionando o conteudo
                 writer.write("Cliente: "+id+" | "+Nome+" "+Sobrenome +" | NIF"+Nif+"AO" +" | Tipo de Cliente: Normal | Estado do Atendedor: Activo | 000000000 | Saldo: 0.0 AOA" +" \n");
                //Dialog
                JOptionPane.showMessageDialog(null, "Cliente Registado com sucesso!");
                //Concatenação
                incrementarCliente();
                
            }
            
            writer.close();
            
            
        
        } 
        
           catch(Exception e)
           {
            JOptionPane.showMessageDialog(null, "Erro ao registar o cliente");
           }
        
       }
    }
    
    public void visualizarCliente()
    {
        Path caminho = Path.of("src\\arquivos\\cliente.txt");
        try {
        List<String> linhas = Files.readAllLines(caminho);
        StringBuilder mensagem = new StringBuilder();
        for (String linha : linhas) {
            mensagem.append(linha).append("\n");
        }
        new Cliente().setMsg(mensagem.toString() +"\n");
    } catch (Exception e) {
        new Cliente().setMsg("Cliente Inexistente");
    }
        
            
       
    }
    
    public void setMsg(String mensagem)
    {
        this.msg = mensagem;
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
