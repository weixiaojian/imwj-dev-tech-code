package com.imwj.main;

/**
 * Java控制流：if、switch、while、for、continue、brek、
 * @author wj
 * @create 2023-04-23 16:34
 */
public class Main {

    public static void main(String[] args) {
        // if判断
        if(1 == 1){
            System.out.println("1 == 1");
        }else{
            System.out.println("1 != 1");
        }

        // switch
        switch (1){
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
                break;
            default:
                System.out.println("default");
        }

        // while
        int j = 0;
        while (j < 100){
            if(j == 99){
                System.out.println(j);
            }
            j ++;
        }

        // for
        for(int i=0; i<100; i++){
            if(i == 1){
                continue;
            }
            if(i == 99){
                System.out.println(i);
                break;
            }
        }


    }

}
