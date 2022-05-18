package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CloudComputing {
<<<<<<< Updated upstream
    private AI aicom= new AI();
=======
    private ai aicom = new ai();
>>>>>>> Stashed changes
    private DataBaseMananger db = new DataBaseMananger();
    private DataProcessing dataprocessing = new DataProcessing();
    public static Stack<UploadFile> DataBuffer = new Stack<UploadFile>();

    public void processTheDataInBuffer(){
        if(!DataBuffer.isEmpty()){
            dataprocessing.uploadFile=DataBuffer.pop();
        }
    }
}
 class DataBaseMananger{

    public List FakeDataBase = new ArrayList<>();
    public void savetodatabase(){

    }
}