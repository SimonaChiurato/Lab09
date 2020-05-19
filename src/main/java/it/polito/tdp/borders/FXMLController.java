
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	private boolean Ok=false;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML
    private ComboBox<Country> BoxStati;

    @FXML
    private ComboBox<String> BoxVisite;

    @FXML
    private Button btnVicini;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	Ok=true;
int anno=0;
this.txtAnno.setPromptText("Anno compreso tra 1816 e 2016");
this.txtResult.clear();
    	try {
    		 anno= Integer.parseInt(this.txtAnno.getText());
    		 if(anno<1816 || anno>2016) {
    			 this.txtResult.appendText("L'anno deve essere compreso tra 1816 e 2016");
    			 return;
    		 }
    	}catch( NumberFormatException n) {
    		this.txtResult.appendText("Devi inserire un anno in numeri!");
    		return;
    	}
    	this.model.calcolaConfini(anno);
    	this.txtResult.appendText(this.model.elencoStati());
    	this.txtResult.appendText("Le componenti connesse sono: "+this.model.connesse());
    }

    
    @FXML
    void findVicini(ActionEvent event) {
    	this.txtResult.clear();
    	if(Ok==false) {
    		this.txtResult.appendText("Devi prima selezionare un anno!");
    		return;
    	}
    	Country start= this.BoxStati.getValue();
    	String visite= this.BoxVisite.getValue();
    	if(start==null || visite==null) {
    		this.txtResult.appendText("Devi selezionare i valori nel menù a tendina!");
    		return;
    	}
    	List<Country> result=null;
    	if(visite.equals("BFI")) {
    		 result=this.model.BFI(start);
    	}else if(visite.equals("DFI")) {
    		result= this.model.DFI(start);
    	}else if(visite.equals("Ricorsione")) {
    		result= this.model.ricorsione(start);
    	}else {
    		result= this.model.iterazione(start);
    	}

    	this.txtResult.appendText(result.toString());
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.BoxStati.getItems().addAll(this.model.getStati());
    	this.BoxVisite.getItems().addAll("BFI","DFI","Ricorsione","Iterativo");
    }
}
