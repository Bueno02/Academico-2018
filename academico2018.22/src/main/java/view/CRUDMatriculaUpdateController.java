/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.DAO.alunoRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Matricula;

 

public class CRUDMatriculaUpdateController implements Initializable {

    private CRUDMatriculaController controllerPai;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField txtFldNota1;
    
    @FXML
    private TextField txtFldNota2;
    
    @FXML
    private TextField txtFldNotaEx;
    
    @FXML
    private TextField txtFldFaltas;
    
    @FXML
    private Label lblDisciplina;
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
     @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblView.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.matricula.setNota1Sem(Integer.parseInt(txtFldNota1.getText()));
        controllerPai.matricula.setNota2Sem(Integer.parseInt(txtFldNota2.getText()));
        controllerPai.matricula.setNotaEx(Integer.parseInt(txtFldNotaEx.getText()));
        controllerPai.matricula.setFaltas(Integer.parseInt(txtFldFaltas.getText()));
        List<Matricula> lstTemp = new ArrayList();
        try {
                    lstTemp = controllerPai.aluno.getMatriculas();
                    controllerPai.aluno.setMatriculas(lstTemp);
                    alunoRepository.save(controllerPai.aluno);
            controllerPai.tblView.setItems(
                    FXCollections.observableList(controllerPai.aluno.getMatriculas()));
            controllerPai.tblView.refresh();
            controllerPai.tblView.getSelectionModel().clearSelection();
            controllerPai.tblView.getSelectionModel().select(controllerPai.matricula);
            anchorPane.getScene().getWindow().hide();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Alteração de notas");
                alert.setContentText(e.getMessage());
            
            alert.showAndWait();

        }
    }
    
    public void setCadastroController(CRUDMatriculaController controllerPai){
        this.controllerPai = controllerPai;
        txtFldNota1.setText(String.valueOf(controllerPai.matricula.getNota1Sem()));
        txtFldNota2.setText(String.valueOf(controllerPai.matricula.getNota2Sem()));
        txtFldNotaEx.setText(String.valueOf(controllerPai.matricula.getNotaEx()));
        txtFldFaltas.setText(String.valueOf(controllerPai.matricula.getFaltas()));
    }
}
