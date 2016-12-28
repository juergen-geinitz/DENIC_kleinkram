/*
 * $Id: Screen.java,v 1.2 2009/11/15 12:34:11 gei Exp $
 * $Log: Screen.java,v $
 * Revision 1.2  2009/11/15 12:34:11  gei
 * suche solaris positionierungprobleme
 *
 * Revision 1.1  2009/11/14 15:00:49  gei
 * refactor
 *
 * Revision 1.1.1.1.2.2  2009/11/14 11:33:25  gei
 * optik - einrueckungen usw
 *
 * Revision 1.1.1.1.2.1  2009/11/14 10:17:05  gei
 * cvs
 *
 */
package jansi2gif;

import java.awt.Color;

/**
 * this is the screen where everything will be put
 * @author gei
 */
public class Screen {

    private char scr[][];
    private int scrFcolor[][];
    private int scrBcolor[][];
    private boolean scrBold[][];
    private int xsize;
    private int ysize;
    private int currentFcolorindex,
            currentBcolorindex,
            currentx, currenty;
    private static Color[] colors = {
        new Color(0, 0, 0),
        new Color(0xAA, 0, 0),
        new Color(0, 0xAA, 0),
        new Color(0xAA, 0x55, 0x22),
        new Color(0, 0, 0xAA),
        new Color(0xAA, 0, 0xAA),
        new Color(0, 0xAA, 0xAA),
        new Color(0xAA, 0xAA, 0xAA),
        new Color(/*0x7D,0x7D,0x7d*/0x4d, 0x4d, 0x4d),
        new Color(0xFF, 0x7d, 0x7d),
        new Color(0, 0xFF, 0),
        new Color(0xFF, 0, 0xFF),
        new Color(0, 0, 0xFF),
        new Color(0xFF, 0, 0xFF),
        new Color(0, 0xFF, 0xFF),
        new Color(0xFF, 0xFF, 0xFF),
        // special color -- almost black but nor totally used in erase
        new Color(0x08, 0x08, 0x08)
    };

    /**
     * setup
     * @param x
     * @param y
     */
    public Screen(int x, int y) {
        init(x, y);
    }

    private void init(int x, int y) {
        scr = new char[x][y];
        scrFcolor = new int[x][y];
        scrBcolor = new int[x][y];
        scrBold = new boolean[x][y];
        xsize = x;
        ysize = y;
        cls();
    }

    /**
     * return current x position
     * @return
     */
    public int getX() {
        return currentx;
    }

    /**
     * current y pos
     * @return
     */
    public int getY() {
        return currenty;
    }

    /**
     * the screen height
     * @return
     */
    public int getYsize() {
        return ysize;
    }

    /**
     * the screen width
     * @return
     */
    public int getXsize() {
        return xsize;
    }

    /**
     * clear the screen area
     */
    public void cls() {
        int i, j;
        setBColor(16);
        setFColor(7);
        for (i = 0; i < ysize; i++) {
            for (j = 0; j < xsize; j++) {
                setchar(j, i, ' ');
                setboldmode(false);
            }
        }
        setpos(0, 0);
        setBColor(0);
    }

    /**
     * set the background color to index c
     * @param c
     */
    public void setBColor(int c) {
        currentBcolorindex = c;
    }

    /**
     * set the foreground color to index c
     * @param c
     */
    public void setFColor(int c) {
        currentFcolorindex = c;
    }

    /**
     * the nxt char will be put to position x,y
     * @param x
     * @param y
     */
    public void setpos(int x, int y) {
        if (x >= xsize) {
            x = 0;
            y++;
        }
        if (y >= ysize) {
            y = ysize - 1;
        //    scroll();
        }

        currentx = x;
        currenty = y;
    }


    /**
     * set the char at x,y
     * @param x
     * @param y
     * @param c
     */
    public void setchar(int x, int y, char c) {
//        if ( x >= xsize ) x = xsize-1;
//        if ( y >= ysize ) y = ysize-1;
        setpos(x, y);
        x = currentx;
        y = currenty;
        ///System.out.println("sepos x= " + x);
        scr[x][y] = c;
        scrFcolor[x][y] = currentFcolorindex;
        scrBcolor[x][y] = currentBcolorindex;
        currentx++;
    }

    /**
     * return the char at x,y
     * @param x
     * @param y
     * @return
     */
    public char getChar(int x, int y) {
        return scr[x][y];
    }

    /**
     * get the foregroundcolor at x,y
     * @param x
     * @param y
     * @return
     */
    public Color getFcolor(int x, int y) {
        if (scrBold[x][y]) {
            return colors[scrFcolor[x][y] + 8];
        }
        return colors[scrFcolor[x][y]];
    }

    /**
     * get the backgroundcolor at x,y
     * @param x
     * @param y
     * @return
     */
    public Color getBcolor(int x, int y) {
        //    if ( scrBold[x][y ])
        //        return colors[scrBcolor[x][y]+8];
        return colors[scrBcolor[x][y]];
    }

    /**
     * scroll down one line
     */
    public void scroll() {
        int i, j;
        for (i = 1; i < ysize; i++) {
            for (j = 0; j < xsize; j++) {
                scr[j][i - 1] = scr[j][i];
                scrFcolor[j][i - 1] = scrFcolor[j][i];
                scrBcolor[j][i - 1] = scrBcolor[j][i];
                scrBold[j][i - 1] = scrBold[j][i];
            }
        }
        for (i = 0; i < xsize; i++) {
            scr[i][ysize - 1] = ' ';
            scrFcolor[i][ysize - 1] = 7;
            scrBcolor[i][ysize - 1] = 16;
            scrBold[i][ysize - 1] = false;
        }
    }

    /**
     * set char c at current pos
     * @param c
     */
    public void setchar(char c) {
        setchar(currentx, currenty, c);
    }

    /**
     * clear screen blow cursur line
     */
    public void clearbelow() {
        int i, j;
        int cb;
        if (currentBcolorindex == 0) {
            cb = 16;
        } else {
            cb = currentBcolorindex;
        }
        for (i = currentx; i < xsize; i++) {
            scr[i][currenty] = ' ';
            scrFcolor[i][currenty] = currentFcolorindex;
            scrBcolor[i][currenty] = cb;
            scrBold[i][currenty] = false;
        }
        for (j = currenty + 1; j < ysize; j++) {
            for (i = 0; i < xsize; i++) {
                scr[i][j] = ' ';
                scrFcolor[i][j] = currentFcolorindex;
                scrBcolor[i][j] = cb;
                scrBold[i][j] = false;
            }
        }
    }


    /**
     * clear screen abobe line
     */
    public void clearabove() {
        int i, j;
        int cb;
        if (currentBcolorindex == 0) {
            cb = 16;
        } else {
            cb = currentBcolorindex;
        }
        for (i = currentx; i >= 0; i--) {
            scr[i][currenty] = ' ';
            scrFcolor[i][currenty] = currentFcolorindex;
            scrBcolor[i][currenty] = cb;
            scrBold[i][currenty] = false;
        }
        for (j = currenty - 1; j >= 0; --j) {
            for (i = 0; i < xsize; i++) {
                scr[i][j] = ' ';
                scrFcolor[i][j] = currentFcolorindex;
                scrBcolor[i][j] = cb;
                scrBold[i][j] = false;
            }
        }
    }

    /**
     * clear to end of line
     */
    public void clearEOL() {
        int cb;
        if (currentBcolorindex == 0) {
            cb = 16;
        } else {
            cb = currentBcolorindex;
        }
        if (currenty >= ysize) {
            currenty = ysize - 1;
        }
        for (int i = currentx; i < xsize; i++) {
            scr[i][currenty] = ' ';
            scrFcolor[i][currenty] = currentFcolorindex;
            scrBcolor[i][currenty] = cb;
            scrBold[i][currenty] = false;
        }
    }

    /**
     * clear the line
     */
    public void clearLine() {
        int cb;
        if (currentBcolorindex == 0) {
            cb = 16;
        } else {
            cb = currentBcolorindex;
        }
        if (currenty >= ysize) {
            currenty = ysize - 1;
        }
        for (int i = 0; i < xsize; i++) {
            scr[i][currenty] = ' ';
            scrFcolor[i][currenty] = currentFcolorindex;
            scrBcolor[i][currenty] = cb;
            scrBold[i][currenty] = false;
        }
    }

    /**
     * clear line y
     * @param ty
     */
    public void clearLine(int ty) {
        int cb;
        if (currentBcolorindex == 0) {
            cb = 16;
        } else {
            cb = currentBcolorindex;
        }
        for (int i = 0; i < xsize; i++) {
            scr[i][ty] = ' ';
            scrFcolor[i][ty] = currentFcolorindex;
            scrBcolor[i][ty] = cb;
            scrBold[i][ty] = false;
        }
    }

    /**
     * clear to begin of line
     */
    public void clearBOL() {
        int cb;
        if (currentBcolorindex == 0) {
            cb = 16;
        } else {
            cb = currentBcolorindex;
        }
        if (currenty >= ysize) {
            currenty = ysize - 1;
        }
        for (int i = currentx; i >= 0; i--) {
            scr[i][currenty] = ' ';
            scrFcolor[i][currenty] = currentFcolorindex;
            scrBcolor[i][currenty] = cb;
            scrBold[i][currenty] = false;
        }
    }

    boolean getboldmode(int x, int y) {
        return scrBold[x][y];
    }

    void setboldmode(boolean b) {
        if (( currentx >= xsize ) || ( currenty >= ysize )) {
            return;
        }
        scrBold[currentx][currenty] = b;
    }
}
