package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ScriptRunner {
    public static <T> T runScript(Caster<T> caster, String pathToScript, String... args){
        File dir = new File(pathToScript.substring(pathToScript.replaceAll("\\\\","/").lastIndexOf("/")));
        return runScript(caster, pathToScript, dir, args);
    }

    public static <T> T runScript(Caster<T> caster, String pathToScript, File dir, String... args)  {
        try{
            Process p = Runtime.getRuntime().exec(pathToScript, args, dir);
            return caster.cast(p.getInputStream());
        }catch (IOException e){
            return null;
        }
    }

    public interface Caster<T>{
        T cast(InputStream stream);
    }
}
