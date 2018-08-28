package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.disciplinaRepository;
import static config.DAO.professorRepository;
import static config.DAO.professorRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import model.Disciplina;
import model.Professor;
import org.springframework.data.domain.Sort;
import repository.DisciplinaRepository;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class ProfessorController implements Initializable {

  /**
     * Initializes the controller class.
     */
    @FXML
    public TableView<Professor> tblView;
    public char acao;
    public Professor professor;
    
    private ListaDisciplinaController controllerDis;
    
    @FXML
    private MaterialDesignIconView btnIncluir;
    @FXML
    private MaterialDesignIconView btnAlterar;
    @FXML
    private MaterialDesignIconView btnExcluir;
    @FXML
    private MaterialDesignIconView btnListar;

    @FXML
    private void acIncluir() {
        acao = INCLUIR;
        professor = new Professor();

        showCRUD();

    }

    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        if(tblView.getSelectionModel().getSelectedItem()!=null){
            professor = tblView.getSelectionModel().getSelectedItem();
            showCRUD();
        }
    }
    @FXML
    private void acExcluir() {
        acao = EXCLUIR;
        if (tblView.getSelectionModel().getSelectedItem() != null) {
            professor = tblView.getSelectionModel().getSelectedItem();
            if (disciplinaRepository.countByProfessor(professor) != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Professor Vinculado");
                alert.setContentText("Profesor Vinculado a Disciplina");
                alert.showAndWait();
                return;
            }
            showCRUD();
        }
    }
    
    @FXML
    private void acListar(){
        String cena = "/fxml/ListaDisciplinas.fxml";
        professor = tblView.getSelectionModel().getSelectedItem();
         
        XPopOver popOver = new XPopOver(cena,"Lista de Disciplinas", btnListar);
       ListaDisciplinaController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
       
       
        
    }

    private void showCRUD() {
        String cena = "/fxml/CRUDProfessor.fxml";
        XPopOver popOver = null;

        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Professor", btnIncluir);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Professor", btnAlterar);
                break;
            case EXCLUIR:
                popOver = new XPopOver(cena, "Exclusão de Professor", btnExcluir);
                break;
        }
        CRUDProfessorController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblView.setItems(
                FXCollections.observableList(professorRepository.findAll(new Sort(new Sort.Order("nome")))));
        
        btnAlterar.visibleProperty().bind(Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        btnExcluir.visibleProperty().bind(btnAlterar.visibleProperty());
        btnListar.visibleProperty().bind(btnAlterar.visibleProperty());
        
    }

}
