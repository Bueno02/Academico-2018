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
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import model.Cidade;
import org.controlsfx.control.PopOver;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class CRUDProfessorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private ProfessorController controllerPai;
    public char acao;
    public Cidade cidade; 

    @FXML
    private TextField txtFldCpf;

    @FXML
    private TextField txtFldNome;
    
    @FXML
    private TextField txtFldEmail;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button btnConfirma;

    @FXML
    public ComboBox cmbCidade;
    
    @FXML
    private MaterialDesignIconView btnIncluir;
    
    @FXML
    private MaterialDesignIconView btnAlterar;
    
    
    @FXML
    private void acIncluir(){
        acao = INCLUIR;
        cidade = new Cidade();

        showCRUD();
        
    }
    
    @FXML
    private void acAlterar(){
        acao = ALTERAR;
        cidade = (Cidade) cmbCidade.getSelectionModel().getSelectedItem();

        showCRUD();
        
    }
    
        private void showCRUD() {
        String cena = "/fxml/CRUDCidade.fxml";
        XPopOver popOver = null;

        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Cidade", btnIncluir,PopOver.ArrowLocation.TOP_RIGHT);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Cidade", btnAlterar,PopOver.ArrowLocation.TOP_RIGHT);
                break;
        }
        CRUDCidadeController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }
    
    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblView.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.professor.setCpf(txtFldCpf.getText());
        controllerPai.professor.setNome(txtFldNome.getText());
        controllerPai.professor.setEmail(txtFldEmail.getText());
        controllerPai.professor.setCidade((Cidade) cmbCidade.getSelectionModel().getSelectedItem());
        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    professorRepository.insert(controllerPai.professor);
                    break;
                case ALTERAR:
                    professorRepository.save(controllerPai.professor);
                    break;
                case EXCLUIR:
                    professorRepository.delete(controllerPai.professor);
                    break;
            }
            controllerPai.tblView.setItems(
                    FXCollections.observableList(professorRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            controllerPai.tblView.refresh();
            controllerPai.tblView.getSelectionModel().clearSelection();
            controllerPai.tblView.getSelectionModel().select(controllerPai.professor);
            anchorPane.getScene().getWindow().hide();
//
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cpf de Disciplina");
            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Cpf já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnConfirma.disableProperty().bind(txtFldCpf.textProperty().isEmpty().
                or(txtFldNome.textProperty().isEmpty()).or(txtFldEmail.textProperty().isEmpty()) );
        btnAlterar.visibleProperty().bind(cmbCidade.getSelectionModel().selectedItemProperty().isNotNull());
    }

    public void setCadastroController(ProfessorController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldCpf.setText(controllerPai.professor.getCpf());
        txtFldNome.setText(controllerPai.professor.getNome());
        txtFldEmail.setText(controllerPai.professor.getEmail());

        cmbCidade.setItems(FXCollections.observableList(
                cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));

        if (controllerPai.acao != INCLUIR) {
            cmbCidade.getSelectionModel().select(controllerPai.professor.getCidade());
        }

        txtFldCpf.setDisable(controllerPai.acao == EXCLUIR);
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldEmail.setDisable(controllerPai.acao == EXCLUIR);

    }

}
