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
    private static String linhas;
    
    
    public void chamadaVoz() {
    if (emissor == null || emissor.isEmpty() || receptor == null || receptor.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Emissor e receptor não podem estar vazios!");
        return;
    }
    
    // Definimos um limite de 60min por chamada, o grt desconta 1kz por minuto
    int custo = (int) (Math.random() * 60) + 1;
    String duracao = custo + "min";
    String caminho = "src/arquivos/chamada.txt";
    File arquivo = new File(caminho);
    boolean emissorExiste = false;
    boolean receptorExiste = false;
    boolean saldoSuficiente = false;

    try (BufferedReader leitor = new BufferedReader(new FileReader("src/arquivos/telefone.txt"))) {
        String l;
        while ((l = leitor.readLine()) != null) {
            if (l.contains(emissor)) {
                emissorExiste = true;
                String[] s = l.split("\\|");
                int saldo = Integer.parseInt(s[4]);
                if (saldo >= custo) {
                    saldoSuficiente = true;
                }
            }
            if (l.contains(receptor)) {
                receptorExiste = true;
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo de telefones");
        return;
    }

    if (!emissorExiste || !receptorExiste) {
        JOptionPane.showMessageDialog(null, "Emissor ou receptor não existem!");
        return;
    }

    if (!saldoSuficiente) {
        JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar a chamada!");
        return;
    }

    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminho, true))) {
        BufferedReader leitor = new BufferedReader(new FileReader("src/arquivos/telefone.txt"));
        String l;
        while ((l = leitor.readLine()) != null) {
            if (l.contains(receptor)) {
                String[] s = l.split("\\|");
                String estadoT = s[3];
                if (estadoT.contains("Ligado")) {
                    atualizarID();
                    escritor.write(id + "|" + emissor + "|" + receptor + "|" + "Voz" + "|" + duracao + "|" + custo + "|" + "AOA" + "|" + "Bem Sucedida");
                    escritor.newLine();
                    JOptionPane.showMessageDialog(null, "Chamada Efetuada!!");
                    atualizarSaldoVozCliente(custo);
                    atualizarSaldoVozTelefone(custo);
                } else {
                    atualizarID();
                    escritor.write(id + "|" + emissor + "|" + receptor + "|" + "Voz" + "|" + "0min" + "|" + "0" + "|" + "AOA" + "|" + "Mal Sucedida");
                    escritor.newLine();
                    JOptionPane.showMessageDialog(null, "Chamada Não Efetuada!! \n O Número Para Qual Tentou Contactar Não Está Disponível!");
                }
                break;
            }
        }
        leitor.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro, Não foi possível solicitar a chamada");
    }
}
    
    public void chamadaSMS() {
    if (emissor == null || emissor.isEmpty() || receptor == null || receptor.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Emissor e receptor não podem estar vazios!");
        return;
    }
    
    if (sms.isEmpty() || sms.equals("")) {
        JOptionPane.showMessageDialog(null, "Escreva a Mensagem");
        return;
    }

    new Thread(() -> {
        String caminho = "src/arquivos/chamada.txt";
        File arquivo = new File(caminho);
        int custo = sms.length();

        if (custo < 50) {
            custo = 10;
        } else if (custo < 100) {
            custo = 16;
        } else {
            custo *= 2;
        }

        boolean emissorExiste = false;
        boolean receptorExiste = false;
        boolean saldoSuficiente = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader("src/arquivos/telefone.txt"))) {
            String l;
            while ((l = leitor.readLine()) != null) {
                if (l.contains(emissor)) {
                    emissorExiste = true;
                    String[] s = l.split("\\|");
                    int saldo = Integer.parseInt(s[4]);
                    if (saldo >= custo) {
                        saldoSuficiente = true;
                    }
                }
                if (l.contains(receptor)) {
                    receptorExiste = true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo de telefones");
            return;
        }

        if (!emissorExiste || !receptorExiste) {
            JOptionPane.showMessageDialog(null, "Emissor ou receptor não existem!");
            return;
        }

        if (!saldoSuficiente) {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente para enviar a mensagem!");
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminho, true))) {
            BufferedReader leitor = new BufferedReader(new FileReader("src/arquivos/telefone.txt"));
            String l;
            while ((l = leitor.readLine()) != null) {
                if (l.contains(receptor)) {
                    String[] s = l.split("\\|");
                    String estadoT = s[3];
                    if (estadoT.contains("Ligado") || estadoT.contains("Silêncio")) {
                        atualizarID();
                        escritor.write(id + "|" + emissor + "|" + receptor + "|" + "SMS" + "|" + custo + "|" + "AOA" + "|" + "Enviada");
                        escritor.newLine();
                        JOptionPane.showMessageDialog(null, "Mensagem Enviada \n" + sms);
                        atualizarSaldoSms(custo);
                    } else if (estadoT.contains("Desligado")) {
                        atualizarID();
                        escritor.write(id + "|" + emissor + "|" + receptor + "|" + "SMS" + "|" + "0" + "|" + "AOA" + "|" + "Não Enviada");
                        escritor.newLine();
                        JOptionPane.showMessageDialog(null, "Mensagem Não Enviada");
                    }
                    break;
                }
            }
            leitor.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível solicitar o envio");
        }
    }).start();
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
     
    
   public void atualizarSaldoSms(int valor) {
    new Thread(() -> {
        File tempFile = new File("src/arquivos/tempFile.txt");
        File inputFile = new File("src/arquivos/cliente.txt");
        File temp = new File("src/arquivos/temp.txt");
        File input = new File("src/arquivos/telefone.txt");

        try (BufferedReader cliente = new BufferedReader(new FileReader(inputFile));
             BufferedReader telefone = new BufferedReader(new FileReader(input));
             BufferedWriter tempC = new BufferedWriter(new FileWriter(tempFile));
             BufferedWriter tempT = new BufferedWriter(new FileWriter(temp))) {

            String c;
            String t;

            while ((c = cliente.readLine()) != null && (t = telefone.readLine()) != null) {
                if (c.contains(emissor) && t.contains(emissor)) {
                    String[] sC = c.split("\\|");
                    String[] sT = t.split("\\|");

                    if (sC.length > 1 && sT.length > 1) {
                        int valor_t = Integer.parseInt(sT[4]);
                        int tF = valor_t - valor;
                        sT[4] = Integer.toString(tF);

                        int valor_c = Integer.parseInt(sC[7]);
                        int cF = valor_c - valor;
                        sC[7] = Integer.toString(cF);
                    }

                    c = String.join("|", sC);
                    t = String.join("|", sT);
                }

                tempC.write(c);
                tempC.newLine();
                tempT.write(t);
                tempT.newLine();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível atualizar o saldo: " + e.getMessage());
        }

        // Deletar o arquivo original e renomear o arquivo temporário
        if (inputFile.delete() && tempFile.renameTo(inputFile)) {
            System.out.println("Arquivo de clientes atualizado com sucesso.");
        } else {
            System.out.println("Não foi possível atualizar o arquivo de clientes.");
        }

        if (input.delete() && temp.renameTo(input)) {
            System.out.println("Arquivo de telefones atualizado com sucesso.");
        } else {
            System.out.println("Não foi possível atualizar o arquivo de telefones.");
        }
    }).start();
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
    
    public void visualizarChamadas()
    {
        File arquivo = new File("src/arquivos/chamada.txt");
        if(!arquivo.exists())
        {
            linhas = "Arquivo Inexistente";
        }
        
        else{
            try(BufferedReader leitor = new BufferedReader(new FileReader(arquivo)))
            { 
                String line;
                StringBuilder s = new StringBuilder();
                while((line = leitor.readLine()) != null)
                {
                    linhas = s.append(line).append("\n").toString(); 
                }
                
                leitor.close();
            }
            
            catch(Exception e)
            {
                linhas = "Erro ao solicitar o histórico";
            }
        }
    }
    
    public void consultarChamada() {
    String caminho = "src/arquivos/chamada.txt";
    File arquivo = new File(caminho);

    if (emissor.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Insira o Telefone");
    } else if (!arquivo.exists()) {
        JOptionPane.showMessageDialog(null, "Arquivo Inexistente");
    } else {
        boolean telefoneEncontrado = false;
        StringBuilder chamadasEncontradas = new StringBuilder();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.contains(emissor)) {
                    telefoneEncontrado = true;
                    chamadasEncontradas.append(linha).append("\n");
                }
            }

            if (!telefoneEncontrado) {
                JOptionPane.showMessageDialog(null, "O Telefone não existe");
            } else {
                JOptionPane.showMessageDialog(null, "Chamada(s) \n" + chamadasEncontradas.toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar as chamadas");
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
    
    public String getLinhas()
    {
        return linhas;
    }
}
