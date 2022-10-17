import java.io.Serializable;

public class Msg implements Serializable {
    protected String nickname;
    protected String msg;

    public Msg(String nickname, String msg) {
        this.nickname = nickname;
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMsg() {
        return msg;
    }
}
