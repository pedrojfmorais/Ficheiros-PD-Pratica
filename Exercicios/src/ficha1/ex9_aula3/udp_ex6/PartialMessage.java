package ficha1.ex9_aula3.udp_ex6;

import java.io.Serial;
import java.io.Serializable;

public class PartialMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private byte[] msg;
    private int msgSize;

    public int getMsgSize() {
        return msgSize;
    }

    public void setMsgSize(int msgSize) {
        this.msgSize = msgSize;
    }

    private boolean lastPacket;

    public PartialMessage() {}

    public byte[] getMsg() {
        return msg;
    }

    public void setMsg(byte[] msg) {
        this.msg = msg;
    }

    public boolean isLastPacket() {
        return lastPacket;
    }

    public void setLastPacket(boolean lastPacket) {
        this.lastPacket = lastPacket;
    }
}
