package com.example.sa_g7_tw2_spring.DataProcessing;

import java.io.*;

public class ScriptRunner {
    public static <T> T runScript(Caster<T> caster, String cmd, String... args){
        File dir = new File(args[0]);
        if(!dir.isDirectory()){
            dir = new File(dir.getParent());
        }
        //File dir = new File(pathToScript.substring(pathToScript.replaceAll("\\\\","/").lastIndexOf("/")));
        return runScript(caster, cmd, dir, args);
    }

    public static <T> T runScript(Caster<T> caster, String cmd, File dir, String... args)  {
        try{
            Process p = Runtime.getRuntime().exec(cmd, args, dir);
            return caster.cast(new BufferedReader(new InputStreamReader(p.getInputStream())));
        }catch (IOException e){
            return null;
        }
    }

    public interface Caster<T>{
        T cast(BufferedReader stream);
    }
}
