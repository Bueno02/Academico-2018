package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.professorRepository;
import static config.DAO.cidadeRepository;
import static config.DAO.disciplinaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import model.Cidade;
import model.Disciplina;
import model.Professor;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class ListaDisciplinaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private ProfessorController controllerPai;
    private Professor professor;
    
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TableView tblView;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       

    }

    public void setCadastroController(ProfessorController controllerPai) {
        this.controllerPai = controllerPai;
        professor = controllerPai.professor;   

        tblView.setItems(
                FXCollections.observableList(disciplinaRepository.findByProfessor(professor)));
    }

}
