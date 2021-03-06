package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.alunoRepository;
import static config.DAO.professorRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import model.Aluno;
import model.Disciplina;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class AlunoController implements Initializable {

  /**
     * Initializes the controller class.
     */
    @FXML
    public TableView<Aluno> tblViewAluno;
    public char acao;
    public Aluno aluno;
    @FXML
    private MaterialDesignIconView btnIncluir;
    @FXML
    private MaterialDesignIconView btnAlterar;
    @FXML
    private MaterialDesignIconView btnExcluir;
    @FXML
    private MaterialDesignIconView btnFiltro;
    @FXML
    private MaterialDesignIconView btnLimpar;
    @FXML
    private MenuItem mnCtxAltera;
    @FXML
    private MenuItem mnCtxExcluir;
    @FXML
    private TextField txtFldFiltro;

    @FXML
    private void tblViewAlunoClick (Event event){
        MouseEvent me = null;
        if(event.getEventType() == MOUSE_CLICKED){
            me = (MouseEvent) event;
            if(me.getClickCount() == 2){
                aluno = tblViewAluno.getSelectionModel().getSelectedItem();
                String cena = "/fxml/CRUDMatricula.fxml";
                 XPopOver popOver = new XPopOver(cena,"Lista de Disciplinas", null);
                CRUDMatriculaController controllerFilho = popOver.getLoader().getController();
                controllerFilho.setCadastroController(this);
    }
    }
    }
    
    @FXML
    private void acIncluir() {
        acao = INCLUIR;
        aluno = new Aluno();

        showCRUD();

    }

    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        if(tblViewAluno.getSelectionModel().getSelectedItem()!=null){
            aluno = tblViewAluno.getSelectionModel().getSelectedItem();
            showCRUD();
        }
    }
    @FXML
    private void acExcluir() {
        acao = EXCLUIR;
        if(tblViewAluno.getSelectionModel().getSelectedItem()!=null){
            aluno = tblViewAluno.getSelectionModel().getSelectedItem();
            showCRUD();
        }
    }
    
    @FXML
    private void acLimpar() {
        txtFldFiltro.setText("");
        tblViewAluno.setItems(
                FXCollections.observableList(alunoRepository.findAll(new Sort(new Sort.Order("nome")))));
    }
    

    @FXML
    private void acFiltrar() {
        tblViewAluno.setItems(FXCollections.observableList(alunoRepository.findByNomeLikeIgnoreCaseOrEmailLikeIgnoreCase(
                txtFldFiltro.getText(),txtFldFiltro.getText())));
    }

    private void showCRUD() {
        String cena = "/fxml/CRUDAluno.fxml";
        XPopOver popOver = null;

        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Aluno", btnIncluir);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Aluno", btnAlterar);
                break;
            case EXCLUIR:
                popOver = new XPopOver(cena, "Exclusão de Aluno", btnExcluir);
                break;
        }
        CRUDAlunoController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        tblViewAluno.setItems(FXCollections.observableList(alunoRepository.findAll(new Sort(new Sort.Order("nome")))));
      
        
        btnAlterar.visibleProperty().bind(Bindings.isEmpty((tblViewAluno.getSelectionModel().getSelectedItems())).not());
        btnExcluir.visibleProperty().bind(btnAlterar.visibleProperty());
        btnFiltro.disableProperty().bind(txtFldFiltro.textProperty().isEmpty());
        btnLimpar.visibleProperty().bind(txtFldFiltro.textProperty().isEmpty().not());
        mnCtxAltera.visibleProperty().bind(btnAlterar.visibleProperty());
        mnCtxExcluir.visibleProperty().bind(btnAlterar.visibleProperty());
        
    }

}
