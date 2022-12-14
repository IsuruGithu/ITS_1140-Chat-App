package controller.client;

import controller.Data;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ConnectionUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author : Isuru Lakmal G K A
 * @since : 0.1.0
 **/

public class UserFormController {
    public TextField txtMsgInput;
    public TextArea txtMsgDisplay;

    public String userName;
    PrintWriter printWriter;
    Socket socket = null;

    public void btnClose(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void btnMinimize(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void btnSend(ActionEvent actionEvent) throws IOException {
        printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(userName + " : " + txtMsgInput.getText());
        printWriter.flush();
    }

    public void initialize() throws IOException {
        userName = Data.userName;
        System.out.println("userName is : " + userName);
        socket = new Socket(ConnectionUtil.host, ConnectionUtil.port);
        txtMsgDisplay.appendText("Connect. \n");
        printWriter = new PrintWriter(socket.getOutputStream());
        TaskReadThread task = new TaskReadThread(socket, this);
        Thread thread = new Thread(task);
        thread.start();

    }

}
