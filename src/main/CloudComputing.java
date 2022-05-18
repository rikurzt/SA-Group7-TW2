package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CloudComputing {
    private AI aicom= new AI();
    private DataBaseMananger db = new DataBaseMananger();
    private DataProcessing dataprocessing = new DataProcessing();
    public static Stack<UploadFile> DataBuffer = new Stack<UploadFile>();


}
<<<<<<< HEAD
class DataBaseMananger{
=======
 class DataBaseMananger{
>>>>>>> main
    public List FakeDataBase = new ArrayList<>();
    public void savetodatabase(){

    }
}