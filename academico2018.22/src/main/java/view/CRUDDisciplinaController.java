/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.disciplinaRepository;
import static config.DAO.professorRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Professor;
import model.Disciplina;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author idomar
 */
public class CRUDDisciplinaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private DisciplinaController controllerPai;


    @FXML
    private TextField txtFldCodigo;

    @FXML
    private TextField txtFldNome;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnConfirma;

    @FXML
    private ComboBox cmbProfessor;

    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblView.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.disciplina.setCodigo(txtFldCodigo.getText());
        controllerPai.disciplina.setNome(txtFldNome.getText());
        controllerPai.disciplina.setProfessor((Professor) cmbProfessor.getSelectionModel().getSelectedItem());
        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    disciplinaRepository.insert(controllerPai.disciplina);
                    break;
                case ALTERAR:
                    disciplinaRepository.save(controllerPai.disciplina);
                    break;
                case EXCLUIR:
                    disciplinaRepository.delete(controllerPai.disciplina);
                    break;
            }
            controllerPai.tblView.setItems(
                    FXCollections.observableList(disciplinaRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            controllerPai.tblView.refresh();
            controllerPai.tblView.getSelectionModel().clearSelection();
            controllerPai.tblView.getSelectionModel().select(controllerPai.disciplina);
            anchorPane.getScene().getWindow().hide();
//
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Disciplina");
            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Código já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnConfirma.disableProperty().bind(txtFldCodigo.textProperty().isEmpty().
                or(txtFldNome.textProperty().isEmpty()));

    }

    public void setCadastroController(DisciplinaController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldCodigo.setText(controllerPai.disciplina.getCodigo());
        txtFldNome.setText(controllerPai.disciplina.getNome());

        cmbProfessor.setItems(FXCollections.observableList(
                professorRepository.findAll(new Sort(new Sort.Order("nome")))));

        if (controllerPai.acao != INCLUIR) {
            cmbProfessor.getSelectionModel().select(controllerPai.disciplina.getProfessor());
        }

        txtFldCodigo.setDisable(controllerPai.acao == EXCLUIR);
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);

    }

}
