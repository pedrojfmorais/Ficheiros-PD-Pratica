package ficha1.ex15_aula6.b;

import java.io.Serializable;

public class MSG_Workers implements Serializable {
    private final int nIntervalos;
    private final int nWorkers;
    private final int indiceWorker;

    public MSG_Workers(int nIntervalos, int nWorkers, int indiceWorker) {
        this.nIntervalos = nIntervalos;
        this.nWorkers = nWorkers;
        this.indiceWorker = indiceWorker;
    }

    public int getnIntervalos() {
        return nIntervalos;
    }

    public int getnWorkers() {
        return nWorkers;
    }

    public int getIndiceWorker() {
        return indiceWorker;
    }
}
