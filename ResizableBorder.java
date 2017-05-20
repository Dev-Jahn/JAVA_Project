package gui_sandbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ResizableBorder implements Border
{

    private int size = 8;

    int locations[] =
    {
        SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
        SwingConstants.EAST, SwingConstants.NORTH_WEST, SwingConstants.NORTH_EAST,
        SwingConstants.SOUTH_WEST, SwingConstants.SOUTH_EAST
    };

    int cursors[] =
    {
        Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
        Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
        Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
    };

	public ResizableBorder() {}
    public ResizableBorder(int size)
    {
        this.size = size;
    }

    @Override
    public Insets getBorderInsets(Component component)
    {
        return new Insets(size, size, size, size);
    }

    @Override
    public boolean isBorderOpaque()
    {
        return false;
    }

    @Override
    public void paintBorder(Component component, Graphics g, int x, int y, int w, int h)
    {
        g.setColor(Color.black);
        g.drawRect(x + size / 2, y + size / 2, w - size, h - size);

        if (component.isFocusOwner())
        {
            for (int i = 0; i < locations.length; i++)
            {
                Rectangle rect = getRectangle(x, y, w, h, locations[i]);
                g.setColor(Color.WHITE);
                g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
    }

    private Rectangle getRectangle(int x, int y, int w, int h, int location)
    {
        switch (location)
        {
            case SwingConstants.NORTH:
                return new Rectangle(x + w / 2 - size / 2, y, size, size);
            case SwingConstants.SOUTH:
                return new Rectangle(x + w / 2 - size / 2, y + h - size, size, size);
            case SwingConstants.WEST:
                return new Rectangle(x, y + h / 2 - size / 2, size, size);
            case SwingConstants.EAST:
                return new Rectangle(x + w - size, y + h / 2 - size / 2, size, size);
            case SwingConstants.NORTH_WEST:
                return new Rectangle(x, y, size, size);
            case SwingConstants.NORTH_EAST:
                return new Rectangle(x + w - size, y, size, size);
            case SwingConstants.SOUTH_WEST:
                return new Rectangle(x, y + h - size, size, size);
            case SwingConstants.SOUTH_EAST:
                return new Rectangle(x + w - size, y + h - size, size, size);
        }
        return null;
    }

    public int getCursor(MouseEvent me)
    {

        Component c = me.getComponent();
        int w = c.getWidth();
        int h = c.getHeight();
        //8
        for (int i = 0; i < locations.length; i++)
        {
            Rectangle rect = getRectangle(0, 0, w, h, locations[i]);
            if (rect.contains(me.getPoint()))
            {
                return cursors[i];
            }
        }

        return Cursor.MOVE_CURSOR;
    }
}
