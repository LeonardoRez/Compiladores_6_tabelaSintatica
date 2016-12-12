package compiladores6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Tabela {

    public String simbolos[];
    public String variaveis[];
    public Producao matriz[][];

    public Tabela(File f) throws IOException {
        //File f = new File("arq.txt");
        //FileReader fr = new FileReader((new File("src/compiladores6/arq.txt")));
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String sCurrentLine;

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
        for (j++; j < sCurrentLine.length(); j++) {
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
            if (sCurrentLine.charAt(i) != '\t' && sCurrentLine.charAt(i) != ' ' && sCurrentLine.charAt(i) != ',') {
                temp += sCurrentLine.charAt(i);
            }
            if (sCurrentLine.charAt(i) == ',' || i == (sCurrentLine.length() - 1)) {
                simbolos[quant++] = temp;
                temp = "";
            }
        }
        //LENDO PRODUCOES PARA POPULAR MATRIZ
        m = 0;
        n = 0;
        quant = 0;
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
                if (sCurrentLine.charAt(i) != '\t' && sCurrentLine.charAt(i) != ' ' && sCurrentLine.charAt(i) != ',') {
                    temp += sCurrentLine.charAt(i);
                }
                if (sCurrentLine.charAt(i) == ',' || i == (sCurrentLine.length() - 1)) {
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

    public boolean validaPalavra(String varInicial, Stack<String> tokens) throws IOException{
        Stack<String> var = new Stack<>();
        var.add("$");
        var.add(varInicial);
        while (!var.isEmpty() && !tokens.isEmpty()) {
            if (tokens.peek().compareTo("$") == 0 && var.peek().compareTo("$") == 0) {
                var.pop();
                tokens.pop();
                break;
            }
            int n = indexTerminal(tokens.peek());
            int m = indexVariavel(var.pop());
            if (m < 0 || n < 0) {
                throw new IOException("Tabela/var inicial apresenta problema");
            }
            Producao temp = matriz[m][n];
            if (temp == null) {
                return false;
            }
            for (int i = (temp.simbolos[0].size() - 1); i >= 0; i--) {
                var.add(temp.simbolos[0].get(i));
            }
            if (tokens.peek().compareTo(var.peek()) == 0) {
                var.pop();
                tokens.pop();
            }
            if (var.peek().compareTo("&") == 0) {
                var.pop();
            }
        }
        if (var.isEmpty() && tokens.isEmpty()) {
            return true;
        }

        return false;
    }

    private int indexVariavel(String nome) {
        for (int i = 0; i < variaveis.length; i++) {
            if (variaveis[i].compareTo(nome) == 0) {
                return i;
            }
        }
        return -1;
    }

    private int indexTerminal(String nome) {
        for (int i = 0; i < simbolos.length; i++) {
            if (simbolos[i].compareTo(nome) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {

        /*        Tabela t = new Tabela();
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
        Stack<String> tokens = new Stack<>();
        tokens.add("$");
        tokens.add("id");
        tokens.add("*");
        tokens.add("id");
        tokens.add("+");
        tokens.add("id");
        tokens.add("+");
        tokens.add("id");
        tokens.add("id");
        if (t.validaPalavra("E", tokens)) {
            System.out.println("FOI ACEITO");
        } else {
            System.out.println("DEU RUIM");
        }
         */
    }

}
