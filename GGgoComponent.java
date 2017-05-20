package gui_sandbox;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


public class GGgoComponent extends JComponent
{
    //Constructor:컴포넌트 인자로 받아 컴포넌트 add, border설정, 리스너 부착
    public GGgoComponent(Component comp)
    {
        this(comp, new ResizableBorder(8));
    }

    public GGgoComponent(Component comp, ResizableBorder border)
    {
        setLayout(new BorderLayout());
        add(comp);
        setBorder(border);
        addMouseListener(resizeListener);
        addMouseMotionListener(resizeListener);
    }
    //
    private void resize()
    {
        if (getParent() != null)
        {
            ((JComponent) getParent()).revalidate();
        }
    }

    MouseInputListener resizeListener = new MouseInputAdapter()
    {
        
        @Override
        public void mouseMoved(MouseEvent me)
        {
            if (hasFocus())
            {
                ResizableBorder border = (ResizableBorder) getBorder();
                setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
            }
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {
            setCursor(Cursor.getDefaultCursor());
        }

        private int cursor;
        private Point initPoint = null;
        /*
        */
        @Override
        public void mousePressed(MouseEvent me)
        {
            ResizableBorder border = (ResizableBorder) getBorder();
            cursor = border.getCursor(me);
            initPoint = me.getPoint();
            requestFocusInWindow();
            repaint();
        }
        //드래그시 
        @Override
        public void mouseDragged(MouseEvent me)
        {
            if (initPoint != null)
            {
                int x = getX();
                int y = getY();
                int w = getWidth();
                int h = getHeight();

                int dx = me.getX() - initPoint.x;
                int dy = me.getY() - initPoint.y;

                switch (cursor)
                {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < 50))
                        {
                            setBounds(x, y + dy, w, h - dy);
                            resize();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < 50))
                        {
                            setBounds(x, y, w, h + dy);
                            initPoint = me.getPoint();
                            resize();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < 50))
                        {
                            setBounds(x + dx, y, w - dx, h);
                            resize();
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < 50))
                        {
                            setBounds(x, y, w + dx, h);
                            initPoint = me.getPoint();
                            resize();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < 50) && !(h - dy < 50))
                        {
                            setBounds(x + dx, y + dy, w - dx, h - dy);
                            resize();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < 50) && !(h - dy < 50))
                        {
                            setBounds(x, y + dy, w + dx, h - dy);
                            initPoint = new Point(me.getX(), initPoint.y);
                            resize();
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < 50) && !(h + dy < 50))
                        {
                            setBounds(x + dx, y, w - dx, h + dy);
                            initPoint = new Point(initPoint.x, me.getY());
                            resize();
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < 50) && !(h + dy < 50))
                        {
                            setBounds(x, y, w + dx, h + dy);
                            initPoint = me.getPoint();
                            resize();
                        }
                        break;

                    case Cursor.MOVE_CURSOR:
                        Rectangle bounds = getBounds();
                        bounds.translate(dx, dy);
                        setBounds(bounds);
                        resize();
                }
                setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }
        //드래그 끝나면 initPoint 초기화
        @Override
        public void mouseReleased(MouseEvent mouseEvent)
        {
            initPoint = null;
        }
    };
}
