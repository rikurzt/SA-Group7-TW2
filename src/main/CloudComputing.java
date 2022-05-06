package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CloudComputing {
    private ai  aicom= new ai();
    private DataBaseMananger db = new DataBaseMananger();
    private DataProcessing dataprocessing = new DataProcessing();
    public static Stack<UploadFile> DataBuffer = new Stack<UploadFile>();


}
 class DataBaseMananger{
    public List FakeDataBase = new ArrayList<>();
    public void savetodatabase(){

    }
}