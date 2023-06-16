package studyCase.Constants.Routes;

public class BookRoute {
    private static final String BOOKS = "/books";
    private static final String API = "/api";
    private static final String ID = "/{id}";


    public static String books() {
        return  API + BOOKS ;
    }

    public static String getBook(){
        return API + BOOKS + ID;
    }


}
