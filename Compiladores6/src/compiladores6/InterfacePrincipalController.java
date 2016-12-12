/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores6;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author leo
 */
public class InterfacePrincipalController implements Initializable {

    @FXML
    TextField tfAddToken;
    @FXML
    TextField entrada;
    @FXML
    TextField varInicial;
    @FXML
    Label log;

    Tabela tabela;
    Stack<String> tokens = new Stack<>();
    File f;

    @FXML
    public void pickTabela() {
        System.out.println("teste");
        try {
            FileChooser fc = new FileChooser();
            f = fc.showOpenDialog(tfAddToken.getScene().getWindow());
            if (f != null) {
                tabela = new Tabela(f);
            }
            log.setText("Tabela carregada com sucesso");
            log.setTextFill(Color.BLUE);
        } catch (Exception e) {
            log.setText(e.getMessage());
            log.setTextFill(Color.RED);
        }
    }

    @FXML
    public void addToken() {
        if (tfAddToken.getText().compareTo("") != 0) {
            tokens.add(tfAddToken.getText());
            entrada.setText(entrada.getText() + " " + tfAddToken.getText());
        }
    }

    @FXML
    public void resetaTokens() {
        tokens.clear();
        entrada.setText("");

    }

    @FXML
    public void submit() {
        try {
            Stack<String> temp = new Stack<>();
            temp.add("$");
            while (!tokens.isEmpty()) {
                temp.add(tokens.pop());
            }
            if (tabela.validaPalavra(varInicial.getText(), temp)) {
                log.setText("SUCESSO!");
                log.setTextFill(Color.BLUE);
            } else {
                log.setText("NÃO ACEITO");
                log.setTextFill(Color.RED);
            }

        } catch (Exception e) {
            log.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
