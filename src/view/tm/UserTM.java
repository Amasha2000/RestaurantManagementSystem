package view.tm;

import javafx.scene.control.Button;

public class UserTM {
    private String userId;
    private String userName;
    private String password;
    private Button btn;

    public UserTM() {
    }

    public UserTM(String userId, String userName, String password, Button btn) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setPassword(password);
        this.setBtn(btn);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
