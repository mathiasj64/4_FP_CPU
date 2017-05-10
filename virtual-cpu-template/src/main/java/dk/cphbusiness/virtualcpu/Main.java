package dk.cphbusiness.virtualcpu;

import java.util.Scanner;

public class Main
{  

    public static void main(String[] args)
    {
        System.out.println("Welcome to the awesome CPU program");
        
        
        
        //2 + 3 program                MOV A +2    MOV B +3      ADD         HALT
        //Program program = new Program("01001000", "01000111", "00000001", "00001111");
        // 2+3
        //Program program = new Program("01000100", "01000111", "00000001", "00001111");
        // -2+-3
        //                               -7 A         6 B 
        //Program program = new Program("01000000", "01111101", "00000001", "00001111"); //af en eller anden grund virker minus kun for ulige tal (måske noget galt med binary shift)
                                     //01vvvvvr    01vvvvvr
                                    
        //Fact 5 program
        Program program = new Program("01001010", "00010000", "00001100", "11000110", "00010010", "00001111", "00110010", "00000111", "10001100", "01000010", "00100001", "00011000", "00010000", "00010111", "00010000", "00001100", "11000110", "00010011", "00010010", "00000010", "00100001", "00011000");

        //MOV R O TEST Program
        //Program program = new Program("01001010", "00010000", "00100001");
                                     
        //idk program
        //Program program = new Program("01000010", "MOV A +5", "00001111", "MOV B +3");

//Faculty program
        //Program program = new Program("01001010", "00010000", "00001100", "00010010", "00001111", "00110010", "00000111", "10001100", "01000010", "00100001", "00011000", "00010000", "00010111", "00010000", "00001100", "11000110", "00010011", "00010010", "00000010", "00100001", "00011000");
        Machine machine = new Machine();
        machine.load(program);
        machine.print(System.out);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to run next line");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("quit"))
        {
            machine.tick();

            if (machine.getCpu().isRunning() == false)
            {
                break;
            }

            machine.print(System.out);
            System.out.println("Press enter to run next line");
            input = scanner.nextLine();
        }

    }

}