package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;

public class Machine {
  private Cpu cpu = new Cpu();
  private Memory memory = new Memory();
  
  public void load(Program program) {
    int index = 0;
    for (int instr : program) {
      memory.set(index++, instr);
      }
    }
  
  public void tick() {
    int instr = memory.get(cpu.getIp());
    if (instr == 0b0000_0000) {
      // 0000 0000  NOP
      cpu.incIp();
      // cpu.setIp(cpu.getIp() + 1);
      }
    else if (instr == 0b0000_0001) {
      // 0000 0001 ADD A B
      cpu.setA(cpu.getA() + cpu.getB());
      cpu.setIp(cpu.getIp() + 1);
      }
    else if (instr == 0b0000_0010) {
      // 0000 0010 MUL A B
      cpu.setA(cpu.getA() * cpu.getB());
      cpu.setIp(cpu.getIp() + 1);
      }
    else if (instr == 0b0000_0011) {
      // 0000 0011 DIV A B
      cpu.setA(cpu.getA() / cpu.getB());
      cpu.setIp(cpu.getIp() + 1);
      }
    else if (instr == 0b0000_0100) {
      // 0000 0100 ZERO
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() == 0);
      }
    else if (instr == 0b0000_0101) {
      // 0000 0101 NEG
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() < 0);
      }
    else if (instr == 0b0000_0110) {
      // 0000 0110 POS
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() > 0);
      }
    else if (instr == 0b0000_0111) {
      // 0000 0111 NZERO
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() != 0);
      }
    else if (instr == 0b0000_1000) {
      // 0000 1000 EQ
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() == cpu.getB());
      }
    else if (instr == 0b0000_1001) {
      // 0000 1001 LT
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() < cpu.getB());
      }
    else if (instr == 0b0000_1010) {
      // 0000 1010 GT
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() > cpu.getB());
      }
    else if (instr == 0b0000_1011) {
      // 0000 1011 NEQ
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(cpu.getA() != cpu.getB());
      }
    else if (instr == 0b0000_1100) {
      // 0000 1100 ALWAYS
      cpu.setIp(cpu.getIp() + 1);
      cpu.setFlag(true);
      }
    else if (instr == 0b0000_1000) {
      // 0000 1111 HALT
      //MANGLER                      MANGLER                            MANGLER
      }
    else if ((instr & 0b1111_1110) == 0b0001_0000) {
      // 0001 000X PUSH r
      //--SP
      cpu.setSp(cpu.getSp() - 1);
      //[SP] <- r
      //If instr is an even number A is used if odd number B is used
      memory.set(cpu.getSp(), (((instr & 0b0000_0001) == 0) ? cpu.getA() : cpu.getB()));
      //IP++
      cpu.setIp(cpu.getIp() + 1);
      }
    else if ((instr & 0b1111_1110) == 0b0001_0010) {
      // 0001 001X POP r
      //r <- [SP]
      //If instr is an even number A is used if odd number B is used
      memory.set(((instr % 2 == 0) ? cpu.getA() : cpu.getB()), cpu.getSp());
      //SP++
      cpu.setSp(cpu.getSp() + 1);
      //IP++
      cpu.setIp(cpu.getIp() + 1);
      }
    else if (instr == 0b0001_0100) {
      // 0001 0100 MOV A B
      cpu.setIp(cpu.getIp() + 1);
      cpu.setB(cpu.getA());
      }
    else if (instr == 0b0001_0101) {
      // 0001 0101 MOV B A
      cpu.setIp(cpu.getIp() + 1);
      cpu.setA(cpu.getB());
      }
    else if (instr == 0b0001_0110) {
      // 0001 0110 INC
      cpu.setIp(cpu.getIp() + 1);
      cpu.setA(cpu.getA() + 1);
      }
    else if (instr == 0b0001_0111) {
      // 0001 0111 DEC
      cpu.setIp(cpu.getIp() + 1);
      cpu.setA(cpu.getA() - 1);
      }
    else if ((instr & 0b1111_1000) == 0b0001_1000) {
      // 0001 1ooo RTN +o
      //IP <- [SP++]
      cpu.setIp(memory.get(cpu.getSp()));
      cpu.setSp(cpu.getSp() + 1);
      //SP += o
      cpu.setSp(cpu.getSp() + instr & 0b0000_0111);
      //ip++
      cpu.setIp(cpu.getIp() + 1);
      }
    else if ((instr & 0b1111_0000) == 0b0010_0000) {
      // 0010 rooo MOV r o
      //[SP + o] <- r
      memory.set(cpu.getSp() + instr & 0b0000_0111, (((instr & 0b0000_1000) == 0) ? cpu.getA() : cpu.getB()));
      //ip++
      cpu.setIp(cpu.getIp() + 1);
      }
    else if ((instr & 0b1111_0000) == 0b0011_0000) {
      // 0011 ooor MOV o r
      //r <- [SP + o]
      memory.set((((instr & 0b0000_0001) == 0) ? cpu.getA() : cpu.getB()), cpu.getSp() + instr & 0b0000_1110);
      //ip++
      cpu.setIp(cpu.getIp() + 1);
      }
    else if ((instr & 0b110_0000) == 0b0100_0000) { //Måske er instr & 0b0011_1110 forkert for value.
      // 01vv vvvr MOV v r
      //r <- v
      memory.set((((instr & 0b0000_0001) == 0) ? cpu.getA() : cpu.getB()), instr & 0b0011_1110);
      //ip++
      cpu.setIp(cpu.getIp() + 1);
      }
    else if ((instr & 0b1100_0000) == 0b1000_0000) {
      // 10aa aaaa JMP #a
      //if F then IP <- a
      if(cpu.isFlag())
      {
          cpu.setIp(cpu.getA());
      }
      else
      {
          //ip++
          cpu.setIp(cpu.getIp() + 1);
      }
      }
    
    
    
    // ..
    else if ((instr & 0b1111_0000) == 0b0010_0000) {
      // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
      
      // 0010 1 011 MOV B (=1) +3  [SP +3] // Move register B to memory position of SP with offset 3
      
      // 00101011 finding instruction
      //    and
      // 11110000
      // --------
      // 00100000
      
      // 00101011 finding offset
      //    and
      // 00000111
      // --------
      // 00000011 = 3
      
      // 00101011 finding register
      //    and
      // 00001000
      // --------
      // 00001000 = 8
      //    >> 3
      // 00000001 = 1
      
      int o = instr & 0b0000_0111;
      int r = (instr & 0b0000_1000) >> 3;
      if (r == cpu.A) memory.set(cpu.getSp() + o, cpu.getA());
      else memory.set(cpu.getSp() + o, cpu.getB());
      cpu.setIp(cpu.getIp() + 1);
      }
    }
  
  public void print(PrintStream out) {
    memory.print(out);
    out.println("-------------");
    cpu.print(out);
    }
  
  }
