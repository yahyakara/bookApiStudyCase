package studyCase.Constants.Routes;

public class BookRoute {
    private static final String VERSION = "/v1";
    private static final String BOOKS = "/books";
    private static final String API = "/api";


    public static String books() {
        return  API + BOOKS ;
    }


}
