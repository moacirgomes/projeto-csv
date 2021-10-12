
package projeto.csv.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JOptionPane;

public class Csv {

    private File caminho;
    private int contadorValido = 0;
    private int contadorInvalido = 0;

    public Csv(File caminho) {
        this.caminho = caminho;
        this.criar_diretorios();
    }

    public int getContadorValido() {
        return contadorValido;
    }

    public int getContadorInvalido() {
        return contadorInvalido;
    }
    
    
    
    public void ler_arquivos_diretorio() throws IOException {
       
        for (File file : caminho.listFiles()) {
            if (!file.isDirectory()) {

                if (verificar_extensao_arquivo(file.getName()).toLowerCase().equals("csv")) {
                   
                    boolean csv_invalido = validar_csv(caminho+"/"+file.getName());
                    
                    if(csv_invalido){
                        contadorInvalido ++;
                        System.out.println("invalido");
                        salvar_csv_invalido(caminho.getPath(), file.getName());
                    }else{
                        contadorValido ++;
                         System.out.println("valido");
                        salvar_csv_valido(caminho.getPath(), file.getName());
                    }
                }

            } else {
//                ler_arquivos_diretorio();
            }
        }
        
    }

    private String verificar_extensao_arquivo(String arquivo) {
        return arquivo.replaceAll("^.*\\.(.*)$", "$1");

    }

    private boolean validar_csv(String arquivoCsv) {
        BufferedReader br = null;
        String linha = "";
        boolean linhaInvalida = false;
        try {

            br = new BufferedReader(new FileReader(arquivoCsv));
           
            
            while ((linha = br.readLine()) != null) {
                String[] linhasCelulas = linha.split(";");
                
                for (int i = 0; i < linhasCelulas.length; i++) {
                    
                    if(linhasCelulas[i].equals("")){
                        linhaInvalida = true;
                        break;
                    }
                   
                }
  
            } 

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return linhaInvalida;
    }
    
    private void criar_diretorios(){
       if (!new File(caminho+"/VALIDADO").exists()) {
            new File(caminho+"/VALIDADO").mkdir();
        }  
       
       if (!new File(caminho+"/INVALIDADO").exists()) {
            new File(caminho+"/INVALIDADO").mkdir();
        } 
    }
    private void salvar_csv_valido(String caminho,  String arquivo) throws IOException{
        
        File source = new File(caminho+"/"+arquivo);
        File dest = new File(caminho+"/VALIDADO/"+arquivo); 
        Files.move(source.toPath(), dest.toPath());
    }
    
    private void salvar_csv_invalido(String caminho,  String arquivo) throws IOException{
        
        
        File source = new File(caminho+"/"+arquivo);
        File dest =  new File(caminho+"/INVALIDADO/"+arquivo); 
        Files.move(source.toPath(), dest.toPath());
        
         
    }
}
