package Modele;

public class ChartData {
    public double Ta;
    public double Tp;
    public double H;
    public double Pr;
    public double Pw;
    public int tick;

    private static int ticks = 0;

    public ChartData(State state) {
        Ta = state.getTa();
        Tp = state.getTp();
        H = state.getH()*30/100;
        Pr = state.getPr();
        Pw = state.getPw()*30/255;
        tick = getTick();
    }

    private int getTick() {
        ChartData.ticks++;
        return ticks;
    }
}