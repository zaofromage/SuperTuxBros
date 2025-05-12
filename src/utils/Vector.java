package utils;

public class Vector {

    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public static double q_rsqrt(double number) {
        float x2 = (float) number * 0.5f;
        float y = (float) number;

        int i = Float.floatToIntBits(y);
        i = 0x5f3759df - (i >> 1);
        y = Float.intBitsToFloat(i);
        y = y * (1.5f - (x2 * y * y));
        return y;
    }

    public void normalize() {
        double q = q_rsqrt(x*x + y*y);
        x *= q;
        y *= q;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
