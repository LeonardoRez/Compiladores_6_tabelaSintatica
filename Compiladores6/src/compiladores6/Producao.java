package compiladores6;

import java.util.ArrayList;

class Simbolo {

    String simbolo;

    public Simbolo() {
    }

    @Override
    public String toString() {
        return simbolo;
    }

}

public class Producao {

    String nome;
    ArrayList<String> simbolos[];

    public Producao(String producao) {
        int quantT[] = validarProducao(producao);
        int quantRegras = -1;
        String temp = "";
        simbolos = new ArrayList[quantT[1]];
        for (int i = 0; i < quantT[1]; i++) {
            simbolos[i] = new ArrayList<>();
        }
        for (int i = 0; i < producao.length(); i++) {
            if (producao.charAt(i) == '<') {
                temp = "";
                if (quantRegras == -1) {
                    while (producao.charAt(i+1) != '>') {
                        temp += producao.charAt(++i);
                    }
                    nome = temp;
                    quantRegras = 0;
                } else {
                    while (producao.charAt(i+1) != '>') {
                        temp += producao.charAt(++i);
                    }
                    simbolos[quantRegras].add(temp);
                }
            } else if (producao.charAt(i) == '|') {
                quantRegras++;
            }
        }
    }

    public int[] validarProducao(String producao) { //[0] é quantidade maxima de simbolos e [1] é quantidade de regras
        int Q = 0, aux = 0, maxSimb = 0, quantRegras = 1;
        int quantT[] = {0, 0};
        for (int i = 0; i < producao.length(); i++) {
            Q = automato(Q, producao.charAt(i));
            if (Q == -1) {
                break;
            }
            if (Q == 8) {
                aux++;
                if (aux > maxSimb) {
                    maxSimb = aux;
                }
            } else if (Q == 9) {
                quantRegras++;
                aux = 0;
            }
        }
        if (Q == 8) {
            quantT[0] = maxSimb;
            quantT[1] = quantRegras;
            return quantT;
        } else {
            throw new IllegalArgumentException("Produção inválida!");
        }
    }

    protected int automato(int Q, char C) { //testa a String de entrada pra validar o alfabeto
        switch (Q) {
            case 0:
                if (C == ' ') {
                    return 0;
                } else if (C == '<') {
                    return 1;
                } else {
                    return -1; //estado de erro
                }
            case 1:
                if (C == ' ') {
                    return 1;
                } else if (isValid(C)) {
                    return 2;
                } else {
                    return -1; //estado de erro
                }
            case 2:
                if (C == ' ' || isValid(C)) {
                    return 2;
                } else if (C == '>') {
                    return 3;
                } else {
                    return -1; //estado de erro
                }
            case 3:
                if (C == ' ') {
                    return 3;
                } else if (C == '-') {
                    return 4;
                } else {
                    return -1; //estado de erro
                }
            case 4:
                if (C == '>') {
                    return 5;
                } else {
                    return -1; //estado de erro
                }
            case 5:
                if (C == ' ') {
                    return 5;
                } else if (C == '<') {
                    return 6;
                } else {
                    return -1; //estado de erro
                }
            case 6:
                if (C == ' ') {
                    return 6;
                } else if (isValid(C)) {
                    return 7;
                } else {
                    return -1; //estado de erro
                }
            case 7:
                if (C == ' ' || isValid(C)) {
                    return 7;
                } else if (C == '>') {
                    return 8;
                } else {
                    return -1; //estado de erro
                }
            case 8:
                if (C == ' ') {
                    return 8;
                } else if (C == '|') {
                    return 9;
                } else if (C == '<') {
                    return 6;
                } else {
                    return -1; //estado de erro
                }
            case 9:
                if (C == ' ') {
                    return 9;
                } else if (C == '<') {
                    return 6;
                } else {
                    return -1; //estado de erro
                }
            case -1:
                return -1; //estado de erro

        }
        return -1;
    }

    public boolean isValid(char C) {
        return (C >= '!' && C <= ';') || (C > '>' && C <= '~') || (C == '=');
    }

    @Override
    public String toString() {
        String saida = nome + " -> ";
        for (ArrayList<String> a : simbolos) {
            for (String s : a) {
                saida += s;
            }
            saida += "|";
        }
        return saida.substring(0, saida.length()-1);
    }

    public static void main(String[] args) {
        try {
            Producao p = new Producao("<A'> -> <a><A>|<&>");
            System.out.println(p);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
