import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {

    public ArrayList<String> strs = new ArrayList<>();
    
    public String handleRequest(URI url) {
        String path = url.getPath();
        String[] params;
        switch(path){ 
            case "/":
            return "Welcome to my search engine";
            case "/add":
            params = url.getQuery().split("=");
            if(params[0].equals("s")){
                this.strs.add(params[1]);
            }
            return String.format("String %s added!", params[1]);
            case "/search":
            params = url.getQuery().split("=");
            if(params[0].equals("s")) {
                List<String> arr = new ArrayList<>();
                for(String s : strs) {
                    if (s.contains(params[1])) {
                        arr.add(s);
                    }
                }
                if (arr.isEmpty()){
                    return String.format("Nothing was found for string %s", params[1]);
                } else {
                    String output = "Found: ";
                    for(String s : arr) {
                        output += s + ", ";
                    }
                    return output;
                }
            }
            return String.format("String %s");
            
            default:
            return String.format("Error 404: Path %s is invalid", path);

        }
    }
}

public class SearchEngine {

    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
