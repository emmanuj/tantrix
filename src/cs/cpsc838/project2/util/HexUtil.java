package cs.cpsc838.project2.util;

/**
 *
 * @author emmanuj
 */
public class HexUtil {

    public Hex3D axialToCubic(double q, double r) {
        double ax = q;
        double az = r;
        double ay = -ax - az;
        return new Hex3D(ax, ay, az);
    }

    public Hex2D pixelToAxial(int x, int y, int radius) {
        double q = (2 / 3.0 * x / radius);
        double r = ((-1 / 3.0 * x + 1 / 3.0 * Math.sqrt(3) * y) / radius);

        return new Hex2D(q, r);
    }

    public Hex3D getNearestHex3D(double x, double y, double z) {
        long rx = Math.round(x);
        long ry = Math.round(y);
        long rz = Math.round(z);

        long x_diff = (long) Math.abs(rx - x);
        long y_diff = (long) Math.abs(ry - y);
        long z_diff = (long) Math.abs(rz - z);

        if (x_diff > y_diff && x_diff > z_diff) {
            rx = -ry - rz;
        } else if (y_diff > z_diff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }
        return new Hex3D(rx, ry, rz);
    }
    public Hex2D cubicToEvenQ(double x, double z){
        double q = x;
        double r = z + (x + ((long)x & 1)) / 2;
        return new Hex2D(q,r);
    }
    //takes evenQ and returns pixel on screen
    public Hex2D hexToPixel(double q, double r, int radius){
        int nx = (int) (radius * 3 / 2.0 * q);
        int ny = (int) (radius * Math.sqrt(3) * (r - 0.5 * ((int) q & 1)));
        return new Hex2D(nx,ny);
    }
    
}
