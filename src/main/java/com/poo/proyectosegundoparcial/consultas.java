/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import tienda.*;
/**
 *
 * @author isaias
 */

public class consultas {
    
    public static Sistema dameSistema(){
        Sistema sis = new Sistema();
        
        try {
            FileInputStream archivo = new FileInputStream("sistema.txt");
            ObjectInputStream lector = new ObjectInputStream(archivo);

            sis = (Sistema) lector.readObject();
        
        } catch(ClassNotFoundException | IOException e){
        }
        
        return sis;
    }
    
    public static void escribeSistema(Sistema sist){
        try {
            FileOutputStream archivo = new FileOutputStream("sistema.txt", false);
            ObjectOutputStream editor = new ObjectOutputStream(archivo);
            
            editor.writeObject(sist);
            
        } catch(IOException e){}
        
    }
    
}
