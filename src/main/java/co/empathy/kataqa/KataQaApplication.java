package co.empathy.kataqa;


import co.empathy.kataqa.db.Database;
import co.empathy.kataqa.db.model.ProductDTO;
import co.empathy.kataqa.index.Index;
import co.empathy.kataqa.search.Search;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class KataQaApplication {

    public static final String PATH_DATABASE_DEFAULT = "src/main/resources/database.csv";
    public static final String PATH_DATABASE_UPDATE = "src/main/resources/database_update.csv";
    public static final String PATH_DATABASE_SAVE = "src/main/resources/database_save.csv";
    public static final String PATH_LOG = "src/main/resources/log.txt";


    private static Index index;
    private static Database db;
    private static Search search;


    public static void main(String[] args) throws IOException, InterruptedException {
        show("Indexing catalog");

        index = new Index();
        db = index.initialize(PATH_DATABASE_DEFAULT);
        search = new Search(db);
        Scanner userInput = new Scanner(System.in);

        label:
        while (true) {
            show("Select Operation: \n1. search \n2. skusearch \n3. delete \n4. update \n5. save \n6. exit");
            while (!userInput.hasNext())
                ;

            String fullInput = "";
            if (userInput.hasNext()) {
                fullInput = userInput.nextLine();
            }

            String[] input = fullInput.split(" ");

            switch (input[0]) {
                case "search":
                    show(search.search(input));
                    break;
                case "skusearch":
                    show(search.skusearch(input[1]));
                    break;
                case "delete":
                    search.delete(input[1]);
                    break;
                case "update":
                    search.update(PATH_DATABASE_UPDATE);
                    break;
                case "save":
                    search.save(PATH_DATABASE_SAVE);
                    break;
                case "exit":
                    search.exit(PATH_LOG);
                    userInput.close();
                    break label;
                default:
                    break;
            }
            Thread.sleep(100);
        }
    }

    private static void show(String s) {
        System.out.println(s);
    }

    private static void show(ProductDTO p) {
        show(p.toString());
    }

    private static void show(List<ProductDTO> productDTOList) {
        show("Total hits " + productDTOList.size());
        for (ProductDTO p : productDTOList) {
            show(p);
        }
    }

}
