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
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Chamadas {
    
    private String emissor;
    private String receptor;
    private String sms;
    private int id = 1;
    
    
    public void chamadaVoz()
    {   
        //definimos um limite de 60min por chamada, o grt desconta 1kz por minuto
        int custo = (int) (Math.random() * 60)+1;
        String duracao = custo+"min";
        String caminho = "src/arquivos/chamada.txt";
        File arquivo = new File(caminho);
        if(!arquivo.exists())
        {
            try(BufferedWriter escritor = new BufferedWriter(new FileWriter(caminho)))
            {
                
                BufferedReader leitor = new BufferedReader(new FileReader("src/arquivos/telefone.txt"));
                String l;
                while((l = leitor.readLine()) != null)
                {
                    if(l.contains(receptor))
                    {
                        String[] s = l.split("\\|");
                        String estadoT = s[3];
                        if(estadoT.contains("Ligado"))
                        {
                           escritor.write(id+"|"+emissor+"|"+receptor+"|"+"Voz"+"|"+duracao+"|"+custo+"|"+"AOA"+"|"+"Bem Sucedida");
                           escritor.newLine();
                           escritor.close();
                           JOptionPane.showMessageDialog(null, "Chamada Efetuada!!");
                            atualizarSaldoVozCliente(custo);
                            atualizarSaldoVozTelefone(custo);
                        }
                        
                        else{
                            escritor.write(id+"|"+emissor+"|"+receptor+"|"+"Voz"+"|"+"0min"+"|"+"0"+"|"+"AOA"+"|"+"Mal Sucedida");
                            escritor.newLine();
                            escritor.close();
                            JOptionPane.showMessageDialog(null, "Chamada Não Efetuada!! \n O Número Para Qual Tentou Contactar Não Está Disponível!");
                        }
                    }
                }
                
                leitor.close();
                
                
            }
        
            catch(Exception e)
            {
               JOptionPane.showMessageDialog(null, "Erro, Não foi possível solicitar a chamada");
            }
        }
        
        else{
            
            try(BufferedWriter escritor = new BufferedWriter(new FileWriter(caminho, true)))
            {
                
                BufferedReader leitor = new BufferedReader(new FileReader("src/arquivos/telefone.txt"));
                String l;
                while((l = leitor.readLine()) != null)
                {
                    if(l.contains(receptor))
                    {
                        String[] s = l.split("\\|");
                        String estadoT = s[3];
                        if(estadoT.contains("Ligado"))
                        {  
                           atualizarID();
                           escritor.write(id+"|"+emissor+"|"+receptor+"|"+"Voz"+"|"+duracao+"|"+custo+"|"+"AOA"+"|"+"Bem Sucedida");
                           escritor.newLine();
                           escritor.close();
                           JOptionPane.showMessageDialog(null, "Chamada Efetuada!!");
                           atualizarSaldoVozCliente(custo);
                           atualizarSaldoVozTelefone(custo);
                        }
                        
                        else{
                            atualizarID();
                            escritor.write(id+"|"+emissor+"|"+receptor+"|"+"Voz"+"|"+"0min"+"|"+"0"+"|"+"AOA"+"|"+"Mal Sucedida");
                            escritor.newLine();
                            escritor.close();
                            JOptionPane.showMessageDialog(null, "Chamada Não Efetuada!! \n O Número Para Qual Tentou Contactar Não Está Disponível!");
                        }
                    }
                }
                
                leitor.close();
            }
        
            catch(Exception e)
            {
               JOptionPane.showMessageDialog(null, "Erro, Não foi possível solicitar a chamada");
            }
            
        }
        
    }
    
    public void chamadaSMS()
    {
        
    }
    
     public void atualizarSaldoVozCliente(int valor)
     {
         File inputFile = new File("src/arquivos/cliente.txt");
         File tempFile = new File("src/arquivos/temp.txt");
         
         
         try(BufferedReader leitorCliente = new BufferedReader(new FileReader(inputFile));
         BufferedWriter escritorCliente = new BufferedWriter(new FileWriter(tempFile))){
            
             String line;
             while((line = leitorCliente.readLine()) != null)
             {
                 if(line.contains(emissor))
                 {
                     String[] c = line.split("\\|");
                     if(c.length > 1)
                     {
                        int valor_c = Integer.parseInt(c[7]);
                        int cF = valor_c - valor;
                        c[7] = Integer.toString(cF);
                     }
                     
                     line = String.join("|", c);
                 }
                 
                 escritorCliente.write(line);
                 escritorCliente.newLine();
             }
             
             escritorCliente.close();
             leitorCliente.close();
         }
         
         catch(Exception e)
         {
             System.out.println("Erro");
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
     
     public void atualizarSaldoVozTelefone(int valor)
     {
         new Thread(() ->{
         File input = new File("src\\arquivos\\telefone.txt");
         File temp = new File("src/arquivos/temp.txt");
         try(BufferedReader leitorTelefone = new BufferedReader(new FileReader(input));
         BufferedWriter escritorTelefone = new BufferedWriter(new FileWriter(temp)))
         {
            
             String line;
             while((line = leitorTelefone.readLine()) != null)
             {
                 if(line.contains(emissor))
                 {
                     String[] c = line.split("\\|");
                     if(c.length > 1)
                     {
                        int valor_c = Integer.parseInt(c[4]);
                        int cF = valor_c - valor;
                        c[4] = Integer.toString(cF);
                     }
                     
                     line = String.join("|", c);
                 }
                 
                 escritorTelefone.write(line);
                 escritorTelefone.newLine();
                 
             }
             
             escritorTelefone.close();
             leitorTelefone.close();
             
         }
         
         catch(Exception e)
         {
             System.out.println("Erro");
         }
         
        if (!input.delete()) {
            System.out.println("Não foi possível deletar o arquivo original");
            return;
        }
        
        else{
            input.delete();
        }

        // Renomear o arquivo temporário para o nome do arquivo original
        if (!temp.renameTo(input)) {
            System.out.println("Não foi possível renomear o arquivo temporário");
        }
        
        else{
            temp.renameTo(input);
        }
      }).start();
     }
     
    
    public void atualizarSaldoSms()
    {
        
    }
    
    public void atualizarID()
    {
        String caminho = "src/arquivos/chamada.txt";
        File arquivo = new File(caminho);
        if(arquivo.exists())
        {
            try(BufferedReader linha = new BufferedReader(new FileReader(caminho)))
            {   
                String idT = null;
                String l;
                while((l = linha.readLine()) != null)
                {
                    String[] s = l.split("\\|");
                    if(s.length > 0)
                    {
                        idT = s[0];
                    }
                }
                
                if(idT != null)
                {
                    int valor = parseInt(idT);
                    valor++;
                    id= valor;
                    System.out.println(id);
                }
                
                linha.close();
            }
            
            
            catch(Exception e)
            {
                
            }
        }
    }
    
    
    public void setEmissor(String valor)
    {
        this.emissor = valor;
    }
    
    public void setReceptor(String valor)
    {
        this.receptor = valor;
    }
    
    public void setSMS(String valor)
    {
        this.sms = valor;
    }
}
