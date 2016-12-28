/*
 * $Log: Version.java,v $
 * Revision 1.4  2009/11/17 20:40:18  gei
 * converter fuer ansi to pc ist vorgesehen, mapping fehlt noch
 *
 * Revision 1.3  2009/11/15 12:40:47  gei
 * build
 *
 * Revision 1.2  2009/11/15 12:39:34  gei
 * new version
 *
 * Revision 1.1  2009/11/14 12:43:05  gei
 * refactor
 *
 * Revision 1.3.2.3  2009/11/14 11:34:43  gei
 * optik - einrueckungen usw
 *
 * Revision 1.3.2.2  2009/11/14 10:47:42  gei
 * start new cursor moves
 *
 * Revision 1.3.2.1  2009/11/14 10:12:07  gei
 * report cvs
 *
 * ------
 * 0.9.5 20091114 almost done - some cursor movements wrong and still
 *                having pc-charset instead of ansi
 * 0.2.3          colorarray is now indexarray
 * 0.2.1 20091107 added esc[G and esc{X
 * 
 * 0.2.0 initial
 */
package jansi2gif;

/**
 *
 * @author gei
 *
 */
public class Version {

    public static final String artname[] = {
        "     @   @    @    @  @@@   @@@   @@@   @@@   @@@  @@@@@",
        "     @  @ @   @@   @ @   @   @   @   @ @   @   @   @    ",
        "     @ @   @  @ @  @ @       @       @ @       @   @    ",
        "     @ @   @  @  @ @  @@@    @    @@@  @ @@@   @   @@@@ ",
        "     @ @@@@@  @  @ @     @   @   @     @   @   @   @    ",
        " @   @ @   @  @   @@ @   @   @   @     @   @   @   @    ",
        "  @@@  @   @  @    @  @@@   @@@  @@@@@  @@@   @@@  @    ",};
    private static final String official = new String("jansi2gif");
    private static final String Major =  "0";
    private static final String Minor = "11";
    private static final String Sub =    "1";
    private static final String cstring =
            new String("(c) 2009 DENIC"/*jgeinitz@gmx.net"*/);
    private static final String cvsVersion =
            "$Id: Version.java,v 1.4 2009/11/17 20:40:18 gei Exp $";
    private boolean verbose = false;

    /**
     * get the official CVS string
     * @return CVS id
     */
    public String getCVS() {
        return cvsVersion;
    }

    /**
     * a copyright string
     *
     * @return String
     */
    public String getCopy() {
        return cstring;
    }

    /**
     * output a version string to stdout
     */
    public String getVersion() {
        return ( official +
                " v " +
                Major +
                "." +
                Minor +
                "." +
                Sub );
    }

    /**
     * output the program name
     */
    public String getName() {
        return ( official );
    }

    /**
     * do we want a verbose output?
     * @return true/false
     */
    public boolean getVerbose() {
        return verbose;
    }

    /**
     * set verbosity
     * @param f bool
     */
    public void setVerbose(boolean f) {
        verbose = f;
    }

    /**
     * fake constructor
     */
    public Version() {
    }

    ;
}
