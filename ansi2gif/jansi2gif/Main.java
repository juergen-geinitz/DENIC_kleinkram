/*
 * The main class
 *
 * $Id: Main.java,v 1.2 2009/11/15 12:34:11 gei Exp $
 * ******************************
 * $Log: Main.java,v $
 * Revision 1.2  2009/11/15 12:34:11  gei
 * suche solaris positionierungprobleme
 *
 * Revision 1.1  2009/11/14 15:00:49  gei
 * refactor
 *
 * Revision 1.3.2.1  2009/11/14 11:36:25  gei
 * cvs version is only sown on verbose help
 *
 * Revision 1.3  2009/11/14 10:12:40  gei
 * report cvs
 *
 * Revision 1.2  2009/11/14 10:07:00  gei
 * cvs setup
 *
 */

package jansi2gif;

import de.xn__jrgen_geinitz_wob.GetOpt;

/**
 *
 * @author geinitz@denic.de
 */
public class Main {

    // in normal case, this prog does not run without parameters
    // when debug is true, we set default filenames
    private static final boolean dEbUg = false;

    // this is the class where the job is done
    protected static ImageProcessor g;

    // its always good to have a centralized version info
    public static Version v = new Version();

    private static void Usage(boolean hiddenflag,
            String v,       
            String n,
            String cvs) {
        System.err.println();
        System.err.println("Usage: java -jar "+
                n +
                ".jar [options]"+
                " infile outfile");
        System.err.println("Options:");
        System.err.println("\t-v     be verbose");
        System.err.println("\t-h     this help");
        System.err.println("\t-d <n> delay between frames in ms");
        System.err.println("\t-n <n> output a frame every n NL chars");
        System.err.println("\t-E <n> output a frame every n ESC chars");
        System.err.println("\t-F <n> output a frame every n chars");
        System.err.println("\t-x <n> screen width");
        System.err.println("\t-y <n> screen height");
        if ( hiddenflag ) {
            System.err.println("Secret options:");
            System.err.println("\t-Q     don't generate first info frame");
            System.err.println("\t-D     delay after info frame");
        }
        System.err.println();
        System.err.println("this is " + v);
        if ( hiddenflag )
            System.err.println("(CVS " + cvs + ")");
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int         optchar    = -1;
        boolean     DontRun    = false;
        String      infile     = null;
        String      outfile    = null;
        boolean     copyimage  = true;
        int         i;
        int         width      = 80,
                    height     = 25;
        int         delay      = 100,
                    numnl      = 1,
                    numesc     = 10,
                    flushcount = 100,
                    fdelay     = 1000;

        GetOpt options = new GetOpt(args, "vhQd:n:E:F:x:y:");
        options.optErr = true;

        while ( (optchar = options.getopt()) != GetOpt.optEOF ) {
            switch (optchar) {
                case 'v':
                    v.setVerbose(true);
                    break;
                case 'd':
                    delay = options.processArg(options.optArgGet(), delay);
                    // min dleay is 10 ms
                    delay = ((delay + 5) / 10 ) * 10;
                    if ( delay == 0 ) delay = 10;
                    break;
                case 'D':
                    fdelay = options.processArg(options.optArgGet(), fdelay);
                    // min dleay is 10 ms
                    fdelay = ((fdelay + 5) / 10 ) * 10;
                    if ( fdelay == 0 ) fdelay = 10;
                    break;
                case 'n':
                    numnl = options.processArg(options.optArgGet(), numnl);
                    break;
                case 'E':
                    numesc = options.processArg(options.optArgGet(), numesc);
                    break;
                case 'F':
                    flushcount = options.processArg(options.optArgGet(),
                            flushcount);
                    break;
                case 'x':
                    width = options.processArg(options.optArgGet(), width);
                    break;
                case 'y':
                    height = options.processArg(options.optArgGet(), height);
                    break;
                case 'Q':
                    copyimage = false;
                    break;
                case 'h':
                case '?':
                    DontRun = true;
                    break;
                default:
                    System.err.println("GetOpt went weird on char "
                            + (char)optchar);
                    DontRun = true;
            }
        }

        // parse the rest of the commandline
        i = options.optIndexGet();
        if ( i < args.length )
            infile = args[i];
        i++;
        if ( i < args.length )
            outfile = args[i];

        // in debug/development we dont want to abort when no filenames
        // are given
        if ( dEbUg && (infile == null))
            infile = "/tmp/infile.txt";
        if ( dEbUg && (outfile == null))
            outfile = "/tmp/outfile.gif";

        // do we have everything we need?
        if ( (infile == null) && (outfile == null))
            DontRun = true;
        if ( ! DontRun ) {
            if ( outfile == null) {
                DontRun = true;
                System.err.println(v.getName() +
                        " Error: outfilename is missing");
            }

            if ( infile == null) {
                DontRun = true;
                System.err.println(v.getName() +
                        " Error: infilename is missing");
           }
        }

        if ( DontRun ) {
            if ( v.getVerbose())
                copyimage=false;
            Usage(!copyimage,
                    v.getVersion(),
                    v.getName(),
                    v.getCVS());
            System.exit(1);
        }

        if ( v.getVerbose() ) {
            System.out.print(v.getVersion());
            System.out.println(" started\n");
        }
        numesc--;
        if ( numesc < 0 ) numesc = 0;
        numnl--;
        if ( numnl < 0 ) numnl = 0;
        if ( flushcount <= 0 )
            flushcount=1;
        if ( v.getVerbose()){
            System.err.println("new image after " +
                    ((int)numnl+1) +
                    " <NL>");
            System.err.println("new image after " +
                    ((int)numesc+1) +
                    " <ESC>");
            System.err.println("new image after " +
                    flushcount +
                    " chars");
            System.err.println("delay of " +
                    delay +
                    " ms between images");
            System.err.println("reading from \"" +
                    infile + "\"");
            System.err.println("writing to   \"" +
                    outfile + "\"");
            System.err.println();
        }
        g = new ImageProcessor(width, height);
        g.setDelay(delay);
        g.setFDelay(fdelay);
        g.setESC(numesc);
        g.setNL(numnl);
        g.setflush(flushcount);
        g.createGif(outfile);
        if ( copyimage )
            g.copyr(v.getVersion(),
                    numnl+1,
                    numesc+2,
                    flushcount,
                    delay,
                    infile,
                    outfile);
        g.processInFile(infile);
        g.endGif();
        if ( v.getVerbose()) {
            System.out.println();
            System.out.println("Done.");
        }
    }

}
