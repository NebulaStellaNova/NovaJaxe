package NovaJaxe.novahandlers;

public class NovaCamera {
    public double x = 0;
    public double y = 0;
    public double zoom = 1;
    public boolean visible = true;
    public NovaCamera(double X, double Y) {
        x = X;
        y = Y;
    }
    public void reset() {
        x = 0;
        y = 0;
        zoom = 1;
    }
}
