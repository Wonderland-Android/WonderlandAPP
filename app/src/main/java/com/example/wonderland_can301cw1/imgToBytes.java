package com.example.wonderland_can301cw1;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class imgToBytes {
    public static byte[] image2byte(String path){
        byte[] data = null;
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        System.out.println(data);
        return data;
    }

//    public static void main(String[] args) {
//        image2byte("/Users/zhaolihan/AndroidStudioProjects/WonderlandAPP/app/src/main/res/drawable/face3.jpeg");
//    }

}
