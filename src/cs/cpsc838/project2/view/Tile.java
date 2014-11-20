package cs.cpsc838.project2.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.JComponent;

/**
 *
 * @author emmanuj
 */
public class Tile extends JComponent implements MouseListener, MouseMotionListener {

    private final int centerX = TileConstants.CENTER_X;
    private final int centerY = TileConstants.CENTER_Y;
    private final int radius = TileConstants.RADIUS;
    private static Tile lastSelected = null;
    private Point location;

    protected MouseEvent pressed;
    private double rotateAng = 0;

    private final Color colors[];
    private final String tileId;
    private final TilePattern pattern;
    private Polygon p = new Polygon();
    protected boolean drawBorder = false;
    private boolean isDragged;
    private Point originalLocation;

    public Tile(String tileId, TilePattern pattern, Color... colors) {
        this.colors = colors;
        this.tileId = tileId;
        this.pattern = pattern;
        setSize(400, 400);
        addMouseListener(Tile.this);
        addMouseMotionListener(Tile.this);
    }

    public String getTileId() {
        return tileId;
    }

    @Override
    public void paintComponent(Graphics g) {
        draw(g);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.rotate(rotateAng, centerX, centerY);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        rh.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
        g2d.setRenderingHints(rh);

        Polygon p = new Polygon();
        for (int i = 0; i < 6; i++) {
            p.addPoint((int) (centerX + radius * Math.cos(i * 2 * Math.PI / 6)), (int) (centerY + radius * Math.sin(i * 2 * Math.PI / 6)));
        }

        //fill polygon and set to clip lines outside the area.
        if (drawBorder) {
            g2d.setColor(Color.cyan);
            BasicStroke bs = new BasicStroke(4.0f);
            g2d.setStroke(bs);
            g2d.drawPolygon(p);
        }
        g2d.setColor(Color.BLACK);
        g2d.fillPolygon(p.xpoints, p.ypoints, p.npoints);
        g2d.setClip(p);

        //set stroke size
        BasicStroke bs = new BasicStroke((float) (radius / 4.0));
        g2d.setStroke(bs);

        //draw the line segments depending on the pattern
        //arcs
        Arc2D.Double arc_v0 = new Arc2D.Double(p.xpoints[0] - centerX / 2, p.ypoints[0] / 2, centerX, centerY, 0, 360, Arc2D.CHORD);
        Arc2D.Double arc_v2 = new Arc2D.Double(p.xpoints[3], p.ypoints[2] - centerY / 2, centerX, centerY, 0, 360, Arc2D.CHORD);
        Arc2D.Double arc_v4 = new Arc2D.Double(p.xpoints[3], p.ypoints[4] - centerY / 2, centerX, centerY, 0, 360, Arc2D.CHORD);
        Arc2D.Double arc_v3 = new Arc2D.Double(p.xpoints[3] - centerX / 2, p.ypoints[3] / 2, centerX, centerY, 0, 360, Arc2D.CHORD);
        //straight line
        Line2D.Double straight = new Line2D.Double((p.xpoints[5] - p.xpoints[4]), p.ypoints[5], (p.xpoints[1] - p.xpoints[2]), p.ypoints[1]);

        //Quads
        QuadCurve2D.Double quad_N = new QuadCurve2D.Double(p.xpoints[3], p.ypoints[4] + centerY / 3, centerX, centerY, p.xpoints[0], p.ypoints[5] + centerY / 3);
        QuadCurve2D.Double quad_S = new QuadCurve2D.Double(p.xpoints[3], p.ypoints[2] - centerY / 3, centerX, centerY, p.xpoints[0], p.ypoints[1] - centerY / 3);
        QuadCurve2D.Double quad_NE = new QuadCurve2D.Double(2 * p.xpoints[4], p.ypoints[4] - centerY / 2, centerX, centerY, p.xpoints[0], p.ypoints[1] - centerY / 3);

        switch (pattern) {
            case ALL_CORNERS:
                g2d.setColor(colors[0]);
                g2d.draw(arc_v0);
                g2d.setColor(colors[1]);
                g2d.draw(arc_v2);
                g2d.setColor(colors[2]);
                g2d.draw(arc_v4);
                break;
            case STRAIGHTS_TWO_BENDS:
                g2d.setColor(colors[0]);
                g2d.draw(quad_N);
                g2d.setColor(colors[1]);
                g2d.draw(straight);
                g2d.setColor(colors[2]);
                g2d.draw(quad_S);
                break;
            case CORNER_TWO_BENDS:
                g2d.setColor(colors[0]);
                g2d.draw(arc_v2);
                g2d.setColor(colors[1]);
                g2d.draw(quad_N);
                g2d.setColor(colors[2]);
                g2d.draw(quad_NE);
                break;
            case STRAIGHT_TWO_CORNERS:
                g2d.setColor(colors[0]);
                g2d.draw(arc_v0);
                g2d.setColor(colors[1]);
                g2d.draw(straight);
                g2d.setColor(colors[2]);
                g2d.draw(arc_v3);
                break;
        }

        g2d.setClip(null);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e;
        originalLocation = getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int w = TileConstants.CENTER_X * 2;
        double h = (int) Math.ceil((Math.sqrt(3) / 2) * w);
        int offset = (int) h / 2;

        int startX = 10;
        int startY = offset + 10;

        if (isDragged) {
            location = getLocation(location);
            int x = location.x - pressed.getX() + e.getX();
            int y = location.y - pressed.getY() + e.getY();
            if ((x > 0 && x < getParent().getWidth() - 50) && (y > 0 && y < getParent().getHeight() - 50)) {

                //get cartessian coordinate
                double q = (2 / 3.0 * x / radius);
                double r = ((-1 / 3.0 * x + 1 / 3.0 * Math.sqrt(3) * y) / radius);

                //convert axial coordinate to 3D coordinate
                double ax = q;
                double az = r;
                double ay = -ax - az;

                //round to the nearest hex
                long rx = Math.round(ax);
                long ry = Math.round(ay);
                long rz = Math.round(az);

                long x_diff = (long) Math.abs(rx - ax);
                long y_diff = (long) Math.abs(ry - ay);
                long z_diff = (long) Math.abs(rz - az);

                if (x_diff > y_diff && x_diff > z_diff) {
                    rx = -ry - rz;
                } else if (y_diff > z_diff) {
                    ry = -rx - rz;
                } else {
                    rz = -rx - ry;
                }

                //convert to even-q coordinate
                q = rx;
                r = rz + (rx + (rx & 1)) / 2;

            //System.out.println(r + " : " + q);
                //convert back to pixel
                int nx = (int) (radius * 3 / 2.0 * q);
                int ny = (int) (radius * Math.sqrt(3) * (r - 0.5 * ((int) q & 1)));

            //System.out.println("nx= " + nx + " : ny = " + ny);
                //System.out.println("x= " + x + " : y = " + y);
                Component c = getParent().getComponentAt(nx, ny);
                System.out.println(c);

                setLocation(nx, ny);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        location = getLocation(location);
        int x = location.x - pressed.getX() + e.getX();
        int y = location.y - pressed.getY() + e.getY();
        if ((x > 0 && x < getParent().getWidth() - 50) && (y > 0 && y < getParent().getHeight() - 50)) {
            setLocation(x, y);
            repaint();
            isDragged = true;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        if (clickCount == 2) {
            rotateAng += (Math.PI / 3);
            drawBorder = false;
            lastSelected = null;
            repaint();
        }
        if (clickCount == 1) {
            if (lastSelected != this) {
                if (lastSelected != null) {
                    Point curr = this.getLocation();
                    Point newLoc = lastSelected.getLocation();

                    this.setLocation(newLoc);
                    lastSelected.setLocation(curr);

                    lastSelected.drawBorder = false;
                    drawBorder = false;
                    lastSelected.repaint();
                    lastSelected = null;
                }
            }
            if (drawBorder == true) {
                drawBorder = false;
                lastSelected = null;
            } else {
                drawBorder = true;
                lastSelected = this;
            }
            repaint();

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public String toString() {
        return "Tile: [id: " + tileId + " type: " + pattern + " colors: " + colors[0] + "," + colors[1] + "," + colors[2] + " ]";
    }

}
