package de.xn__jrgen_geinitz_wob;


public class GetOpt {

   public GetOpt(String[] args, String opts) {
   }

   // user can toggle this to control printing of error messages
   public boolean optErr = false;

   public int processArg(String arg, int n) {
   }

   public int tryArg(int k, int n) {
   }

   public long processArg(String arg, long n) {
   }

   public long tryArg(int k, long n) {
   }

   public double processArg(String arg, double d) {
   }

   public double tryArg(int k, double d) {
   }

   public float processArg(String arg, float f) {
   }

   public float tryArg(int k, float f) {
   }

   public boolean processArg(String arg, boolean b) {
      // `true' in any case mixture is true; anything else is false
   }

   public boolean tryArg(int k, boolean b) {
   }

   public String tryArg(int k, String s) {
   }

   public static final int optEOF = -1;

   public int optIndexGet() {return optIndex;}

   public String optArgGet() {return optArg;}


   public int getopt() {
   }
}

