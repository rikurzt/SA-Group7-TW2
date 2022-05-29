package com.example.sa_g7_tw2_spring.DataProcessing;

import java.io.*;

public class ScriptRunner {
    static Caster printCaster = br->{
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println (">" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    };

    static class StreamConsumer<T> extends Thread {
        InputStream is;
        Caster<T> caster;
        T result;

        StreamConsumer (InputStream is, Caster<T> caster) {
            this.is = is;
            this.caster = caster;
        }

        public void run () {
            InputStreamReader isr = new InputStreamReader (is);
            BufferedReader br = new BufferedReader (isr);
            result = caster.cast(br);
        }

        public T getResult(){
            return result;
        }
    }

    public static <T> T runScript(Caster<T> caster, String cmd, String... args){
        File dir = new File(args[0]);

        //File dir = new File(pathToScript.substring(pathToScript.replaceAll("\\\\","/").lastIndexOf("/")));
        return runScript(caster, cmd, dir, args);
    }

    public static <T> T runScript(Caster<T> caster, String cmd, File dir, String... args)  {
        try{
            //Process p = Runtime.getRuntime().exec("cmd.exe /c " + cmd + " " + String.join(" ", args),null, dir);
            String command = cmd+" "+ String.join(" ", args);
            Process p = Runtime.getRuntime().exec(command);

            StreamConsumer err = new StreamConsumer(p.getErrorStream(), printCaster);
            StreamConsumer<T> output = new StreamConsumer(p.getInputStream(), caster);
            err.start();
            output.start();

            p.waitFor();

            output.join();
            return output.getResult();
            //return caster.cast(new BufferedReader(new InputStreamReader(p.getInputStream())));
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public interface Caster<T>{
        T cast(BufferedReader stream);
    }
}
