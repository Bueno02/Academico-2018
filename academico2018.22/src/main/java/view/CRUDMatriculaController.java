package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.Aluno;
import model.Matricula;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author bueno
 */
public class CRUDMatriculaController implements Initializable {

   public Matricula matricula;
   public Aluno aluno;
    
   @FXML
   public TableView<Matricula> tblView;
   
   @FXML
   private Label lblAluno;
   
   @FXML
   private MenuItem mnCtxAltera; 
    
   @FXML
       private void showCRUD() {
        matricula = tblView.getSelectionModel().getSelectedItem();
        String cena = "/fxml/CRUDMatriculaUpdate.fxml";
        XPopOver popOver = new XPopOver(cena, "Alteração de Notas", null);
        CRUDMatriculaUpdateController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mnCtxAltera.visibleProperty().bind(Bindings.isEmpty(tblView.getSelectionModel().getSelectedItems()).not());
        
    }
    
    public void setCadastroController (AlunoController controllerPai){
        aluno = controllerPai.aluno;
        if(controllerPai.aluno.getMatriculas() != null)
        tblView.setItems(FXCollections.observableList(controllerPai.aluno.getMatriculas()));
        lblAluno.setText(controllerPai.aluno.getNome());
    }
    
}
