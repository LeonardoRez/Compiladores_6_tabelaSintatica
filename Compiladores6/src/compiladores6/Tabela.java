package compiladores6;

import com.sun.org.apache.xerces.internal.util.FeatureState;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tabela {

    public String simbolos[];
    public String variaveis[];
    public Producao matriz[][];

    public Tabela() throws IOException {
        FileReader fr = new FileReader((new File("file:/home/leo/arq.txt")));
        BufferedReader br = new BufferedReader(fr);

        String sCurrentLine;

        br = new BufferedReader(new FileReader(""));
        boolean flag = true;
        String temp = "";
        //PEGANDO QUANTIDADE DE TERMINAIS
        sCurrentLine = br.readLine();
        int j;
        for (j = 0; j < sCurrentLine.length(); j++) {
            if (sCurrentLine.charAt(j) == ' ') {
                break;
            }
            temp += sCurrentLine.charAt(j);
        }
        int n = Integer.parseInt(temp);
        //PEGANDO A QUANTIDADE DE VARIAVEIS
        temp = "";
        for (; j < sCurrentLine.length(); j++) {
            temp += sCurrentLine.charAt(j);
        }
        int m = Integer.parseInt(temp);

        //INSTANCIANDO AS MATRIZES
        simbolos = new String[n];
        variaveis = new String[m];
        matriz = new Producao[m][n];

        //LENDO OS TERMINAIS
        temp = "";
        sCurrentLine = br.readLine();
        int quant = 0;
        for (int i = 0; i < sCurrentLine.length(); i++) {
            if (sCurrentLine.charAt(i) != '\t' || sCurrentLine.charAt(i) != ' ' || sCurrentLine.charAt(i) != ',') {
                temp += sCurrentLine.charAt(i);
            } else if (sCurrentLine.charAt(i) == ',') {
                simbolos[quant++] = temp;
                temp = "";
            }
        }
        //LENDO PRODUCOES PARA POPULAR MATRIZ
        m = 0;
        n = 0;
        int i;
        while ((sCurrentLine = br.readLine()) != null) {
            for (i = 0; i < sCurrentLine.length(); i++) {
                if (sCurrentLine.charAt(i) == ',' || sCurrentLine.charAt(i) == ' ') {
                    break;
                }
                temp += sCurrentLine.charAt(i);
            }
            variaveis[quant++] = temp;
            temp = "";
            for (i++; i < sCurrentLine.length(); i++) {
                if (sCurrentLine.charAt(i) != '\t' || sCurrentLine.charAt(i) != ' ' || sCurrentLine.charAt(i) != ',') {
                    temp += sCurrentLine.charAt(i);
                } else if (sCurrentLine.charAt(i) == ',') {
                    temp = temp.replace(" ", "");
                    temp = temp.replace("\t", "");
                    if (temp.length() == 0) {
                        n++;
                    } else {
                        matriz[m][n++] = new Producao(temp);
                    }
                    temp = "";
                }
            }
            m++;
            n = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        Tabela t = new Tabela();
        System.out.println("TERMINAIS");
        for (String s : t.simbolos) {
            System.out.println(s);
        }
        System.out.println("VARIAVEIS");
        for (String s : t.variaveis) {
            System.out.println(s);
        }
        System.out.println("Matriz");
        for (int i = 0; i < t.variaveis.length; i++) {
            for (int j = 0; j < t.simbolos.length; j++) {
                System.out.print(t.matriz[i][j] + "\t\t\t");
            }
            System.out.println("");
        }
    }

}
