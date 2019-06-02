package it.polito.tdp.dizionariograph;

import it.polito.tdp.dizionariograph.model.Model;
import it.polito.tdp.dizionariograph.model.Parola;

import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	Model model; 
	
	public void setModel(Model model) {
	this.model = model; 
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumLettere;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnGeneraGrafo;

    @FXML
    private Button btnTrovaVicini;

    @FXML
    private Button btnGradoMax;

    @FXML
    private TextArea txtResult;

    @FXML
    void generaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int num = Integer.parseInt(txtNumLettere.getText());
    	if(num!= txtParola.getText().length())
    		txtResult.appendText("Il numero inserito non corrisponde al numero di lettere della parola!");
    	else
    		{model.createGraph(num);
    	txtResult.appendText("Il grafo è stato generato!");}
    }

    @FXML
    void trovaGradoMax(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText(Integer.toString(model.findMaxDegree()));
    	
    }

    @FXML
    void trovaVicini(ActionEvent event) {
    	
    	List <Parola> result = model.displayNeighbours(new Parola (txtParola.getText()));
    	for(Parola pp : result) {
    		txtResult.appendText(pp.toString());
    	}
    }

    @FXML
    void initialize() {
        assert txtNumLettere != null : "fx:id=\"txtNumLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGradoMax != null : "fx:id=\"btnGradoMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }
}


