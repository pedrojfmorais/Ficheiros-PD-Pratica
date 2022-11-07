package ficha1.ex16_aula7;

import java.io.Serial;
import java.io.Serializable;

public class Hora implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    private final int horas;
    private final int minutos;
    private final int segundos;

    public Hora(int horas, int minutos, int segundos) {
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
    }

    public int getHoras() {
        return horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    @Override
    public String toString() {
        return horas + ":" + minutos + ":" + segundos;
    }
}
