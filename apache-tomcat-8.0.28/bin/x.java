public class x {
  public static void main(String args[]) {
    for (int count=1;count<=102;count++) {
      int lightid = (int)(Math.random()*27)+1;
      System.out.println ("update light set lightid='"+lightid+"',startTime=startTime where sn='"+count+"';");
      
    }
  }
}