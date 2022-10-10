package udp_ex6;

import java.io.Serializable;

public class PartialMessage implements Serializable {

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
