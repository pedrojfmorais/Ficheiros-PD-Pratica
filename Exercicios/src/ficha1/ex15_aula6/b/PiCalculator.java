package ficha1.ex15_aula6.b;

public class PiCalculator
{
    public static double getPartialPiValue(int myId, int nWorkers, long nIntervals)
    {
        long i;
        double dX, xi, result;

        if(nIntervals < 1 || myId < 1 || myId > nWorkers)
            return 0.0;

        dX = 1.0 / nIntervals;
        result = 0;

        for (i = myId - 1 ; i < nIntervals; i += nWorkers)
        {
            xi = dX * (i + 0.5);
            result += (4.0 / (1.0 + xi * xi));
        }

        result *= dX;
        return result;
    }

    public static void main(String[] args)
    {
        double pi1, pi2, pi3;

        pi1 = getPartialPiValue(1, 1, 100000);
        System.out.println("PI (1 worker) = " + pi1);

        pi1 = getPartialPiValue(1, 2, 100000);
        pi2 = getPartialPiValue(2, 2, 100000);
        System.out.println("PI (2 workers) = " + pi1 + " + "
                + pi2 + " = " + (pi1 + pi2));

        pi1 = getPartialPiValue(1, 3, 100000);
        pi2 = getPartialPiValue(2, 3, 100000);
        pi3 = getPartialPiValue(3, 3, 100000);
        System.out.println("PI (3 workers) = " + pi1 + " + "
                + pi2 + " + " + pi3 + " = " + (pi1 + pi2 + pi3));
    }
}
