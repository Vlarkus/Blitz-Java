package blitz.ui.main.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import org.checkerframework.checker.optional.qual.PolyPresent;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;
import blitz.servises.CartesianCoordinate;
import blitz.servises.FieldImage;
import blitz.servises.Utils;
import blitz.ui.main.pointers.BezierPointer;
import blitz.ui.main.pointers.ControlPointer;
import blitz.ui.main.pointers.HelperLine;
import blitz.ui.main.pointers.HelperPointer;
import blitz.ui.main.pointers.Pointer.State;
import blitz.ui.main.tools.Tool;
import blitz.ui.main.tools.Tool.Tools;
import blitz.ui.main.tools.ToolListener;

public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener, ActiveListener, ToolListener{

    private int mousePreviousX, mousePreviousY;
    private BufferedImage field;

    private JScrollPane scrollPane;

    private ArrayList<Trajectory> visibleTrajectories;
    private ArrayList<ControlPointer> controlPointers;
    private ArrayList<HelperPointer> helperPointers;
    private ArrayList<HelperLine> helperLines;
    private ArrayList<BezierPointer> bezierPointers;

    public CanvasPanel(){
        
        setBackground(MainFrameConfig.CANVAS_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.CANVAS_PANEL_PREFFERED_DIMENSION);
        setLayout(null);

        visibleTrajectories = new ArrayList<Trajectory>();
        controlPointers = new ArrayList<ControlPointer>();
        bezierPointers = new ArrayList<BezierPointer>();
        helperPointers = new ArrayList<HelperPointer>();

        try {
            setFieldImage(new FieldImage(MainFrameConfig.PATH_TO_DEFAULT_FIELD));
        } catch(Exception e){
            setFieldImage(null);
        }

        addMouseListener(this);
        addMouseMotionListener(this);
        Active.addActiveListener(this);
        Tool.addToolListener(this);

    }

    private void clearControlPointers(){
        controlPointers = new ArrayList<ControlPointer>();
    }

    private void clearBezierPointers(){
        bezierPointers = new ArrayList<BezierPointer>();
    }

    private void clearHelperPointers(){
        helperPointers = new ArrayList<HelperPointer>();
    }

    private void clearHelperLines(){
        helperLines = new ArrayList<HelperLine>();
    }

    private void populateControlPointers(){
        
        clearControlPointers();

        for (Trajectory tr : visibleTrajectories) {
            for (ControlPoint cp : tr.getAllControlPoints()) {

                CartesianCoordinate coordinate = convertFieldToScreenCoordinates(cp.getPosition());
                int x = (int) coordinate.getX();
                int y = (int) coordinate.getY();
                ControlPointer pointer = new ControlPointer(x, y, cp);
                if(cp.equals(Active.getActiveControlPoint())){
                    pointer.setState(State.SELECTED);
                }
                controlPointers.add(pointer);

            }
        }



    }

    private void populateBezierPointers(){

        clearBezierPointers();

        for (Trajectory tr : visibleTrajectories) {
            for (ControlPoint cp : tr.getAllControlPoints()) {
                
                ArrayList<CartesianCoordinate> bezierCoordinates = tr.calculateBezierCurveFrom(cp);
                if(bezierCoordinates == null){
                    continue;
                }

                for (CartesianCoordinate cartesianCoordinate : bezierCoordinates) {
                    CartesianCoordinate coordinate = convertFieldToScreenCoordinates(cartesianCoordinate);
                    int x = (int) coordinate.getX();
                    int y = (int) coordinate.getY();
                    bezierPointers.add(new BezierPointer(x, y, cp));
                }


            }
        }

    }

    private void populateHelperPointers() {
        clearHelperPointers();
        clearHelperLines();
    
        for (ControlPointer p : controlPointers) {
            if (p.getState() == State.SELECTED) {
                ControlPoint cp = p.getRelatedControlPoint();
    
                CartesianCoordinate helperStartCoord = convertFieldToScreenCoordinates(cp.getAbsStartHelperPos());
                HelperPointer helperStartPointer = new HelperPointer((int) helperStartCoord.getX(), (int) helperStartCoord.getY(), cp, true);
                helperPointers.add(helperStartPointer);
                createHelperLine(helperStartPointer);
    
                CartesianCoordinate endStartCoord = convertFieldToScreenCoordinates(cp.getAbsEndHelperPos());
                HelperPointer helperEndPointer = new HelperPointer((int) endStartCoord.getX(), (int) endStartCoord.getY(), cp, false);
                helperPointers.add(helperEndPointer);
                createHelperLine(helperEndPointer);
            }
        }
    }
    
    private void createHelperLine(HelperPointer p) {
        int x1 = p.getCenterX();
        int y1 = p.getCenterY();
        ControlPoint cp = p.getRelatedControlPoint();
        CartesianCoordinate c = convertFieldToScreenCoordinates(new CartesianCoordinate(cp.getX(), cp.getY()));
        int x2 = (int) c.getX();
        int y2 = (int) c.getY();
        HelperLine line = new HelperLine(x1, y1, x2, y2);
    
        helperLines.add(line);
    }

    private void addAllComponents() {
        removeAll();
    
        for (HelperPointer p : helperPointers) {
            add(p);
        }
    
        for (ControlPointer p : controlPointers) {
            add(p);
        }

        for (HelperLine l : helperLines) {
            add(l);
        }
    
        
    
        for (BezierPointer p : bezierPointers) {
            add(p);
        }
        repaint();
    }

    public CartesianCoordinate convertFieldToScreenCoordinates(CartesianCoordinate c) {
        double x = c.getX() + MainFrameConfig.CANVAS_PANEL_X_OFFSET;
        double y = -c.getY() + MainFrameConfig.CANVAS_PANEL_Y_OFFSET;
        return new CartesianCoordinate((int) x, (int) y);
    }
    
    public CartesianCoordinate convertScreenToFieldCoordinates(CartesianCoordinate c) {
        double x = c.getX() - MainFrameConfig.CANVAS_PANEL_X_OFFSET;
        double y = -(c.getY() - MainFrameConfig.CANVAS_PANEL_Y_OFFSET);
        return new CartesianCoordinate(x, y);
    }

    public ArrayList<Trajectory> getVisibleTrajectories() {
        return visibleTrajectories;
    }

    public void setVisibleTrajectories(ArrayList<Trajectory> visibleTrajectories) {
        this.visibleTrajectories = visibleTrajectories;
        renderVisibleTrajectories();
        //TODO: Rerender visibleTrajectories.
    }

    public void renderVisibleTrajectories(){
        populateControlPointers();
        populateBezierPointers();
        populateHelperPointers();
        addAllComponents();
        repaint();
    }

    private ArrayList<ControlPointer> getControlPointers() {
        return controlPointers;
    }

    private void setControlPointers(ArrayList<ControlPointer> controlPointers) {
        this.controlPointers = controlPointers;
    }

    private ArrayList<HelperPointer> getHelperPointers() {
        return helperPointers;
    }

    private void setHelperPointers(ArrayList<HelperPointer> helperPointers) {
        this.helperPointers = helperPointers;
    }

    private ArrayList<BezierPointer> getBezierPointers() {
        return bezierPointers;
    }

    private void setBezierPointers(ArrayList<BezierPointer> bezierPointers) {
        this.bezierPointers = bezierPointers;
    }

    public void setScrollPane(JScrollPane p){
        scrollPane = p;
    }

    public void setImageToRender(String path) {
        try {
            this.field = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mousePreviousX = e.getX();
        mousePreviousY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Tool.getSelectedTool()) {
            case MOVE:
                break;

            case ADD:
                break;
            
            case INSERT:
                break;
            
            case REMOVE:
                break;
            
            case CUT:
                break;
            
            case SHOW_ROBOT:
                break;
            
            case MERGE:
                break;
            
            case RENDER_ALL:
                break;

            case PAN:
                calculatePanning(e);
                break;

            case EDIT_TIME:
                break;
        }
    }

    private void calculatePanning(MouseEvent e){
        JViewport viewport = scrollPane.getViewport();
        if (viewport != null) {
            int dx = e.getX() - mousePreviousX;
            int dy = e.getY() - mousePreviousY;
            Point viewPos = viewport.getViewPosition();
            viewPos.translate(-dx, -dy);

            int maxX = Math.max(0, getWidth() - viewport.getWidth());
            int maxY = Math.max(0, getHeight() - viewport.getHeight());

            if (viewPos.x < 0) viewPos.x = 0;
            if (viewPos.x > maxX) viewPos.x = maxX;
            if (viewPos.y < 0) viewPos.y = 0;
            if (viewPos.y > maxY) viewPos.y = maxY;

            viewport.setViewPosition(viewPos);
        }
    }
    

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setFieldImage(FieldImage fieldImage) {
        try {
            if(fieldImage == null){
                field = null;
                return;
            }
            field = ImageIO.read(new File(fieldImage.getPath()));
            resizeFieldImage(600, 600);
            repaint();
        } catch (IOException e) {

        }
    }

    private void resizeFieldImage(int width, int height){
        field = Utils.resizeImage(field, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the image at the center of the panel
        if (field != null) {
            int imageX = (getWidth() - field.getWidth()) / 2;
            int imageY = (getHeight() - field.getHeight()) / 2;
            g.drawImage(field, imageX, imageY, this);
        }
    }

    public void activeTrajectoryChanged(Trajectory tr) {

    }

    public void activeControlPointChanged(ControlPoint cp) {
        renderVisibleTrajectories();
    }

    @Override
    public void selectedToolChanged(Tools tool) {
        switch (tool) {
            case RENDER_ALL:
                renderVisibleTrajectories();
                break;
        }
    }

    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        for (BezierPointer bezierPointer : bezierPointers) {
            if(bezierPointer.getRelatedControlPoint().equals(cp)){
                bezierPointers.remove(bezierPointer);
            }
        }
    }
    
}
