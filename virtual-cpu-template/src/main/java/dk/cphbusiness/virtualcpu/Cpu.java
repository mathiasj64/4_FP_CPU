package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;

public class Cpu
{

    public static final int A = 0;
    public static final int B = 1;
    private int a = 0;
    private int b = 0;
    private int ip = 0;
    private int sp = 0;
    private boolean flag = false;
    private boolean running = true;

    public void decSp()
    {
        if (sp == 0)
        {
            sp = 64;
        }
        sp = sp - 1;
    }

    public void incIp()
    {
        ip++;
        if (ip == 64)
        {
            ip = 0;
        }
    }

    public int getA()
    {
        return a;
    }

    public void setA(int a)
    {
        this.a = a;
    }

    public int getB()
    {
        return b;
    }

    public void setB(int b)
    {
        this.b = b;
    }

    public int getIp()
    {
        return ip;
    }

    public void setIp(int ip)
    {
        this.ip = ip;
    }

    public int getSp()
    {
        return sp;
    }

    public void setSp(int sp)
    {
        this.sp = sp;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public void print(PrintStream out)
    {
        out.printf("A:  %4d\n", a);
        out.printf("B:  %4d\n", b);
        out.printf("IP: %4d\n", ip);
        out.printf("SP: %4d\n", sp);
        out.println("F:  " + flag);
    }
    
    
    
//  
//  public void nop()
//  {
//      ip++;
//  }
//  
//  public void add(int a, int b)
//  {
//      ip++;
//      this.a = a + b;
//  }
//  
//  public void mul (int a, int b)
//  {
//      ip++;
//      this.a = a * b;
//  }
//          
//  public void div (int a, int b)
//  {
//      ip++;
//      this.a = a / b;
//  }
//          
//  public void zero (int a)
//  {
//      ip++;
//      flag = (a == 0);
//  }
//          
//  public void neg (int a)
//  {
//      ip++;
//      flag = (a < 0);
//  }
//          
//  public void pos (int a)
//  {
//      ip++;
//      flag = (a > 0);
//  }
//          
//  public void nzero (int a)
//  {
//      ip++;
//      flag = (a != 0);
//  }
//          
//  public void eq (int a, int b)
//  {
//      ip++;
//      flag = (a == b);
//  }
//          
//  public void always (int a, int b)
//  {
//      ip++;
//      flag = true;
//  }
//          
//  public void halt (int a, int b)
//  {
//      //Mangler
//  }
//      

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

}
